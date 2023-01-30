package tools.unsafe;

import com.oracle.svm.core.annotate.AutomaticFeature;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeClassInitialization;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.BasicValue;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@AutomaticFeature
public class ReflectionFinalFieldsFeature implements Feature {

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {

        Set<String> classNames = new HashSet<>();

        try {
            for (Path path : access.getApplicationClassPath()) {
                System.out.println(path);
                System.out.println("=========");
                if (path.toFile().isFile()) {
                    ZipInputStream zip = new ZipInputStream(new FileInputStream(path.toFile()));
                    while (true) {
                        ZipEntry e = zip.getNextEntry();
                        if (e == null)
                            break;
                        String name = e.getName();
                        //System.out.println(name);

                        // TODO: process classes in JAR

                    }
                } else if (path.toFile().isDirectory()) {
                    try (Stream<Path> stream = Files.walk(path)) {
                        stream.filter(Files::isRegularFile)
                                .forEach(file -> {
                                            System.out.println(file);
                                            // TODO: use https://stackoverflow.com/questions/48802291/asm-get-exact-value-from-stack-frame to get class name which should be configured in GraalVM
                                            // TODO: see

                                            try {

                                                Set<String> lambdaNames = null;

                                                {
                                                    ClassReader cr = new ClassReader(Files.newInputStream(file));
                                                    FieldSupplierDetector fieldSupplierDetector = new FieldSupplierDetector(Opcodes.ASM5);
                                                    cr.accept(
                                                            fieldSupplierDetector,
                                                            0
                                                    );
                                                    lambdaNames = fieldSupplierDetector.getUnsafeMethodVisitors().stream().flatMap(it -> it.getLambdas().stream()).collect(Collectors.toSet());
                                                }

                                                if (null != lambdaNames && !lambdaNames.isEmpty()) {
                                                    ClassReader cr = new ClassReader(Files.newInputStream(file));
                                                    ClassNode cn = new ClassNode(Opcodes.ASM5);
                                                    cr.accept(cn, 0);

                                                    for (MethodNode mn : cn.methods) {
                                                        //if(!mn.name.equals("main")) continue;
                                                        if (lambdaNames.contains(mn.name)) {
                                                            Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicInterpreter());
                                                            analyzer.analyze(cn.name, mn);

                                                            for (AbstractInsnNode node : mn.instructions) {
                                                                if (node instanceof MethodInsnNode) {
                                                                    MethodInsnNode method = (MethodInsnNode) node;
                                                                    if ("getDeclaredField".equals(method.name) && "java/lang/Class".equals(method.owner)) {
                                                                        if (node.getPrevious() instanceof LdcInsnNode && node.getPrevious().getPrevious() instanceof LdcInsnNode) {
                                                                            Object cst = ((LdcInsnNode) node.getPrevious().getPrevious()).cst;
                                                                            if (cst instanceof Type) {
                                                                                Type type = (Type) cst;
                                                                                classNames.add(type.getClassName());
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }
                                );
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //RuntimeClassInitialization.initializeAtRunTime(SampleClass.class);

        //if (true) System.exit(0);
        for (String className : classNames) {
            try {
                RuntimeClassInitialization.initializeAtRunTime(Class.forName(className, false, ReflectionFinalFieldsFeature.class.getClassLoader()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

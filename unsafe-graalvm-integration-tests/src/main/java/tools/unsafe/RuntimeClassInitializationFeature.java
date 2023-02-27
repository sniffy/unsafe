package tools.unsafe;

import org.graalvm.nativeimage.hosted.Feature;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//@AutomaticFeature
public class RuntimeClassInitializationFeature implements Feature {

    /*@Override
    public void duringSetup(DuringSetupAccess access) {
        access.registerObjectReplacer(o -> {
            if (o instanceof FieldSupplier) {
                System.out.println(o);
                try {
                    FieldSupplier fs = (FieldSupplier) o;
                    Field field = fs.field();
                    Class<?> declaringClass = field.getDeclaringClass();
                    RuntimeClassInitialization.initializeAtRunTime(declaringClass);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
            return o;
        });
    }*/

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {

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
                                                ClassReader cr = new ClassReader(Files.newInputStream(file));
                                                FieldSupplierDetector fieldSupplierDetector = new FieldSupplierDetector(Opcodes.ASM5);
                                                cr.accept(
                                                        fieldSupplierDetector,
                                                        0
                                                );
                                                Set<String> lamdaNames = fieldSupplierDetector.getUnsafeMethodVisitors().stream().flatMap(it -> it.getLambdas().stream()).collect(Collectors.toSet());

                                                cr.accept(
                                                        new FieldSupplierDetectorStage2(Opcodes.ASM5, lamdaNames),
                                                        0
                                                );

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

        if (true) System.exit(0);

        //RuntimeClassInitialization.initializeAtRunTime(SampleClass.class);

        /*try {
            access.registerMethodOverrideReachabilityHandler(
                    (duringAnalysisAccess, executable) -> {
                        try {
                            if (executable instanceof Method) {
                                Method method = (Method) executable;
                                System.out.println(method);
                                System.out.println(method.getDeclaringClass());
                                System.out.println(Arrays.toString(method.getDeclaringClass().getDeclaredConstructors()));
                                Constructor<?> declaredConstructor = method.getDeclaringClass().getDeclaredConstructor();
                                System.out.println("declaredConstructor=" + declaredConstructor);
                                declaredConstructor.setAccessible(true);
                                Object newInstance = declaredConstructor.newInstance();
                                System.out.println("newInstance=" + newInstance);
                                FieldSupplier fs = (FieldSupplier) newInstance;
                                Field field = fs.field();
                                Class<?> declaringClass = field.getDeclaringClass();
                                RuntimeClassInitialization.initializeAtRunTime(declaringClass);
                            }
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InstantiationException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        } catch (LambdaConversionException e) {
                            throw new RuntimeException(e);
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    },
                    FieldSupplier.class.getMethod("field")
            );
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }*/
    }

}

package tools.unsafe;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashSet;
import java.util.Set;

public class FieldSupplierDetectorStage2 extends ClassVisitor {

    private final Set<String> lamdaNames;
    private final Set<UnsafeMethodVisitorStage2> unsafeMethodVisitors = new HashSet<>();
    boolean implementsFieldSupplier = false;

    public FieldSupplierDetectorStage2(int api, Set<String> lamdaNames) {
        super(api);
        this.lamdaNames = lamdaNames;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        // https://stackoverflow.com/questions/47561756/how-to-modify-instructions-in-java-bootstrapmethods-using-asm
        implementsFieldSupplier = false;
        if (null != interfaces) {
            for (String iface : interfaces) {
                if (iface.contains("FieldSupplier")) {
                    //System.out.println(">>>> " + name + " extends " + iface);
                    implementsFieldSupplier = true;
                }
            }
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (lamdaNames.contains(name)) {
            System.out.println("Visiting " + name);
            UnsafeMethodVisitorStage2 unsafeMethodVisitor = new UnsafeMethodVisitorStage2(
                    Opcodes.ASM5,
                    super.visitMethod(access, name, descriptor, signature, exceptions)
            );
            unsafeMethodVisitors.add(unsafeMethodVisitor);
            return unsafeMethodVisitor;
        } else {
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        implementsFieldSupplier = false;
    }

    public Set<UnsafeMethodVisitorStage2> getUnsafeMethodVisitors() {
        return unsafeMethodVisitors;
    }

}

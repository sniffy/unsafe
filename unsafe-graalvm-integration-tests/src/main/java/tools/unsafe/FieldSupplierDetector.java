package tools.unsafe;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashSet;
import java.util.Set;

public class FieldSupplierDetector extends ClassVisitor {

    private final Set<UnsafeMethodVisitor> unsafeMethodVisitors = new HashSet<>();
    boolean implementsFieldSupplier = false;

    public FieldSupplierDetector(int api) {
        super(api);
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
        UnsafeMethodVisitor unsafeMethodVisitor = new UnsafeMethodVisitor(
                Opcodes.ASM5,
                super.visitMethod(access, name, descriptor, signature, exceptions)
        );
        unsafeMethodVisitors.add(unsafeMethodVisitor);
        return unsafeMethodVisitor;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        implementsFieldSupplier = false;
    }

    public Set<UnsafeMethodVisitor> getUnsafeMethodVisitors() {
        return unsafeMethodVisitors;
    }

}

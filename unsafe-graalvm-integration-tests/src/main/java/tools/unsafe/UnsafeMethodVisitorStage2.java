package tools.unsafe;

import org.objectweb.asm.MethodVisitor;

import java.util.HashSet;
import java.util.Set;

public class UnsafeMethodVisitorStage2 extends MethodVisitor {


    private final Set<String> lambdas = new HashSet<>();

    public UnsafeMethodVisitorStage2(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitLdcInsn(Object value) {
        super.visitLdcInsn(value);
        System.out.println("visitLdcInsn(" + value.getClass() + ")=" + value);
    }

    public Set<String> getLambdas() {
        return lambdas;
    }
}

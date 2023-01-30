package tools.unsafe;

import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;

import java.util.HashSet;
import java.util.Set;

public class UnsafeMethodVisitor extends MethodVisitor {


    private final Set<String> lambdas = new HashSet<>();

    public UnsafeMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
        /*System.out.println("visitInvokeDynamicInsn()");
        System.out.println(name);
        System.out.println(descriptor);
        System.out.println(bootstrapMethodHandle);
        System.out.println(Arrays.toString(bootstrapMethodArguments));*/
        if ("()Ltools/unsafe/reflection/FieldSupplier;".equals(descriptor)) {
            if ("altMetafactory".equals(bootstrapMethodHandle.getName())) {
                Object lambda = bootstrapMethodArguments[1];
                //System.out.println("Detected lambda of type " + lambda.getClass() + " = " + lambda);
                Handle handle = (Handle) lambda;
                //System.out.println("Lambda name is " + handle.getName());
                lambdas.add(handle.getName());
            }
        }
    }

    public Set<String> getLambdas() {
        return lambdas;
    }
}

package tools.unsafe.reflection.object;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.field.objects.resolved.ResolvedInstanceObjectFieldRef;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class UnresolvedObjectRef<C> extends UnresolvedRef<ObjectRef<C>> {

    public UnresolvedObjectRef(@Nonnull Callable<ObjectRef<C>> refSupplier) {
        super(refSupplier);
    }

    public <T> T getField(String fieldName) throws UnresolvedRefException, UnsafeInvocationException {
        return resolve().getField(fieldName);
    }

    public <T> void setField(String fieldName, T value) throws UnresolvedRefException, UnsafeInvocationException {
        resolve().setField(fieldName, value);
    }

    public <T> ResolvedInstanceObjectFieldRef<C, T> field(String fieldName) throws UnresolvedRefException {
        return resolve().field(fieldName);
    }

    public <T> UnresolvedObjectRef<T> fieldObjectRef(String fieldName) throws UnresolvedRefException {
        return resolve().fieldObjectRef(fieldName);
    }

    // TODO: add more methods similar to what is there in ClassRef (method, voidMEthod, etc.)
    public <T> T invoke(Class<T> returnType, String methodName) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        return resolve().invoke(returnType, methodName, new Class<?>[0], new Object[0]);
    }

    public <T> T invoke(Class<T> returnType, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        return resolve().invoke(returnType, methodName, parameterTypes, parameters);
    }

}

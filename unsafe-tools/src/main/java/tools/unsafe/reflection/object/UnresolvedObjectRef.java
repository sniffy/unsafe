package tools.unsafe.reflection.object;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.field.ResolvedInstanceObjectFieldRef;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class UnresolvedObjectRef<C> extends UnresolvedRef<ObjectRef<C>> {

    public UnresolvedObjectRef(@Nullable ObjectRef<C> ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    public <T> T getField(String fieldName) throws UnresolvedRefException, UnsafeInvocationException {
        return resolve().getField(fieldName);
    }

    public <T> void setField(String fieldName, T value) throws UnresolvedRefException, UnsafeInvocationException {
        resolve().setField(fieldName, value);
    }

    public <T> ResolvedInstanceObjectFieldRef<C,T> field(String fieldName) throws UnresolvedRefException {
        return resolve().field(fieldName);
    }

    public <T> UnresolvedObjectRef<T> $(String fieldName) throws UnresolvedRefException {
        return resolve().$(fieldName);
    }

    public <T> T $(Class<T> returnType, String methodName) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        return invoke(returnType, methodName);
    }

    public <T> T invoke(Class<T> returnType, String methodName) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        return resolve().invoke(returnType, methodName, new Class<?>[0], new Object[0]);
    }

    public <T> T invoke(Class<T> returnType, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        return resolve().invoke(returnType, methodName, parameterTypes, parameters);
    }

}

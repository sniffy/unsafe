package io.sniffy.unsafe.reflection.object;

import io.sniffy.unsafe.reflection.UnresolvedRef;
import io.sniffy.unsafe.reflection.UnresolvedRefException;
import io.sniffy.unsafe.reflection.UnsafeInvocationException;
import io.sniffy.unsafe.reflection.field.ObjectFieldRef;
import io.sniffy.unsafe.reflection.method.ObjectNonVoidMethodRef;

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

    public <T> ObjectFieldRef<C,T> field(String fieldName) throws UnresolvedRefException {
        return resolve().field(fieldName);
    }

    public <T> T invoke(Class<T> returnType, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        return resolve().invoke(returnType, methodName, parameterTypes, parameters);
    }

}

package io.sniffy.unsafe.reflection.method;

import io.sniffy.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NonStaticNonVoidMethodRef<C,T> {

    private final Method method;

    public NonStaticNonVoidMethodRef(Method method) {
        this.method = method;
    }

    public T invoke(C instance, Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        try {
            //noinspection unchecked
            return (T) method.invoke(instance, parameters);
        } catch (InvocationTargetException e) {
            throw e;
        } catch (Exception e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

package io.sniffy.unsafe.reflection.method;

import io.sniffy.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectNonVoidMethodRef<C,T> {

    private final NonStaticNonVoidMethodRef<C,T> nonStaticNonVoidMethodRef;
    private final C object;

    public ObjectNonVoidMethodRef(NonStaticNonVoidMethodRef<C, T> nonStaticNonVoidMethodRef, C object) {
        this.nonStaticNonVoidMethodRef = nonStaticNonVoidMethodRef;
        this.object = object;
    }

    public T invoke(C instance, Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        return nonStaticNonVoidMethodRef.invoke(object, parameters);
    }

}

package io.sniffy.unsafe.reflection.field;

import io.sniffy.unsafe.reflection.UnsafeInvocationException;

public class ObjectFieldRef<C, T> {

    private final NonStaticFieldRef<C, T> nonStaticFieldRef;
    private final C object;

    public ObjectFieldRef(NonStaticFieldRef<C, T> nonStaticFieldRef, C object) {
        this.nonStaticFieldRef = nonStaticFieldRef;
        this.object = object;
    }

    public T get() throws UnsafeInvocationException {
        return nonStaticFieldRef.get(object);
    }

    public void set(T value) throws UnsafeInvocationException {
        nonStaticFieldRef.set(object, value);
    }

}

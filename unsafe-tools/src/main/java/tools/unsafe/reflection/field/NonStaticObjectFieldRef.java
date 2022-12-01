package tools.unsafe.reflection.field;

import tools.unsafe.reflection.UnsafeInvocationException;

public interface NonStaticObjectFieldRef<C,T> {

    T get(C instance) throws UnsafeInvocationException;

    void set(C instance, T value) throws UnsafeInvocationException;

}

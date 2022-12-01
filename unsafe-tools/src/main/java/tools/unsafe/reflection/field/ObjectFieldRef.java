package tools.unsafe.reflection.field;

import tools.unsafe.reflection.UnsafeInvocationException;

public interface ObjectFieldRef<T> {

    T get() throws UnsafeInvocationException;

    void set(T value) throws UnsafeInvocationException;

}

package tools.unsafe.reflection.field.booleans;

import tools.unsafe.reflection.UnsafeInvocationException;

public interface BooleanFieldRef {

    boolean get() throws UnsafeInvocationException;

    void set(boolean value) throws UnsafeInvocationException;

    boolean compareAndSet(boolean oldValue, boolean newValue) throws UnsafeInvocationException;

}

package tools.unsafe.reflection.field.booleans;

import tools.unsafe.reflection.UnsafeInvocationException;

import javax.annotation.Nonnull;

public interface DynamicBooleanFieldRef<C> {

    boolean get(C instance) throws UnsafeInvocationException;

    void set(C instance, boolean value) throws UnsafeInvocationException;

    boolean compareAndSet(C instance, boolean oldValue, boolean newValue) throws UnsafeInvocationException;

    void copy(@Nonnull C from, @Nonnull C to) throws UnsafeInvocationException;


}

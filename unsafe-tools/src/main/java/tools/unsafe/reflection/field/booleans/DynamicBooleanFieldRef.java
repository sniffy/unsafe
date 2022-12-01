package tools.unsafe.reflection.field.booleans;

import tools.unsafe.reflection.UnsafeInvocationException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface DynamicBooleanFieldRef<C> {

    boolean get(@Nullable C instance) throws UnsafeInvocationException;

    void set(@Nullable C instance, boolean value) throws UnsafeInvocationException;

    boolean compareAndSet(@Nullable C instance, boolean oldValue, boolean newValue) throws UnsafeInvocationException;

    void copy(@Nonnull C from, @Nonnull C to) throws UnsafeInvocationException;


}

package tools.unsafe.reflection.field.objects;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.object.ObjectRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface DynamicObjectFieldRef<C,T> {

    @Nullable
    T get(@Nullable C instance) throws UnsafeInvocationException;

    @Nonnull
    ObjectRef<T> objectRef(@Nullable C instance) throws UnsafeInvocationException;

    @Nonnull T getNotNull(@Nullable C instance, @Nonnull T defaultValue) throws UnsafeInvocationException;

    void set(@Nullable C instance, @Nullable T value) throws UnsafeInvocationException;

    boolean compareAndSet(@Nullable C instance, @Nullable T oldValue, @Nullable T newValue) throws UnsafeInvocationException;

    void copy(@Nonnull C from, @Nonnull C to) throws UnsafeInvocationException;

}

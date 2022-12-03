package tools.unsafe.reflection.field.objects;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.object.ObjectRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ObjectFieldRef<T> {

    @Nullable
    T get() throws UnsafeInvocationException;

    @Nonnull
    ObjectRef<T> objectRef() throws UnsafeInvocationException;

    @Nonnull
    T getNotNull(@Nonnull T defaultValue) throws UnsafeInvocationException;

    void set(@Nullable T value) throws UnsafeInvocationException;

    boolean compareAndSet(@Nullable T oldValue, @Nullable T newValue) throws UnsafeInvocationException;

}

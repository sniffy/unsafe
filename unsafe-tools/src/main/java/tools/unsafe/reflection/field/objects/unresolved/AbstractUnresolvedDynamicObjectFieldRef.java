package tools.unsafe.reflection.field.objects.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.field.AbstractUnresolvedFieldRef;
import tools.unsafe.reflection.field.objects.DynamicObjectFieldRef;
import tools.unsafe.reflection.field.objects.resolved.AbstractDynamicObjectFieldRef;
import tools.unsafe.reflection.object.ObjectRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AbstractUnresolvedDynamicObjectFieldRef<R extends AbstractDynamicObjectFieldRef<C,T>, C, T> extends AbstractUnresolvedFieldRef<R,C> implements DynamicObjectFieldRef<C,T> {

    public AbstractUnresolvedDynamicObjectFieldRef(@Nullable R ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    @Nullable
    @Override
    public T get(@Nullable C instance) throws UnsafeInvocationException {
        try {
            return resolve().get(instance);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    @Nonnull
    @Override
    public ObjectRef<T> objectRef(@Nullable C instance) throws UnsafeInvocationException {
        try {
            return resolve().objectRef(instance);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    @Override
    public void set(@Nullable C instance, @Nullable T value) throws UnsafeInvocationException {
        try {
            resolve().set(instance, value);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public boolean compareAndSet(C instance, T oldValue, T newValue) throws UnsafeInvocationException {
        try {
            return resolve().compareAndSet(instance, oldValue, newValue);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public boolean trySet(C instance, T value) {
        try {
            set(instance, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public T getOrDefault(C instance, T defaultValue) {
        try {
            return get(instance);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public T getNotNullOrDefault(C instance, T defaultValue) {
        try {
            return getNotNull(instance, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    @Nonnull
    public T getNotNull(C instance, @Nonnull T defaultValue) throws UnsafeInvocationException {
        try {
            return resolve().getNotNull(instance, defaultValue);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public boolean tryCopy(C from, C to) {
        try {
            copy(from, to);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public void copy(@Nonnull C from, @Nonnull C to) throws UnsafeInvocationException {
        try {
            resolve().copy(from, to);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

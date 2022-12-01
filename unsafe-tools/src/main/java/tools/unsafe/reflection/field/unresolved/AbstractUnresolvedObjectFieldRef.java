package tools.unsafe.reflection.field.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.field.resolved.AbstractFieldRef;
import tools.unsafe.reflection.field.resolved.AbstractObjectFieldRef;
import tools.unsafe.reflection.field.types.ObjectFieldRef;
import tools.unsafe.reflection.object.ObjectRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AbstractUnresolvedObjectFieldRef<R extends AbstractObjectFieldRef<C,T>, C, T> extends AbstractUnresolvedFieldRef<R,C> implements ObjectFieldRef<T> {

    public AbstractUnresolvedObjectFieldRef(@Nullable R ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    @Nullable
    @Override
    public T get() throws UnsafeInvocationException {
        try {
            return resolve().get();
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    @Nonnull
    @Override
    public ObjectRef<T> objectRef() throws UnsafeInvocationException {
        try {
            return resolve().objectRef();
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    @Nonnull
    public T getNotNull(@Nonnull T defaultValue) throws UnsafeInvocationException {
        try {
            return resolve().getNotNull(defaultValue);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    @Override
    public void set(@Nullable T value) throws UnsafeInvocationException {
        try {
            resolve().set(value);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public boolean compareAndSet(T oldValue, T newValue) throws UnsafeInvocationException {
        try {
            return resolve().compareAndSet(oldValue, newValue);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public boolean trySet(T value) {
        try {
            set(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public T getOrDefault(T defaultValue) {
        try {
            return get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

}

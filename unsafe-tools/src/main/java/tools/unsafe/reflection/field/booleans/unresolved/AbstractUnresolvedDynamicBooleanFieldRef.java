package tools.unsafe.reflection.field.booleans.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.field.AbstractUnresolvedFieldRef;
import tools.unsafe.reflection.field.booleans.DynamicBooleanFieldRef;
import tools.unsafe.reflection.field.booleans.resolved.AbstractDynamicBooleanFieldRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AbstractUnresolvedDynamicBooleanFieldRef<R extends AbstractDynamicBooleanFieldRef<C>, C> extends AbstractUnresolvedFieldRef<R, C> implements DynamicBooleanFieldRef<C> {

    public AbstractUnresolvedDynamicBooleanFieldRef(@Nullable R ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    @Override
    public boolean get(@Nullable C instance) throws UnsafeInvocationException {
        try {
            return resolve().get(instance);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    @Override
    public void set(@Nullable C instance, boolean value) throws UnsafeInvocationException {
        try {
            resolve().set(instance, value);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public boolean compareAndSet(C instance, boolean oldValue, boolean newValue) throws UnsafeInvocationException {
        try {
            return resolve().compareAndSet(instance, oldValue, newValue);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public boolean trySet(C instance, boolean value) {
        try {
            set(instance, value);
            return true;
        } catch (Exception e) {
            return false;
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

package tools.unsafe.reflection.field.booleans.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.field.AbstractUnresolvedFieldRef;
import tools.unsafe.reflection.field.booleans.BooleanFieldRef;
import tools.unsafe.reflection.field.booleans.resolved.AbstractBooleanFieldRef;

import javax.annotation.Nullable;

public class AbstractUnresolvedBooleanFieldRef<R extends AbstractBooleanFieldRef<C>, C> extends AbstractUnresolvedFieldRef<R, C> implements BooleanFieldRef {

    public AbstractUnresolvedBooleanFieldRef(@Nullable R ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    @Override
    public boolean get() throws UnsafeInvocationException {
        try {
            return resolve().get();
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    @Override
    public void set(boolean value) throws UnsafeInvocationException {
        try {
            resolve().set(value);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public boolean compareAndSet(boolean oldValue, boolean newValue) throws UnsafeInvocationException {
        try {
            return resolve().compareAndSet(oldValue, newValue);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public boolean trySet(boolean value) {
        try {
            set(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

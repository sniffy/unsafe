package tools.unsafe.reflection.field;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;

import java.lang.reflect.Field;

public class UnresolvedInstanceObjectFieldRef<C,T> extends UnresolvedRef<ResolvedInstanceObjectFieldRef<C,T>> implements UnresolvedFieldRef<C>, ObjectFieldRef<T> {

    public UnresolvedInstanceObjectFieldRef(ResolvedInstanceObjectFieldRef<C,T> ref, Throwable throwable) {
        super(ref, throwable);
    }

    @Override
    public ClassRef<C> getDeclaringClassRef() throws UnresolvedRefException {
        return resolve().getDeclaringClassRef();
    }

    @Override
    public Field getField() throws UnresolvedRefException {
        return resolve().getField();
    }

    public boolean compareAndSet(T oldValue, T newValue) throws UnresolvedRefException, UnsafeInvocationException {
        return resolve().compareAndSet(oldValue, newValue);
    }

    public boolean trySet(T value) {
        try {
            set(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void set(T value) throws UnsafeInvocationException {
        try {
            resolve().set(value);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public T getOrDefault(T defaultValue) {
        try {
            return get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public T get() throws UnsafeInvocationException {
        try {
            return resolve().get();
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

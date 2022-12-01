package tools.unsafe.reflection.field;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;

import java.lang.reflect.Field;

public class UnresolvedNonStaticObjectFieldRef<C,T> extends UnresolvedRef<ResolvedNonStaticObjectFieldRef<C,T>> implements UnresolvedFieldRef<C>, NonStaticObjectFieldRef<C,T> {

    public UnresolvedNonStaticObjectFieldRef(ResolvedNonStaticObjectFieldRef<C,T> ref, Throwable throwable) {
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

    public boolean compareAndSet(C instance, T oldValue, T newValue) throws UnresolvedRefException, UnsafeInvocationException {
        return resolve().compareAndSet(instance, oldValue, newValue);
    }

    public boolean trySet(C instance, T value) {
        try {
            set(instance, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void set(C instance, T value) throws UnsafeInvocationException {
        try {
            resolve().set(instance, value);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public T getOrDefault(C instance, T defaultValue) {
        try {
            return get(instance);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public T get(C instance) throws UnsafeInvocationException {
        try {
            return resolve().get(instance);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public T getNotNullOrDefault(C instance, T defaultValue) {
        try {
            return getNotNull(instance, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public T getNotNull(C instance, T defaultValue) throws UnresolvedRefException, UnsafeInvocationException {
        return resolve().getNotNull(instance, defaultValue);
    }

    public boolean tryCopy(C from, C to) {
        try {
            resolve().copy(from, to);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public void copy(C from, C to) throws UnsafeInvocationException, UnresolvedRefException {
        resolve().copy(from, to);
    }

}

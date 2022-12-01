package tools.unsafe.reflection.field;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;

public class UnresolvedStaticFieldRef<C,T> extends UnresolvedRef<StaticFieldRef<C,T>> {

    public UnresolvedStaticFieldRef(StaticFieldRef<C,T> ref, Throwable throwable) {
        super(ref, throwable);
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

    public void set(T value) throws UnresolvedRefException, UnsafeInvocationException {
        resolve().set(value);
    }

    public T getOrDefault(T defaultValue) {
        try {
            return get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public T get() throws UnresolvedRefException, UnsafeInvocationException {
        return resolve().get();
    }

}

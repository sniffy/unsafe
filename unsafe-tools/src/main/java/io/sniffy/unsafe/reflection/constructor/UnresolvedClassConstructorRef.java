package io.sniffy.unsafe.reflection.constructor;

import io.sniffy.unsafe.reflection.UnresolvedRef;
import io.sniffy.unsafe.reflection.UnresolvedRefException;
import io.sniffy.unsafe.reflection.UnsafeInvocationException;

public class UnresolvedClassConstructorRef<C> extends UnresolvedRef<ClassConstructorRef<C>> {

    public UnresolvedClassConstructorRef(ClassConstructorRef<C> ref, Throwable throwable) {
        super(ref, throwable);
    }

    public C newInstanceOrNull(Object... parameters) {
        try {
            return newInstance();
        } catch (Throwable e) {
            return null;
        }
    }

    public C newInstance(Object... parameters) throws UnsafeInvocationException, UnresolvedRefException {
        return resolve().newInstance();
    }

}

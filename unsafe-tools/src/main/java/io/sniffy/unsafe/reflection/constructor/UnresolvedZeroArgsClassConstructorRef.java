package io.sniffy.unsafe.reflection.constructor;

import io.sniffy.unsafe.reflection.UnresolvedRef;
import io.sniffy.unsafe.reflection.UnresolvedRefException;
import io.sniffy.unsafe.reflection.UnsafeInvocationException;

public class UnresolvedZeroArgsClassConstructorRef<C> extends UnresolvedRef<ZeroArgsClassConstructorRef<C>> {

    public UnresolvedZeroArgsClassConstructorRef(ZeroArgsClassConstructorRef<C> ref, Throwable throwable) {
        super(ref, throwable);
    }

    public C newInstanceOrNull() {
        try {
            return newInstance();
        } catch (Throwable e) {
            return null;
        }
    }

    public C newInstance() throws UnsafeInvocationException, UnresolvedRefException {
        return resolve().newInstance();
    }

}

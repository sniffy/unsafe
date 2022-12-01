package io.sniffy.unsafe.reflection.constructor;

import io.sniffy.unsafe.Unsafe;
import io.sniffy.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ZeroArgsClassConstructorRef<C> {

    private final Constructor<C> constructor;

    public ZeroArgsClassConstructorRef(Constructor<C> constructor) {
        this.constructor = constructor;
        // TODO: should we make it accessible here ?
    }

    public C newInstanceOrNull() {
        try {
            return newInstance();
        } catch (Throwable e) {
            return null;
        }
    }

    public C newInstance() throws UnsafeInvocationException {
        try {
            return constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw Unsafe.throwException(e.getTargetException());
        } catch (Throwable e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

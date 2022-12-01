package io.sniffy.unsafe.reflection.method;

import io.sniffy.unsafe.reflection.UnresolvedRef;
import io.sniffy.unsafe.reflection.UnresolvedRefException;
import io.sniffy.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;

public class UnresolvedStaticMethodRef extends UnresolvedRef<StaticMethodRef> {

    public UnresolvedStaticMethodRef(StaticMethodRef ref, Throwable throwable) {
        super(ref, throwable);
    }

    public <T> T invoke(Object... parameters) throws UnsafeInvocationException, UnresolvedRefException, InvocationTargetException {
        return resolve().invoke(parameters);
    }

}

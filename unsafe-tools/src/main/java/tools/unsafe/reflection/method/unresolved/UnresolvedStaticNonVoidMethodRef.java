package tools.unsafe.reflection.method.unresolved;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.resolved.StaticNonVoidMethodRef;

import java.lang.reflect.InvocationTargetException;

public class UnresolvedStaticNonVoidMethodRef<T> extends UnresolvedRef<StaticNonVoidMethodRef<T>> {

    public UnresolvedStaticNonVoidMethodRef(StaticNonVoidMethodRef<T> ref, Throwable throwable) {
        super(ref, throwable);
    }

    public T invoke(Object... parameters) throws UnsafeInvocationException, UnresolvedRefException, InvocationTargetException {
        return resolve().invoke(parameters);
    }

}

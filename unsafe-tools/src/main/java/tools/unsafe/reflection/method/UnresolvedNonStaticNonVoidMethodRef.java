package tools.unsafe.reflection.method;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;

public class UnresolvedNonStaticNonVoidMethodRef<C,T> extends UnresolvedRef<NonStaticNonVoidMethodRef<C,T>> {

    public UnresolvedNonStaticNonVoidMethodRef(NonStaticNonVoidMethodRef<C,T> ref, Throwable throwable) {
        super(ref, throwable);
    }

    public T invoke(C instance, Object... parameters) throws UnsafeInvocationException, UnresolvedRefException, InvocationTargetException {
        return resolve().invoke(instance, parameters);
    }

}

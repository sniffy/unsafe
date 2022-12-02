package tools.unsafe.reflection.method.unresolved;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.resolved.NonStaticMethodRef;

import java.lang.reflect.InvocationTargetException;

public class UnresolvedNonStaticMethodRef<C> extends UnresolvedRef<NonStaticMethodRef<C>> {

    public UnresolvedNonStaticMethodRef(NonStaticMethodRef<C> ref, Throwable throwable) {
        super(ref, throwable);
    }

    public <T> T invoke(C instance, Object... parameters) throws UnsafeInvocationException, UnresolvedRefException, InvocationTargetException {
        return resolve().invoke(instance, parameters);
    }

}

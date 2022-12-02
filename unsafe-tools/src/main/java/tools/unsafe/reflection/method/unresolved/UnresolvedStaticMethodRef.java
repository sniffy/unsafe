package tools.unsafe.reflection.method.unresolved;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.resolved.StaticMethodRef;

import java.lang.reflect.InvocationTargetException;

public class UnresolvedStaticMethodRef extends UnresolvedRef<StaticMethodRef> {

    public UnresolvedStaticMethodRef(StaticMethodRef ref, Throwable throwable) {
        super(ref, throwable);
    }

    public <T> T invoke(Object... parameters) throws UnsafeInvocationException, UnresolvedRefException, InvocationTargetException {
        return resolve().invoke(parameters);
    }

}

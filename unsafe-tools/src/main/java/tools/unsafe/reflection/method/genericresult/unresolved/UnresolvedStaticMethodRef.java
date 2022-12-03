package tools.unsafe.reflection.method.genericresult.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.genericresult.GenericMethodRef;
import tools.unsafe.reflection.method.genericresult.resolved.ResolvedStaticMethodRef;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class UnresolvedStaticMethodRef<C> extends AbstractUnresolvedMethodRef<ResolvedStaticMethodRef<C>, C> implements GenericMethodRef<C> {

    public UnresolvedStaticMethodRef(@Nullable ResolvedStaticMethodRef<C> ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    @Override
    public <T> T invoke(Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        try {
            return resolve().invoke(parameters);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }
}

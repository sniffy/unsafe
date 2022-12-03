package tools.unsafe.reflection.method.genericresult.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.genericresult.GenericDynamicMethodRef;
import tools.unsafe.reflection.method.genericresult.resolved.ResolvedDynamicMethodRef;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class UnresolvedDynamicMethodRef<C> extends AbstractUnresolvedMethodRef<ResolvedDynamicMethodRef<C>, C> implements GenericDynamicMethodRef<C> {

    public UnresolvedDynamicMethodRef(@Nullable ResolvedDynamicMethodRef<C> ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    @Override
    public <T> T invoke(C instance, Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        try {
            return resolve().invoke(instance, parameters);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }
}

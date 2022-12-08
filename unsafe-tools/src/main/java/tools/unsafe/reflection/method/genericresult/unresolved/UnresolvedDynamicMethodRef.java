package tools.unsafe.reflection.method.genericresult.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.genericresult.GenericDynamicMethodRef;
import tools.unsafe.reflection.method.genericresult.resolved.ResolvedDynamicMethodRef;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class UnresolvedDynamicMethodRef<C> extends AbstractUnresolvedMethodRef<ResolvedDynamicMethodRef<C>, C> implements GenericDynamicMethodRef<C> {

    public UnresolvedDynamicMethodRef(@Nonnull Callable<ResolvedDynamicMethodRef<C>> refSupplier) {
        super(refSupplier);
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

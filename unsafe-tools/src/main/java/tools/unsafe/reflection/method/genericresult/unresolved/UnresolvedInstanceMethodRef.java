package tools.unsafe.reflection.method.genericresult.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.genericresult.GenericMethodRef;
import tools.unsafe.reflection.method.genericresult.resolved.ResolvedInstanceMethodRef;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class UnresolvedInstanceMethodRef<C> extends AbstractUnresolvedMethodRef<ResolvedInstanceMethodRef<C>, C> implements GenericMethodRef<C> {

    public UnresolvedInstanceMethodRef(@Nonnull Callable<ResolvedInstanceMethodRef<C>> refSupplier) {
        super(refSupplier);
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

package tools.unsafe.reflection.method.typedresult.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.typedresult.GenericTypedMethodRef;
import tools.unsafe.reflection.method.typedresult.resolved.ResolvedStaticTypedMethodRef;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class UnresolvedStaticTypedMethodRef<C, T> extends AbstractUnresolvedMethodRef<ResolvedStaticTypedMethodRef<C, T>, C> implements GenericTypedMethodRef<C, T> {

    public UnresolvedStaticTypedMethodRef(@Nonnull Callable<ResolvedStaticTypedMethodRef<C, T>> refSupplier) {
        super(refSupplier);
    }

    @Override
    public T invoke(Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        try {
            return resolve().invoke(parameters);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

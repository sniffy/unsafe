package tools.unsafe.reflection.method.voidresult.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.voidresult.GenericVoidDynamicMethodRef;
import tools.unsafe.reflection.method.voidresult.resolved.ResolvedVoidDynamicMethodRef;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class UnresolvedVoidDynamicMethodRef<C> extends AbstractUnresolvedMethodRef<ResolvedVoidDynamicMethodRef<C>, C> implements GenericVoidDynamicMethodRef<C> {

    public UnresolvedVoidDynamicMethodRef(@Nonnull Callable<ResolvedVoidDynamicMethodRef<C>> refSupplier) {
        super(refSupplier);
    }

    @Override
    public void invoke(C instance, Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        try {
            resolve().invoke(instance, parameters);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }
}

package tools.unsafe.reflection.method.voidresult.oneparam.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.voidresult.oneparam.GenericVoidDynamicOneParamMethodRef;
import tools.unsafe.reflection.method.voidresult.oneparam.resolved.ResolvedVoidDynamicOneParamMethodRef;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class UnresolvedVoidDynamicOneParamMethodRef<C, P1> extends AbstractUnresolvedMethodRef<ResolvedVoidDynamicOneParamMethodRef<C, P1>, C> implements GenericVoidDynamicOneParamMethodRef<C, P1> {

    public UnresolvedVoidDynamicOneParamMethodRef(@Nonnull Callable<ResolvedVoidDynamicOneParamMethodRef<C, P1>> refSupplier) {
        super(refSupplier);
    }

    @Override
    public void invoke(C instance, P1 p1) throws UnsafeInvocationException, InvocationTargetException {
        try {
            resolve().invoke(instance, p1);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }
}

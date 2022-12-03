package tools.unsafe.reflection.method.voidresult.oneparam.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.voidresult.oneparam.GenericVoidDynamicOneParamMethodRef;
import tools.unsafe.reflection.method.voidresult.oneparam.resolved.ResolvedVoidDynamicOneParamMethodRef;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class UnresolvedVoidDynamicOneParamMethodRef<C, P1> extends AbstractUnresolvedMethodRef<ResolvedVoidDynamicOneParamMethodRef<C, P1>, C> implements GenericVoidDynamicOneParamMethodRef<C, P1> {

    public UnresolvedVoidDynamicOneParamMethodRef(@Nullable ResolvedVoidDynamicOneParamMethodRef<C, P1> ref, @Nullable Throwable throwable) {
        super(ref, throwable);
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

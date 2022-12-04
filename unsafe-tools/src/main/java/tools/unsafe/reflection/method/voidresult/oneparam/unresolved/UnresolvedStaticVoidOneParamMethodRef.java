package tools.unsafe.reflection.method.voidresult.oneparam.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.voidresult.oneparam.GenericVoidOneParamMethodRef;
import tools.unsafe.reflection.method.voidresult.oneparam.resolved.ResolvedStaticVoidOneParamMethodRef;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class UnresolvedStaticVoidOneParamMethodRef<C, P1> extends AbstractUnresolvedMethodRef<ResolvedStaticVoidOneParamMethodRef<C, P1>, C> implements GenericVoidOneParamMethodRef<C, P1> {

    public UnresolvedStaticVoidOneParamMethodRef(@Nullable ResolvedStaticVoidOneParamMethodRef<C, P1> ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    @Override
    public void invoke(P1 p1) throws UnsafeInvocationException, InvocationTargetException {
        try {
            resolve().invoke(p1);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }
}
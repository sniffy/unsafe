package tools.unsafe.reflection.method.voidresult.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.voidresult.GenericVoidMethodRef;
import tools.unsafe.reflection.method.voidresult.resolved.ResolvedStaticVoidMethodRef;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class UnresolvedStaticVoidMethodRef<C> extends AbstractUnresolvedMethodRef<ResolvedStaticVoidMethodRef<C>, C> implements GenericVoidMethodRef<C> {

    public UnresolvedStaticVoidMethodRef(@Nullable ResolvedStaticVoidMethodRef<C> ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    @Override
    public void invoke(Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        try {
            resolve().invoke(parameters);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }
}

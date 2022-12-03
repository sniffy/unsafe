package tools.unsafe.reflection.method.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.GenericTypedMethodRef;
import tools.unsafe.reflection.method.resolved.ResolvedInstanceTypedMethodRef;
import tools.unsafe.reflection.method.resolved.ResolvedStaticTypedMethodRef;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class UnresolvedInstanceTypedMethodRef<C,T> extends AbstractUnresolvedMethodRef<ResolvedInstanceTypedMethodRef<C,T>,C> implements GenericTypedMethodRef<C,T> {

    public UnresolvedInstanceTypedMethodRef(@Nullable ResolvedInstanceTypedMethodRef<C,T> ref, @Nullable Throwable throwable) {
        super(ref, throwable);
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

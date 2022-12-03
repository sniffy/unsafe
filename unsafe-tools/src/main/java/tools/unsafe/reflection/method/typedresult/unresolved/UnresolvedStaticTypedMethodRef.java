package tools.unsafe.reflection.method.typedresult.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.typedresult.GenericTypedMethodRef;
import tools.unsafe.reflection.method.typedresult.resolved.ResolvedStaticTypedMethodRef;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class UnresolvedStaticTypedMethodRef<C, T> extends AbstractUnresolvedMethodRef<ResolvedStaticTypedMethodRef<C, T>, C> implements GenericTypedMethodRef<C, T> {

    public UnresolvedStaticTypedMethodRef(@Nullable ResolvedStaticTypedMethodRef<C, T> ref, @Nullable Throwable throwable) {
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

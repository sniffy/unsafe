package tools.unsafe.reflection.method.typedresult.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.typedresult.GenericDynamicTypedMethodRef;
import tools.unsafe.reflection.method.typedresult.resolved.ResolvedDynamicTypedMethodRef;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class UnresolvedDynamicTypedMethodRef<C, T> extends AbstractUnresolvedMethodRef<ResolvedDynamicTypedMethodRef<C, T>, C> implements GenericDynamicTypedMethodRef<C, T> {

    public UnresolvedDynamicTypedMethodRef(@Nullable ResolvedDynamicTypedMethodRef<C, T> ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    @Override
    public T invoke(C instance, Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        try {
            return resolve().invoke(instance, parameters);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }
}
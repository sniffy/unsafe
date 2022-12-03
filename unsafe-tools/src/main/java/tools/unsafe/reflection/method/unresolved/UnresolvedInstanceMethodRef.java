package tools.unsafe.reflection.method.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.GenericMethodRef;
import tools.unsafe.reflection.method.resolved.ResolvedInstanceMethodRef;
import tools.unsafe.reflection.method.resolved.ResolvedStaticMethodRef;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class UnresolvedInstanceMethodRef<C> extends AbstractUnresolvedMethodRef<ResolvedInstanceMethodRef<C>,C> implements GenericMethodRef<C> {

    public UnresolvedInstanceMethodRef(@Nullable ResolvedInstanceMethodRef<C> ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    @Override
    public <T> T invoke(Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        try {
            return resolve().<T>invoke(parameters);
        } catch (UnresolvedRefException e) {
            throw new UnsafeInvocationException(e);
        }
    }
}

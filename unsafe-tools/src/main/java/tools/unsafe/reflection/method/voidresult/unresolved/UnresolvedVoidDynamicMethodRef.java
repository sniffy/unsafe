package tools.unsafe.reflection.method.voidresult.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.AbstractUnresolvedMethodRef;
import tools.unsafe.reflection.method.voidresult.GenericVoidDynamicMethodRef;
import tools.unsafe.reflection.method.voidresult.resolved.ResolvedVoidDynamicMethodRef;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class UnresolvedVoidDynamicMethodRef<C> extends AbstractUnresolvedMethodRef<ResolvedVoidDynamicMethodRef<C>, C> implements GenericVoidDynamicMethodRef<C> {

    public UnresolvedVoidDynamicMethodRef(@Nullable ResolvedVoidDynamicMethodRef<C> ref, @Nullable Throwable throwable) {
        super(ref, throwable);
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

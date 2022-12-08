package tools.unsafe.reflection.method;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public abstract class AbstractUnresolvedMethodRef<T extends AbstractMethodRef<C>, C> extends UnresolvedRef<T> implements UnresolvedMethodRef<C> {

    public AbstractUnresolvedMethodRef(@Nonnull Callable<T> refSupplier) {
        super(refSupplier);
    }

    @Override
    public @Nonnull ClassRef<C> getDeclaringClassRef() throws UnresolvedRefException {
        return resolve().getDeclaringClassRef();
    }

    @Override
    public @Nonnull Method getMethod() throws UnresolvedRefException {
        return resolve().getMethod();
    }

    @Override
    public @Nonnull Method getAccessibleMethod() throws UnsafeInvocationException, UnresolvedRefException {
        return resolve().getAccessibleMethod();
    }
}

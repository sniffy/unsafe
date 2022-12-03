package tools.unsafe.reflection.method;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class AbstractUnresolvedMethodRef<T extends AbstractMethodRef<C>, C> extends UnresolvedRef<T> implements UnresolvedMethodRef<C> {

    public AbstractUnresolvedMethodRef(@Nullable T ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    @Override
    public @Nonnull ClassRef<C> getDeclaringClassRef() throws UnresolvedRefException {
        return resolve().getDeclaringClassRef();
    }

    @Override
    public @Nonnull Method getMethod() throws UnresolvedRefException {
        return resolve().getMethod();
    }

}

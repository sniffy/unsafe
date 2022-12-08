package tools.unsafe.reflection.field;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.concurrent.Callable;

public abstract class AbstractUnresolvedFieldRef<T extends AbstractFieldRef<C>, C> extends UnresolvedRef<T> implements UnresolvedFieldRef<C> {

    public AbstractUnresolvedFieldRef(@Nonnull Callable<T> refSupplier) {
        super(refSupplier);
    }

    @Override
    public @Nonnull ClassRef<C> getDeclaringClassRef() throws UnresolvedRefException {
        return resolve().getDeclaringClassRef();
    }

    @Override
    public @Nonnull Field getField() throws UnresolvedRefException {
        return resolve().getField();
    }

}

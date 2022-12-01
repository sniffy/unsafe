package tools.unsafe.reflection.field;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public interface UnresolvedFieldRef<C> {

    @Nonnull ClassRef<C> getDeclaringClassRef() throws UnresolvedRefException;

    @Nonnull Field getField() throws UnresolvedRefException;

}

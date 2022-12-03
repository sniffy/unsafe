package tools.unsafe.reflection.field;

import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public interface FieldRef<C> extends UnresolvedFieldRef<C> {

    @Nonnull
    ClassRef<C> getDeclaringClassRef();

    @Nonnull
    Field getField();

}

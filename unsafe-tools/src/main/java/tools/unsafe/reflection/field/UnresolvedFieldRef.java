package tools.unsafe.reflection.field;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.clazz.ClassRef;

import java.lang.reflect.Field;

public interface UnresolvedFieldRef<C> {

    ClassRef<C> getDeclaringClassRef() throws UnresolvedRefException;

    Field getField() throws UnresolvedRefException;

    // TODO: introduce BooleanFieldRef and other similar classes

}

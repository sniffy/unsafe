package tools.unsafe.reflection.field;

import tools.unsafe.reflection.clazz.ClassRef;

import java.lang.reflect.Field;

public interface FieldRef<C> extends UnresolvedFieldRef<C> {

    ClassRef<C> getDeclaringClassRef();

    Field getField();

    // TODO: introduce BooleanFieldRef and other similar classes

}

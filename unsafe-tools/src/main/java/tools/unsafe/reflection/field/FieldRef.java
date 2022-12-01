package tools.unsafe.reflection.field;

import tools.unsafe.reflection.clazz.ClassRef;

import java.lang.reflect.Field;

public interface FieldRef<C> {

    ClassRef<C> getDeclaringClassRef();

    Field getField();

    // TODO: compare and set
    // TODO: introduce BooleanFieldRef and other similar classes

}

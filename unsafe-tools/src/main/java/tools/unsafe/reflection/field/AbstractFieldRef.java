package tools.unsafe.reflection.field;

import tools.unsafe.reflection.clazz.ClassRef;

import java.lang.reflect.Field;

public abstract class AbstractFieldRef<C> {

    protected final ClassRef<C> classRef;
    protected final Field field;

    public AbstractFieldRef(ClassRef<C> classRef, Field field) {
        this.classRef = classRef;
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public ClassRef<C> getDeclaringClassRef() {
        return classRef;
    }

}

package tools.unsafe.reflection.field.resolved;

import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.field.FieldRef;

import java.lang.reflect.Field;

public abstract class AbstractFieldRef<C> implements FieldRef<C> {

    protected final ClassRef<C> declaringClassRef;
    protected final Field field;

    public AbstractFieldRef(ClassRef<C> declaringClassRef, Field field) {
        this.declaringClassRef = declaringClassRef;
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public ClassRef<C> getDeclaringClassRef() {
        return declaringClassRef;
    }

}

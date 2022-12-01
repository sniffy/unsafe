package tools.unsafe.reflection.field;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;

import java.lang.reflect.Field;

public class ObjectFieldRef<C, T> implements FieldRef<C, T> {

    private final ClassRef<C> classRef;
    private final NonStaticFieldRef<C, T> nonStaticFieldRef;
    private final C object;

    public ObjectFieldRef(ClassRef<C> classRef, NonStaticFieldRef<C, T> nonStaticFieldRef, C object) {
        this.classRef = classRef;
        this.nonStaticFieldRef = nonStaticFieldRef;
        this.object = object;
    }

    @Override
    public ClassRef<C> getClassRef() {
        return classRef;
    }

    @Override
    public Field getField() {
        return nonStaticFieldRef.getField();
    }

    public T get() throws UnsafeInvocationException {
        return nonStaticFieldRef.get(object);
    }

    public void set(T value) throws UnsafeInvocationException {
        nonStaticFieldRef.set(object, value);
    }

}

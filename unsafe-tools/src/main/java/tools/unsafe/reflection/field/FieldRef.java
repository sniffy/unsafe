package tools.unsafe.reflection.field;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;

import java.lang.reflect.Field;

public interface FieldRef<C,T> {

    ClassRef<C> getClassRef();

    Field getField();

    T get() throws UnsafeInvocationException;

    void set(T value) throws UnsafeInvocationException;

    // TODO: compare and set
    // TODO: introduce BooleanFieldRef and other similar classes

}

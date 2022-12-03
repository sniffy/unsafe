package tools.unsafe.reflection.field.objects.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.field.AbstractFieldRef;
import tools.unsafe.reflection.field.objects.ObjectFieldRef;
import tools.unsafe.reflection.object.ObjectRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public abstract class AbstractObjectFieldRef<C, T> extends AbstractFieldRef<C> implements ObjectFieldRef<T> {

    public AbstractObjectFieldRef(ClassRef<C> classRef, Field field, long offset) {
        super(classRef, field, offset);
    }

    public abstract T get() throws UnsafeInvocationException;

    @Override
    @Nonnull
    public ObjectRef<T> objectRef() throws UnsafeInvocationException {
        T value = get();
        Class<T> clazz;
        if (null == value) {
            //noinspection unchecked
            clazz = (Class<T>) field.getType();
        } else {
            //noinspection unchecked
            clazz = (Class<T>) value.getClass();
        }
        return new ObjectRef<T>(
                new ClassRef<T>(clazz),
                value
        );
    }

    @Nonnull
    public T getNotNull(@Nonnull T defaultValue) throws UnsafeInvocationException {
        T value = get();
        return null == value ? defaultValue : value;
    }

    @Override
    public abstract void set(T value) throws UnsafeInvocationException;

}

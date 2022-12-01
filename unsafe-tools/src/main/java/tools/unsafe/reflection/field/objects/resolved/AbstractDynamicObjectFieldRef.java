package tools.unsafe.reflection.field.objects.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.field.AbstractFieldRef;
import tools.unsafe.reflection.field.objects.DynamicObjectFieldRef;
import tools.unsafe.reflection.object.ObjectRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public abstract class AbstractDynamicObjectFieldRef<C,T> extends AbstractFieldRef<C> implements DynamicObjectFieldRef<C, T> {

    public AbstractDynamicObjectFieldRef(ClassRef<C> classRef, Field field, long offset) {
        super(classRef, field, offset);
    }

    public abstract T get(C instance) throws UnsafeInvocationException;

    @Override
    @Nonnull
    public ObjectRef<T> objectRef(C instance) throws UnsafeInvocationException {
        T value = get(instance);
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
    public T getNotNull(C instance, @Nonnull T defaultValue) throws UnsafeInvocationException {
        T value = get(instance);
        return null == value ? defaultValue : value;
    }

    @Override
    public abstract void set(C instance, T value) throws UnsafeInvocationException;


    public void copy(@Nonnull C from, @Nonnull C to) throws UnsafeInvocationException {
        set(to, get(from));
    }

}

package tools.unsafe;

import java.lang.reflect.Field;

abstract public class StaticFinalObjectFieldRef<T> extends StaticObjectFieldRef<T> {

    public StaticFinalObjectFieldRef(Field field) {
        super(field);
    }

    @Override
    public void set(T value) {
        try {
            set(Unsafe.getSunMiscUnsafe(), value);
        } catch (NoSuchFieldException e) {
            throw Unsafe.throwException(e);
        }
    }

    public abstract void set(sun.misc.Unsafe unsafe, T value) throws NoSuchFieldException;

}

package tools.unsafe;

import java.lang.reflect.Field;

abstract public class StaticFinalObjectFieldRef<T> extends StaticObjectFieldRef<T> {

    @Deprecated
    protected final Class clazzField;
    @Deprecated
    protected final long offsetField;

    /*public StaticFinalObjectFieldRef(Field field) {
        super(field);
    }*/

    public StaticFinalObjectFieldRef(Field field, Class clazzField, long offsetField) {
        super(field);
        this.clazzField = clazzField;
        this.offsetField = offsetField;
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

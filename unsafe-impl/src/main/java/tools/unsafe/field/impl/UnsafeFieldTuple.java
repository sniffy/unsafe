package tools.unsafe.field.impl;

import java.lang.reflect.Field;

public class UnsafeFieldTuple {

    public final Field field;
    public final Object base;
    public final long offset;

    public UnsafeFieldTuple(Field field, Object base, long offset) {
        this.field = field;
        this.base = base;
        this.offset = offset;
    }

}

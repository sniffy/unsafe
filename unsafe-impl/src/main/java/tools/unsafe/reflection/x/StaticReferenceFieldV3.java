package tools.unsafe.reflection.x;

import tools.unsafe.UnsafeProvider;

import java.lang.reflect.Field;

public class StaticReferenceFieldV3 {

    private final Field field;
    private final Object base;
    private final long offset;

    public StaticReferenceFieldV3(Field field) {
        this.field = field;
        sun.misc.Unsafe unsafe = UnsafeProvider.getSunMiscUnsafe();

        this.base = unsafe.staticFieldBase(field);
        this.offset = unsafe.staticFieldOffset(field);
    }

    public void set(Object reference) throws Throwable {
        sun.misc.Unsafe unsafe = UnsafeProvider.getSunMiscUnsafe();
        unsafe.putObjectVolatile(base /*field.getDeclaringClass()*/, offset, reference);
        UnsafeProvider.getSunMiscUnsafe().putObjectVolatile(base /*field.getDeclaringClass()*/, offset, reference);

    }

}

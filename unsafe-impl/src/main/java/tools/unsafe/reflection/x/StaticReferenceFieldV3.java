package tools.unsafe.reflection.x;

import tools.unsafe.Unsafe;
import tools.unsafe.UnsafeProvider;

import java.lang.reflect.Field;

public class StaticReferenceFieldV3 {

    private final Field field;
    private final Object base;
    private final long offset;

    public StaticReferenceFieldV3(Field field) {
        this.field = field;
        this.base = Unsafe.getSunMiscUnsafe().staticFieldBase(field);
        this.offset = Unsafe.getSunMiscUnsafe().staticFieldOffset(field);
    }

    public void set(Object reference) throws Throwable {
        Unsafe.getSunMiscUnsafe().putObjectVolatile(base /*field.getDeclaringClass()*/, offset, reference);
        UnsafeProvider.getSunMiscUnsafe().putObjectVolatile(base /*field.getDeclaringClass()*/, offset, reference);

    }

}

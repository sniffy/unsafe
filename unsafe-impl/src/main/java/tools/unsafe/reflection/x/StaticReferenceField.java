package tools.unsafe.reflection.x;

import tools.unsafe.UnsafeProvider;
import tools.unsafe.field.FieldSupplier;
import tools.unsafe.reflection.FieldBaseSupplier;
import tools.unsafe.reflection.FieldOffsetSupplier;

import java.lang.reflect.Field;

public class StaticReferenceField {

    private final FieldSupplier fieldSupplier;
    private final FieldBaseSupplier fieldBaseSupplier;
    private final FieldOffsetSupplier fieldOffsetSupplier;

    public StaticReferenceField(FieldSupplier fieldSupplier, FieldBaseSupplier fieldBaseSupplier, FieldOffsetSupplier fieldOffsetSupplier) {
        this.fieldSupplier = fieldSupplier;
        this.fieldBaseSupplier = fieldBaseSupplier;
        this.fieldOffsetSupplier = fieldOffsetSupplier;
    }

    public void set(Object reference) throws Throwable {
        sun.misc.Unsafe unsafe = UnsafeProvider.getSunMiscUnsafe();
        Field field = fieldSupplier.call();
        Object fieldBase = fieldBaseSupplier.base(unsafe);
        long fieldOffset = fieldOffsetSupplier.offset(unsafe);
        UnsafeProvider.getSunMiscUnsafe().putObjectVolatile(fieldBase /*field.getDeclaringClass()*/, fieldOffset, reference);
    }

}

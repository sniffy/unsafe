package tools.unsafe.reflection;

import sun.misc.Unsafe;
import tools.unsafe.UnsafeProvider;

import java.lang.reflect.Field;

public class ReferenceFieldHandle<T> {

    private final Field field;

    private final Object base;
    private final long offset;

    private final Unsafe unsafe;

    private final Throwable throwable;

    public ReferenceFieldHandle(FieldSupplier fieldSupplier) {
        this(fieldSupplier, true);
    }

    public ReferenceFieldHandle(FieldSupplier fieldSupplier, boolean resolve) {

        Field field;
        Object base;
        long offset;
        Unsafe unsafe;
        Throwable throwable;
        try {
            field = fieldSupplier.call();
            unsafe = UnsafeProvider.getSunMiscUnsafe();
            base = unsafe.staticFieldBase(field);
            offset = unsafe.staticFieldOffset(field);
            throwable = null;
        } catch (Throwable e) {
            field = null;
            unsafe = null;
            base = null;
            offset = 0;
            throwable = e;
        }

        this.field = field;
        this.base = base;
        this.offset = offset;
        this.unsafe = unsafe;
        this.throwable = throwable;

        if (resolve && null != throwable) {
            // TODO: throw throwable
        }
    }

    public ReferenceFieldHandle(Field field) {
        this(field, true);
    }

    public ReferenceFieldHandle(Field field, boolean resolve) {
        this.field = field;
        Object base;
        long offset;
        Unsafe unsafe;
        Throwable throwable;
        try {
            unsafe = UnsafeProvider.getSunMiscUnsafe();
            base = unsafe.staticFieldBase(field);
            offset = unsafe.staticFieldOffset(field);
            throwable = null;
        } catch (Throwable e) {
            unsafe = null;
            base = null;
            offset = 0;
            throwable = e;
        }

        this.base = base;
        this.offset = offset;
        this.unsafe = unsafe;
        this.throwable = throwable;

        if (resolve && null != throwable) {
            // TODO: throw throwable
        }

    }

    /*public static void main(String[] args) throws Exception {

        VarHandle vh = null;

        vh.toString()

    }*/

    public T get() {
        UnsafeProvider.getSunMiscUnsafe().ensureClassInitialized(field.getDeclaringClass());

        // TODO: throw throwable if it is not null
        return (T) unsafe.getObject(base, offset);
    }

    public void set(T value) {
        UnsafeProvider.getSunMiscUnsafe().ensureClassInitialized(field.getDeclaringClass());

        unsafe.putObject(base, offset, value);
    }

    public <VH> VH asVarHandle() {
        return null;
    }

    public Field asField() {
        return field;
    }

}

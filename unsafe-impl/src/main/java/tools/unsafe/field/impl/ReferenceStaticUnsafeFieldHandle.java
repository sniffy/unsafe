package tools.unsafe.field.impl;

import sun.misc.Unsafe;
import tools.unsafe.UnsafeProvider;
import tools.unsafe.field.FieldSupplier;

import java.lang.reflect.Field;

public class ReferenceStaticUnsafeFieldHandle<T> extends AbstractUnsafeFieldHandle {

    private Object base;
    private long offset;

    public ReferenceStaticUnsafeFieldHandle(FieldSupplier fieldSupplier) {
        super(fieldSupplier);
        try {
            Field call = fieldSupplier.call();
            Unsafe unsafe = UnsafeProvider.getSunMiscUnsafe();
            base = unsafe.staticFieldBase(call);
            offset = unsafe.staticFieldOffset(call);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object fieldBase(Field field) {
        return unsafe().staticFieldBase(field);
    }

    @Override
    protected long fieldOffset(Field field) {
        return unsafe().staticFieldOffset(field);
    }

    public T get() {
        // TODO: throw throwable if it is not null
        UnsafeFieldTuple fieldTuple = resolve();
        //noinspection unchecked
        return (T) unsafe().getObject(base, offset);
        //return (T) unsafe().getObject(fieldTuple.base, fieldTuple.offset);
    }

    public void set(T value) {
        UnsafeFieldTuple fieldTuple = resolve();
        unsafe().putObject(base, offset, value);
        //unsafe().putObject(fieldTuple.base, fieldTuple.offset, value);
    }

}

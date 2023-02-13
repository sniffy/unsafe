package tools.unsafe.field.impl.unsafe;

import tools.unsafe.field.FieldSupplier;
import tools.unsafe.field.impl.ReferenceStaticFieldHandleImpl;
import tools.unsafe.vm.UnsafeVirtualMachine;
import tools.unsafe.vm.VirtualMachineFamily;

import java.lang.reflect.Field;

public class ReferenceStaticUnsafeFieldHandle<T> extends AbstractUnsafeFieldHandle implements ReferenceStaticFieldHandleImpl<T> {

    public ReferenceStaticUnsafeFieldHandle(FieldSupplier fieldSupplier) {
        super(fieldSupplier);
    }

    @Override
    protected Object fieldBase(Field field) {
        try {
            return unsafe().staticFieldBase(field);
        } catch (Error e) {
            e.printStackTrace();
            return field.getDeclaringClass();
        }
    }

    @Override
    protected long fieldOffset(Field field) {
        try {
            return unsafe().staticFieldOffset(field);
        } catch (Error e) {
            e.printStackTrace();
            return unsafe().objectFieldOffset(field);
        }
    }

    public T get() {
        // TODO: throw throwable if it is not null
        UnsafeFieldTuple fieldTuple = resolve();
        //noinspection unchecked
        return (T) unsafe().getObject(fieldTuple.base, fieldTuple.offset);
    }

    public void set(T value) {
        UnsafeFieldTuple fieldTuple = resolve();
        unsafe().putObject(fieldTuple.base, fieldTuple.offset, value);
    }

}

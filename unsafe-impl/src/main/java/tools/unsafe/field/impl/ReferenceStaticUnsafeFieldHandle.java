package tools.unsafe.field.impl;

import tools.unsafe.field.FieldSupplier;
import tools.unsafe.vm.UnsafeVirtualMachine;
import tools.unsafe.vm.VirtualMachineFamily;

import java.lang.reflect.Field;

public class ReferenceStaticUnsafeFieldHandle<T> extends AbstractUnsafeFieldHandle {

    public ReferenceStaticUnsafeFieldHandle(FieldSupplier fieldSupplier) {
        super(fieldSupplier);
    }

    @Override
    protected Object fieldBase(Field field) {
        try {
            if (VirtualMachineFamily.ANDROID != UnsafeVirtualMachine.getFamily()) {
                return field.getDeclaringClass();
            } else {
                return unsafe().staticFieldBase(field);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return field.getDeclaringClass();
        }
    }

    @Override
    protected long fieldOffset(Field field) {
        if (VirtualMachineFamily.ANDROID != UnsafeVirtualMachine.getFamily()) {
            return unsafe().objectFieldOffset(field);
        } else {
            return unsafe().staticFieldOffset(field);
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

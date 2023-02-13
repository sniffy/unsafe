package tools.unsafe.field.impl.reflection;

import tools.unsafe.field.FieldSupplier;
import tools.unsafe.field.impl.AbstractFieldHandleImpl;
import tools.unsafe.field.impl.ReferenceStaticFieldHandleImpl;
import tools.unsafe.field.impl.unsafe.AbstractUnsafeFieldHandle;
import tools.unsafe.field.impl.unsafe.UnsafeFieldTuple;
import tools.unsafe.vm.UnsafeVirtualMachine;
import tools.unsafe.vm.VirtualMachineFamily;

import java.lang.reflect.Field;

public class ReferenceStaticReflectionFieldHandle<T> implements ReferenceStaticFieldHandleImpl<T> {

    private final FieldSupplier fieldSupplier;

    public ReferenceStaticReflectionFieldHandle(FieldSupplier fieldSupplier) {
        System.out.println("Using ReferenceStaticReflectionFieldHandle");
        this.fieldSupplier = fieldSupplier;
    }

    public T get() {
        // TODO: add resolve() here
        try {
            return (T) fieldSupplier.call().get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void set(T value) {
        try {
            fieldSupplier.call().set(null, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Field asField() {
        try {
            return fieldSupplier.call();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object resolve() {
        try {
            return fieldSupplier.call();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

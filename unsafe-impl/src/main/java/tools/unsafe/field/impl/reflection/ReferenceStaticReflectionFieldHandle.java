package tools.unsafe.field.impl.reflection;

import tools.unsafe.field.FieldSupplier;
import tools.unsafe.field.impl.AbstractFieldHandleImpl;
import tools.unsafe.field.impl.ReferenceStaticFieldHandleImpl;
import tools.unsafe.field.impl.unsafe.AbstractUnsafeFieldHandle;
import tools.unsafe.field.impl.unsafe.UnsafeFieldTuple;
import tools.unsafe.vm.UnsafeVirtualMachine;
import tools.unsafe.vm.VirtualMachineFamily;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReferenceStaticReflectionFieldHandle<T> implements ReferenceStaticFieldHandleImpl<T> {

    private final FieldSupplier fieldSupplier;

    public ReferenceStaticReflectionFieldHandle(FieldSupplier fieldSupplier) {
        System.out.println("Using ReferenceStaticReflectionFieldHandle");
        this.fieldSupplier = fieldSupplier;
    }

    public T get() {
        // TODO: add resolve() here
        try {
            return (T) resolve().get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void set(T value) {
        try {
            resolve().set(null, value);
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
    public Field resolve() {
        try {
            Field field = fieldSupplier.call();

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            try {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.set(field, field.getModifiers() & ~Modifier.FINAL);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                try {
                    Field modifiersField = Field.class.getDeclaredField("accessFlags");
                    modifiersField.setAccessible(true);
                    modifiersField.set(field, field.getModifiers() & ~Modifier.FINAL);
                } catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                }
            }

            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

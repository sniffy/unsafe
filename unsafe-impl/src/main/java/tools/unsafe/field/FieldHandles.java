package tools.unsafe.field;

import tools.unsafe.field.impl.ReferenceStaticFieldHandleImpl;
import tools.unsafe.field.impl.reflection.ReferenceStaticReflectionFieldHandle;
import tools.unsafe.field.impl.unsafe.ReferenceStaticUnsafeFieldHandle;
import tools.unsafe.vm.UnsafeVirtualMachine;

import java.lang.reflect.Field;

import static tools.unsafe.vm.VirtualMachineFamily.ANDROID;

public class FieldHandles {

    public static <T> ReferenceStaticFieldHandle<T> staticReferenceField(FieldSupplier fieldSupplier) {
        return new ReferenceStaticFieldHandle<T>(fieldSupplier);
    }

    public static <T> ReferenceStaticFieldHandle<T> staticReferenceField(FieldSupplier fieldSupplier, boolean resolve) {
        return new ReferenceStaticFieldHandle<T>(fieldSupplier, resolve);
    }

    public static class ReferenceStaticFieldHandle<T> extends AbstractFieldHandle<ReferenceStaticFieldHandleImpl<T>> {

        public ReferenceStaticFieldHandle(FieldSupplier fieldSupplier) {
            super(fieldSupplier);
        }

        public ReferenceStaticFieldHandle(FieldSupplier fieldSupplier, boolean resolve) {
            super(fieldSupplier, resolve);
        }

        public ReferenceStaticFieldHandle(Field field) {
            super(field);
        }

        public ReferenceStaticFieldHandle(Field field, boolean resolve) {
            super(field, resolve);
        }

        @Override
        protected ReferenceStaticFieldHandleImpl<T> createImpl(FieldSupplier fieldSupplier) {
            System.out.println("UnsafeVirtualMachine.getFamily()=" + UnsafeVirtualMachine.getFamily());
            if (ANDROID == UnsafeVirtualMachine.getFamily()) {
                return new ReferenceStaticReflectionFieldHandle<T>(fieldSupplier);
            } else {
                return new ReferenceStaticUnsafeFieldHandle<T>(fieldSupplier);
            }
        }

        public T get() {
            return impl.get();
        }

        public void set(T value) {
            impl.set(value);
        }

    }

}

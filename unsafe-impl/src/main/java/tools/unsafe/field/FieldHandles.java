package tools.unsafe.field;

import tools.unsafe.field.impl.ReferenceStaticUnsafeFieldHandle;

import java.lang.reflect.Field;

public class FieldHandles {

    public static <T> ReferenceStaticFieldHandle<T> staticReferenceField(FieldSupplier fieldSupplier) {
        return new ReferenceStaticFieldHandle<T>(fieldSupplier);
    }

    public static <T> ReferenceStaticFieldHandle<T> staticReferenceField(FieldSupplier fieldSupplier, boolean resolve) {
        return new ReferenceStaticFieldHandle<T>(fieldSupplier, resolve);
    }

    public static class ReferenceStaticFieldHandle<T> extends AbstractFieldHandle<ReferenceStaticUnsafeFieldHandle<T>> {

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
        protected ReferenceStaticUnsafeFieldHandle<T> createImpl(FieldSupplier fieldSupplier) {
            return new ReferenceStaticUnsafeFieldHandle<>(fieldSupplier);
        }

        public T get() {
            return impl.get();
        }

        public void set(T value) {
            impl.set(value);
        }

    }

}

package tools.unsafe.field;

import tools.unsafe.field.impl.ReferenceStaticUnsafeFieldHandle;

import java.lang.reflect.Field;

public class ReferenceFieldHandle<T> {

    private final ReferenceStaticUnsafeFieldHandle<T> impl;

    public ReferenceFieldHandle(FieldSupplier fieldSupplier) {
        this(fieldSupplier, true); // TODO: default should be false
    }

    public ReferenceFieldHandle(FieldSupplier fieldSupplier, boolean resolve) {
        impl = new ReferenceStaticUnsafeFieldHandle<>(fieldSupplier);
        if (resolve) {
            impl.resolve();
        }
    }

    public ReferenceFieldHandle(Field field) {
        this(new CompletedFieldSupplier(field));
    }

    public ReferenceFieldHandle(Field field, boolean resolve) {
        this(new CompletedFieldSupplier(field), resolve);
    }

    public T get() {
        return impl.get();
    }

    public void set(T value) {
        impl.set(value);
    }

    public <VH> VH asVarHandle() {
        return null;
    }

    public Field asField() {
        return impl.resolve().field;
    }

}

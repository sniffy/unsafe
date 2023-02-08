package tools.unsafe.field;

import tools.unsafe.field.impl.AbstractUnsafeFieldHandle;

import java.lang.reflect.Field;

public abstract class AbstractFieldHandle<IMPL extends AbstractUnsafeFieldHandle> {

    protected final IMPL impl;

    public AbstractFieldHandle(FieldSupplier fieldSupplier) {
        this(fieldSupplier, true); // TODO: default should be false
    }

    public AbstractFieldHandle(FieldSupplier fieldSupplier, boolean resolve) {
        impl = createImpl(fieldSupplier);
        if (resolve) {
            impl.resolve();
        }
    }

    public AbstractFieldHandle(Field field) {
        this(new CompletedFieldSupplier(field));
    }

    public AbstractFieldHandle(Field field, boolean resolve) {
        this(new CompletedFieldSupplier(field), resolve);
    }

    protected abstract IMPL createImpl(FieldSupplier fieldSupplier);

    public <VH> VH asVarHandle() {
        return null;
    }

    public Field asField() {
        return impl.resolve().field;
    }


}

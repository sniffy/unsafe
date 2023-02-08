package tools.unsafe.field.impl;

import tools.unsafe.UnsafeProvider;
import tools.unsafe.field.FieldSupplier;
import tools.unsafe.field.UnresolvedRef;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;

public abstract class AbstractUnsafeFieldHandle implements Callable<UnsafeFieldTuple> {

    private final FieldSupplier fieldSupplier;
    private final UnresolvedRef<UnsafeFieldTuple> unresolvedRef;

    public AbstractUnsafeFieldHandle(FieldSupplier fieldSupplier) {
        this.fieldSupplier = fieldSupplier;
        unresolvedRef = new UnresolvedRef<UnsafeFieldTuple>(this);
    }

    @Override
    public UnsafeFieldTuple call() throws Exception {
        Field field = fieldSupplier.call();
        unsafe().ensureClassInitialized(field.getDeclaringClass());
        Object base = fieldBase(field);
        long offset = fieldOffset(field);
        return new UnsafeFieldTuple(field, base, offset);
    }

    protected abstract Object fieldBase(Field field);

    protected abstract long fieldOffset(Field field);

    protected static sun.misc.Unsafe unsafe() {
        return UnsafeProvider.getSunMiscUnsafe();
    }

    public UnsafeFieldTuple resolve() {
        // TODO: do unsafe.ensureClassInitialized(call.getDeclaringClass());
        return unresolvedRef.resolve();
    }

}

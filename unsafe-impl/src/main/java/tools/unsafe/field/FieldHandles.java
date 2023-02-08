package tools.unsafe.field;

public class FieldHandles {

    public static <T> ReferenceFieldHandle<T> staticReferenceField(FieldSupplier fieldSupplier) {
        return new ReferenceFieldHandle<T>(fieldSupplier);
    }

}

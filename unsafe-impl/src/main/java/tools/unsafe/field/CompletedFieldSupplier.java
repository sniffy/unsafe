package tools.unsafe.field;

import java.lang.reflect.Field;

public class CompletedFieldSupplier implements FieldSupplier {

    private final Field field;

    public CompletedFieldSupplier(Field field) {
        this.field = field;
    }

    @Override
    public Field call() throws Exception {
        return field;
    }

}

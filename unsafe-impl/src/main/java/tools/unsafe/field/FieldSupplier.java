package tools.unsafe.field;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;

public interface FieldSupplier extends Callable<Field> {

    Field call() throws Exception;

}

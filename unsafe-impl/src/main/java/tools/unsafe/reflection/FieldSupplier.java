package tools.unsafe.reflection;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.concurrent.Callable;

public interface FieldSupplier extends Serializable, Callable<Field> {

    Field call() throws Exception;

}

package tools.unsafe.reflection;

import java.lang.reflect.Field;

public interface FieldSupplier {

    Field field() throws Throwable;

}

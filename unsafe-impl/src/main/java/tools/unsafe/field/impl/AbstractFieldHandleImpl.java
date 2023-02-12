package tools.unsafe.field.impl;

import java.lang.reflect.Field;

public interface AbstractFieldHandleImpl {

    Field asField();

    Object resolve();

}

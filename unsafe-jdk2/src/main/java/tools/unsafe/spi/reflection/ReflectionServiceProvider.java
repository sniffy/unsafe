package tools.unsafe.spi.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

public interface ReflectionServiceProvider {

    boolean makeAccessible(AccessibleObject ao);

    boolean removeFinalModifier(Field field);

}

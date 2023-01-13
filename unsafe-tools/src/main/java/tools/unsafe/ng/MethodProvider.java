package tools.unsafe.ng;

import java.lang.reflect.Method;

public interface MethodProvider {

    Method apply(Class<?> t, Class<?>[] u) throws NoSuchMethodException, SecurityException;

}


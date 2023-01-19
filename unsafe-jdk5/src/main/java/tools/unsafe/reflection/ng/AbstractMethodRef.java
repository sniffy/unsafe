package tools.unsafe.reflection.ng;

import tools.unsafe.Exceptions;
import tools.unsafe.Reflections;

import java.lang.reflect.Method;

public abstract class AbstractMethodRef extends ExecutableRef {

    protected final Method method;

    public AbstractMethodRef(Method method) {
        try {
            Reflections.setAccessible(method); // TODO: make it lazy
        } catch (Exception e) {
            throw Exceptions.throwException(e);
        }
        this.method = method;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }

    protected void validateReturnClass(Class<?> clazz) {
        if (!method.getReturnType().isAssignableFrom(clazz)) {
            throw new WrongMethodSignatureException();
        }
    }

}

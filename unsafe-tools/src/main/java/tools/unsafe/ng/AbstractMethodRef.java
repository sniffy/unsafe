package tools.unsafe.ng;

import tools.unsafe.Unsafe;

import java.lang.reflect.Method;

public abstract class AbstractMethodRef extends ExecutableRef {

    protected final Method method;

    public AbstractMethodRef(Method method) {
        try {
            Unsafe.setAccessible(method); // TODO: make it lazy
        } catch (Exception e) {
            throw Unsafe.throwException(e);
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

package tools.unsafe.ng.impl;

import tools.unsafe.Unsafe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionMethodInvoker implements MethodInvoker {

    private final Method method;

    public ReflectionMethodInvoker(Method method) {
        this.method = method;
    }

    @Override
    public Object invoke(Object obj, Object... args) {
        try {
            return method.invoke(null, args);
        } catch (InvocationTargetException e) {
            throw Unsafe.throwException(e.getTargetException());
        } catch (Exception e) {
            throw Unsafe.throwException(e);
        }
    }

}

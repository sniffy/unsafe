package tools.unsafe.ng.impl;

import tools.unsafe.Unsafe;
import tools.unsafe.ng.Methods;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodHandleMethodInvoker implements MethodInvoker {

    private final MethodHandle methodHandle;

    public MethodHandleMethodInvoker(Method method) {
        try {
            this.methodHandle = Methods.privateLookupIn(method.getDeclaringClass()).unreflect(method);
        } catch (IllegalAccessException e) {
            throw Unsafe.throwException(e);
        }
    }

    @Override
    public Object invoke(Object obj, Object... args) {
        try {
            return methodHandle.invokeWithArguments(args);
        } catch (InvocationTargetException e) {
            throw Unsafe.throwException(e.getTargetException());
        } catch (Throwable e) {
            throw Unsafe.throwException(e);
        }
    }

}

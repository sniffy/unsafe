package tools.unsafe.spi.invoke;

import tools.unsafe.Exceptions;
import tools.unsafe.internal.UnsafeToolsLogging;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionMethodInvoker implements MethodInvoker {

    private final Method method;

    public ReflectionMethodInvoker(Method method) {
        this.method = method;
    }

    //@Override
    public Object invoke(Object obj, Object[] args) {
        try {
            return method.invoke(null, args);
        } catch (InvocationTargetException e) {
            throw Exceptions.throwException(e.getTargetException());
        } catch (Exception e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
            throw Exceptions.throwException(e);
        }
    }

}

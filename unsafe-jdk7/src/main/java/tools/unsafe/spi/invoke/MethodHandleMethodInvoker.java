package tools.unsafe.spi.invoke;

import tools.unsafe.Exceptions;
import tools.unsafe.Lookups;
import tools.unsafe.internal.UnsafeToolsLogging;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodHandleMethodInvoker implements MethodInvoker {

    private final MethodHandle methodHandle;

    public MethodHandleMethodInvoker(Method method) {
        try {
            this.methodHandle = Lookups.trustedLookup().unreflect(method);
        } catch (Throwable e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
            throw Exceptions.throwException(e);
        }
    }

    @Override
    public Object invoke(Object obj, Object... args) {
        try {
            return methodHandle.invokeWithArguments(args);
        } catch (InvocationTargetException e) {
            throw Exceptions.throwException(e.getTargetException());
        } catch (Throwable e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
            throw Exceptions.throwException(e);
        }
    }

}

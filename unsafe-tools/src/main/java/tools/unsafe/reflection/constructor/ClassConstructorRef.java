package tools.unsafe.reflection.constructor;

import tools.unsafe.ConstructorInvoker;
import tools.unsafe.InternalUnsafeException;
import tools.unsafe.Unsafe;
import tools.unsafe.UnsafeToolsSPIs;
import tools.unsafe.reflection.UnsafeInvocationException;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassConstructorRef<C> {

    private final Constructor<C> constructor;

    public ClassConstructorRef(Constructor<C> constructor) {
        this.constructor = constructor;
        // TODO: should we make it accessible here ?
    }

    public C newInstanceOrNull(Object... parameters) {
        try {
            return newInstance();
        } catch (Throwable e) {
            return null;
        }
    }

    public C newInstance(Object... parameters) throws UnsafeInvocationException {
        try {
            return constructor.newInstance(parameters);
        } catch (InvocationTargetException e) {
            throw Unsafe.throwException(e.getTargetException());
        } catch (Throwable e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public void invokeOnInstance(@Nonnull C instance, Object... parameters) throws UnsafeInvocationException {
        try {
            ConstructorInvoker<C> constructorInvoker = UnsafeToolsSPIs.getConstructorInvokerFactory().
                    createConstructorInvoker(constructor.getDeclaringClass(), constructor.getParameterTypes());
            constructorInvoker.invoke(instance, parameters);
        } catch (InternalUnsafeException e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

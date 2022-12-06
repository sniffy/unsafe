package tools.unsafe.reflection.constructor;

import tools.unsafe.ConstructorInvoker;
import tools.unsafe.InternalUnsafeException;
import tools.unsafe.Unsafe;
import tools.unsafe.UnsafeToolsSPIs;
import tools.unsafe.reflection.UnsafeInvocationException;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ZeroArgsClassConstructorRef<C> {

    private final Constructor<C> constructor;

    // TODO: pass ClassRef here
    public ZeroArgsClassConstructorRef(Constructor<C> constructor) {
        this.constructor = constructor;
        // TODO: should we make it accessible here ?
    }

    public C newInstanceOrNull() {
        try {
            return newInstance();
        } catch (Throwable e) {
            return null;
        }
    }

    public C newInstance() throws UnsafeInvocationException {
        try {
            return constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw Unsafe.throwException(e.getTargetException());
        } catch (Throwable e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public void invokeOnInstance(@Nonnull C instance) throws UnsafeInvocationException {
        try {
            ConstructorInvoker<C> constructorInvoker = UnsafeToolsSPIs.getConstructorInvokerFactory().
                    createConstructorInvoker(constructor.getDeclaringClass());
            constructorInvoker.invoke(instance);
        } catch (InternalUnsafeException e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

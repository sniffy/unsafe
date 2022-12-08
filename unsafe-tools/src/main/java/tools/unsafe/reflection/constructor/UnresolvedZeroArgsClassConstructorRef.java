package tools.unsafe.reflection.constructor;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public class UnresolvedZeroArgsClassConstructorRef<C> extends UnresolvedRef<ZeroArgsClassConstructorRef<C>> {

    public UnresolvedZeroArgsClassConstructorRef(@Nonnull Callable<ZeroArgsClassConstructorRef<C>> refSupplier) {
        super(refSupplier);
    }

    public C newInstanceOrNull() {
        try {
            return newInstance();
        } catch (Throwable e) {
            return null;
        }
    }

    public C newInstance() throws UnsafeInvocationException, UnresolvedRefException {
        return resolve().newInstance();
    }

    public void invokeOnInstance(@Nonnull C instance) throws UnsafeInvocationException, UnresolvedRefException {
        resolve().invokeOnInstance(instance);
    }

}

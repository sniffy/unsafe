package tools.unsafe.reflection.method;

import tools.unsafe.Unsafe;
import tools.unsafe.reflection.UnsafeException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public abstract class AbstractMethodRef<C> implements MethodRef<C> {

    private final ClassRef<C> declaringClassRef;
    private final Method method;

    public AbstractMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method) {
        this.declaringClassRef = declaringClassRef;
        this.method = method;

        //noinspection ConstantConditions
        assert null != declaringClassRef;
        //noinspection ConstantConditions
        assert null != method;
    }

    public @Nonnull Method getAccessibleMethod() throws UnsafeInvocationException {
        if (!method.isAccessible()) {
            try {
                Unsafe.setAccessible(method);
            } catch (UnsafeException e) {
                throw new UnsafeInvocationException(e);
            }
        }

        return method;
    }

    public @Nonnull Method getMethod() {
        return method;
    }

    public @Nonnull ClassRef<C> getDeclaringClassRef() {
        return declaringClassRef;
    }

}

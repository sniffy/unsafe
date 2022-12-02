package tools.unsafe.reflection.method;

import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public abstract class AbstractMethodRef<C> implements MethodRef<C> {

    protected final ClassRef<C> declaringClassRef;
    protected final Method method;

    public AbstractMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method) {
        this.declaringClassRef = declaringClassRef;
        this.method = method;

        //noinspection ConstantConditions
        assert null != declaringClassRef;
        //noinspection ConstantConditions
        assert null != method;
    }

    public @Nonnull Method getMethod() {
        return method;
    }

    public @Nonnull ClassRef<C> getDeclaringClassRef() {
        return declaringClassRef;
    }

}

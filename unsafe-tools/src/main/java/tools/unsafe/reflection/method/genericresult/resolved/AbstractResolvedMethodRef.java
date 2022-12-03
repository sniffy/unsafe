package tools.unsafe.reflection.method.genericresult.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.method.AbstractMethodRef;
import tools.unsafe.reflection.method.genericresult.GenericMethodRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AbstractResolvedMethodRef<C> extends AbstractMethodRef<C> implements GenericMethodRef<C> {

    @Nullable
    private final C instance;

    public AbstractResolvedMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method, @Nullable C instance) {
        super(declaringClassRef, method);
        this.instance = instance;
    }

    public <T> T invoke(Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        try {
            //noinspection unchecked
            return (T) getAccessibleMethod().invoke(instance, parameters);
        } catch (InvocationTargetException e) {
            throw e;
        } catch (Exception e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

package tools.unsafe.reflection.method.genericresult.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.method.AbstractMethodRef;
import tools.unsafe.reflection.method.genericresult.GenericDynamicMethodRef;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResolvedDynamicMethodRef<C> extends AbstractMethodRef<C> implements GenericDynamicMethodRef<C> {

    public ResolvedDynamicMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method) {
        super(declaringClassRef, method);
    }

    public <T> T invoke(C instance, Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
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

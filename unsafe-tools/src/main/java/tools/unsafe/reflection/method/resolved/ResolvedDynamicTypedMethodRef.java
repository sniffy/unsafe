package tools.unsafe.reflection.method.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.method.AbstractMethodRef;
import tools.unsafe.reflection.method.GenericDynamicMethodRef;
import tools.unsafe.reflection.method.GenericDynamicTypedMethodRef;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResolvedDynamicTypedMethodRef<C,T> extends AbstractMethodRef<C> implements GenericDynamicTypedMethodRef<C,T> {

    public ResolvedDynamicTypedMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method) {
        super(declaringClassRef, method);
    }

    public T invoke(C instance, Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
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

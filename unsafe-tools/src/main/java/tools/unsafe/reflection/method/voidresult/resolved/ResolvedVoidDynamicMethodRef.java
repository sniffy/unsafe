package tools.unsafe.reflection.method.voidresult.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.method.AbstractMethodRef;
import tools.unsafe.reflection.method.voidresult.GenericVoidDynamicMethodRef;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResolvedVoidDynamicMethodRef<C> extends AbstractMethodRef<C> implements GenericVoidDynamicMethodRef<C> {

    public ResolvedVoidDynamicMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method) {
        super(declaringClassRef, method);
    }

    public void invoke(C instance, Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        try {
            getAccessibleMethod().invoke(instance, parameters);
        } catch (InvocationTargetException e) {
            throw e;
        } catch (Exception e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

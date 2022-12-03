package tools.unsafe.reflection.method.voidresult.oneparam.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.method.AbstractMethodRef;
import tools.unsafe.reflection.method.voidresult.oneparam.GenericVoidDynamicOneParamMethodRef;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResolvedVoidDynamicOneParamMethodRef<C, P1> extends AbstractMethodRef<C> implements GenericVoidDynamicOneParamMethodRef<C, P1> {

    public ResolvedVoidDynamicOneParamMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method) {
        super(declaringClassRef, method);
    }

    public void invoke(C instance, P1 p1) throws UnsafeInvocationException, InvocationTargetException {
        try {
            getAccessibleMethod().invoke(instance, p1);
        } catch (InvocationTargetException e) {
            throw e;
        } catch (Exception e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

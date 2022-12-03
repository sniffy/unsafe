package tools.unsafe.reflection.method.voidresult.oneparam.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.method.AbstractMethodRef;
import tools.unsafe.reflection.method.voidresult.oneparam.GenericVoidOneParamMethodRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AbstractResolvedVoidOneParamMethodRef<C, P1> extends AbstractMethodRef<C> implements GenericVoidOneParamMethodRef<C, P1> {

    @Nullable
    private final C instance;

    public AbstractResolvedVoidOneParamMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method, @Nullable C instance) {
        super(declaringClassRef, method);
        this.instance = instance;
    }

    public void invoke(P1 p1) throws UnsafeInvocationException, InvocationTargetException {
        try {
            getAccessibleMethod().invoke(instance, p1);
        } catch (InvocationTargetException e) {
            throw e;
        } catch (Exception e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

package tools.unsafe.reflection.method.voidresult.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.method.AbstractMethodRef;
import tools.unsafe.reflection.method.voidresult.GenericVoidMethodRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AbstractResolvedVoidMethodRef<C> extends AbstractMethodRef<C> implements GenericVoidMethodRef<C> {

    @Nullable
    private final C instance;

    public AbstractResolvedVoidMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method, @Nullable C instance) {
        super(declaringClassRef, method);
        this.instance = instance;
    }

    public void invoke(Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        try {
            getAccessibleMethod().invoke(instance, parameters);
        } catch (InvocationTargetException e) {
            throw e;
        } catch (Exception e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

package tools.unsafe.reflection.method.voidresult.oneparam.resolved;

import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class ResolvedInstanceVoidOneParamMethodRef<C, P1> extends AbstractResolvedVoidOneParamMethodRef<C, P1> {

    public ResolvedInstanceVoidOneParamMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method, @Nullable C instance) {
        super(declaringClassRef, method, instance);
    }

}

package tools.unsafe.reflection.method.voidresult.oneparam.resolved;

import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public class ResolvedStaticVoidOneParamMethodRef<C, P1> extends AbstractResolvedVoidOneParamMethodRef<C, P1> {

    public ResolvedStaticVoidOneParamMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method) {
        super(declaringClassRef, method, null);
    }

}

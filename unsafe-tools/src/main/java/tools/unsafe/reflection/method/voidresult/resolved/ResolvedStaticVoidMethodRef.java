package tools.unsafe.reflection.method.voidresult.resolved;

import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public class ResolvedStaticVoidMethodRef<C> extends AbstractResolvedVoidMethodRef<C> {

    public ResolvedStaticVoidMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method) {
        super(declaringClassRef, method, null);
    }

}

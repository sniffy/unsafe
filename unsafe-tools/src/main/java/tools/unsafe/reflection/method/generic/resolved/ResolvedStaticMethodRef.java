package tools.unsafe.reflection.method.generic.resolved;

import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public class ResolvedStaticMethodRef<C> extends AbstractResolvedMethodRef<C> {

    public ResolvedStaticMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method) {
        super(declaringClassRef, method, null);
    }

}

package tools.unsafe.reflection.method.resolved;

import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class ResolvedStaticTypedMethodRef<C,T> extends AbstractResolvedTypedMethodRef<C,T> {

    // TODO: should we pass ObjectRef here instead?

    public ResolvedStaticTypedMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method) {
        super(declaringClassRef, method, null);
    }

}

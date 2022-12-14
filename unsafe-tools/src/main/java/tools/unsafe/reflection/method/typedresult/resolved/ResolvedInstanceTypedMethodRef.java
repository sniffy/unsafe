package tools.unsafe.reflection.method.typedresult.resolved;

import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class ResolvedInstanceTypedMethodRef<C, T> extends AbstractResolvedTypedMethodRef<C, T> {

    // TODO: should we pass ObjectRef here instead?

    public ResolvedInstanceTypedMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method, @Nullable C instance) {
        super(declaringClassRef, method, instance);
    }

}

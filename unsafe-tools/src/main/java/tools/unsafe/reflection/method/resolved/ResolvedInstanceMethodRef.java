package tools.unsafe.reflection.method.resolved;

import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class ResolvedInstanceMethodRef<C> extends AbstractResolvedMethodRef<C> {

    public ResolvedInstanceMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method, @Nullable C instance) {
        super(declaringClassRef, method, instance);
    }

}

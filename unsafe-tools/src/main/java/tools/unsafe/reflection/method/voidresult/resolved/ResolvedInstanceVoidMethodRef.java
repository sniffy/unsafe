package tools.unsafe.reflection.method.voidresult.resolved;

import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class ResolvedInstanceVoidMethodRef<C> extends AbstractResolvedVoidMethodRef<C> {

    public ResolvedInstanceVoidMethodRef(@Nonnull ClassRef<C> declaringClassRef, @Nonnull Method method, @Nullable C instance) {
        super(declaringClassRef, method, instance);
    }

}

package tools.unsafe.reflection.method;

import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public interface MethodRef<C> extends UnresolvedMethodRef<C> {

    @Nonnull
    ClassRef<C> getDeclaringClassRef();

    @Nonnull
    Method getMethod();

}

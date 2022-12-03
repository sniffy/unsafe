package tools.unsafe.reflection.method;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.clazz.ClassRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public interface UnresolvedMethodRef<C> {

    @Nonnull
    ClassRef<C> getDeclaringClassRef() throws UnresolvedRefException;

    @Nonnull
    Method getMethod() throws UnresolvedRefException;

    // TODO: add getName() method

}

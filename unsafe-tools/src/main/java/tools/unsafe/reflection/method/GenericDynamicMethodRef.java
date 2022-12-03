package tools.unsafe.reflection.method;

import tools.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;

public interface GenericDynamicMethodRef<C> {

    <T> T invoke(C instance, Object... parameters) throws UnsafeInvocationException, InvocationTargetException;

}

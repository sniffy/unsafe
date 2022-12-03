package tools.unsafe.reflection.method.typed;

import tools.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;

public interface GenericDynamicTypedMethodRef<C, T> {

    T invoke(C instance, Object... parameters) throws UnsafeInvocationException, InvocationTargetException;

}

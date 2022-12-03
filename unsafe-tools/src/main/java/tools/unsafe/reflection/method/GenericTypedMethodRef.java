package tools.unsafe.reflection.method;

import tools.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;

public interface GenericTypedMethodRef<C,T> {

    T invoke(Object... parameters) throws UnsafeInvocationException, InvocationTargetException;

}

package tools.unsafe.reflection.method.generic;

import tools.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;

public interface GenericMethodRef<C> {

    <T> T invoke(Object... parameters) throws UnsafeInvocationException, InvocationTargetException;

}

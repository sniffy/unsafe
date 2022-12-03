package tools.unsafe.reflection.method.voidresult;

import tools.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;

public interface GenericVoidMethodRef<C> {

    void invoke(Object... parameters) throws UnsafeInvocationException, InvocationTargetException;

}

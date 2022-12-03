package tools.unsafe.reflection.method.voidresult;

import tools.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;

public interface GenericVoidDynamicMethodRef<C> {

    void invoke(C instance, Object... parameters) throws UnsafeInvocationException, InvocationTargetException;

}

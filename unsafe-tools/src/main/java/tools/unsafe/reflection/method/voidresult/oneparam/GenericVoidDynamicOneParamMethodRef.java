package tools.unsafe.reflection.method.voidresult.oneparam;

import tools.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;

public interface GenericVoidDynamicOneParamMethodRef<C, P1> {

    void invoke(C instance, P1 p1) throws UnsafeInvocationException, InvocationTargetException;

}

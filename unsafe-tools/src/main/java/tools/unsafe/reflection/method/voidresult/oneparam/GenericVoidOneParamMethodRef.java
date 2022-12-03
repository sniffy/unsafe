package tools.unsafe.reflection.method.voidresult.oneparam;

import tools.unsafe.reflection.UnsafeInvocationException;

import java.lang.reflect.InvocationTargetException;

public interface GenericVoidOneParamMethodRef<C, P1> {

    void invoke(P1 p1) throws UnsafeInvocationException, InvocationTargetException;

}

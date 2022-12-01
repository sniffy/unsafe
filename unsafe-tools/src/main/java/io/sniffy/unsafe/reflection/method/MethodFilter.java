package io.sniffy.unsafe.reflection.method;

import java.lang.reflect.Method;

public interface MethodFilter {

    boolean include(MethodKey methodKey, Method method);

}

package tools.unsafe.spi.invoke;

import tools.unsafe.Reflections;

import java.lang.reflect.Method;

public class ReflectionMethodInvokerServiceProvider implements MethodInvokerServiceProvider {

    @Override
    public MethodInvoker createMethodInvoker(Method method) {
        Reflections.setAccessible(method);
        return new ReflectionMethodInvoker(method);
    }

}

package tools.unsafe;

import tools.unsafe.spi.VintageServiceProviders;
import tools.unsafe.spi.invoke.MethodInvoker;
import tools.unsafe.spi.invoke.MethodInvokerServiceProvider;
import tools.unsafe.spi.reflection.ReflectionServiceProvider;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class Reflections {

    private Reflections() {
    }

    private static ReflectionServiceProvider getReflectionServiceProvider() {
        return (ReflectionServiceProvider) VintageServiceProviders.getInstance().getReflectionServiceProvider();
    }

    private static MethodInvokerServiceProvider getMethodInvokerServiceProvider() {
        return (MethodInvokerServiceProvider) VintageServiceProviders.getInstance().getMethodInvokerServiceProvider();
    }

    // TODO: implement similar for older Java
    public static boolean setAccessible(AccessibleObject ao) {
        return getReflectionServiceProvider().makeAccessible(ao);
    }

    // TODO: implement similar for older Java
    public static boolean removeFinalModifier(Field field) {
        return getReflectionServiceProvider().removeFinalModifier(field);
    }

    // TODO: implement similar for older Java
    public static MethodInvoker getMethodInvoker(Method method) {
        return getMethodInvokerServiceProvider().createMethodInvoker(method);
    }

}

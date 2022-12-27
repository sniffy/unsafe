package tools.unsafe.reflection.mimic;

import tools.unsafe.reflection.object.ObjectRef;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Mimic implements InvocationHandler {

    private final ObjectRef<?> delegateRef;

    public Mimic(Object delegate) {
        this.delegateRef = ObjectRef.of(delegate);
    }

    public static <T> T as(Class<T> clazz, Object delegate) {
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new Mimic(delegate)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return delegateRef.invoke(
                method.getReturnType(),
                method.getName(),
                method.getParameterTypes(),
                args
        );
    }

}

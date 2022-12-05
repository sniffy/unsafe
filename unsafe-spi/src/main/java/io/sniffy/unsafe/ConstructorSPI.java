package io.sniffy.unsafe;

public interface ConstructorSPI {

    void invokeConstructor(Class<?> clazz, Object o, Class<?>[] parameterTypes, Object[] parameters) throws Throwable;

}

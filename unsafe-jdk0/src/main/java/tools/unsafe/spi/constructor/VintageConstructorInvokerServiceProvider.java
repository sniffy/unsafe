package tools.unsafe.spi.constructor;

public interface VintageConstructorInvokerServiceProvider {

    VintageConstructorInvoker createConstructorInvoker(Class clazz, Class[] parameterTypes);

}

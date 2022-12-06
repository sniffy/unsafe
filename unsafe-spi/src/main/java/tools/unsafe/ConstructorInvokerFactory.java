package tools.unsafe;

public interface ConstructorInvokerFactory {

    <C> ConstructorInvoker<C> createConstructorInvoker(Class<C> clazz, Class<?>... parameterTypes) throws InternalUnsafeException;

}

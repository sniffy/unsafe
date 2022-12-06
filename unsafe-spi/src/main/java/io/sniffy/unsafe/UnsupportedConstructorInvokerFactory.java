package io.sniffy.unsafe;

public class UnsupportedConstructorInvokerFactory implements ConstructorInvokerFactory {

    @Override
    public <C> ConstructorInvoker<C> createConstructorInvoker(Class<C> clazz, Class<?>... parameterTypes) throws InternalUnsafeException {
        throw new InternalUnsafeException(new UnsupportedOperationException());
    }

}

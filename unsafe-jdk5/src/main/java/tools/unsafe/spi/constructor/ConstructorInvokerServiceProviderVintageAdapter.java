package tools.unsafe.spi.constructor;

import tools.unsafe.spi.VintageServiceProviders;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ConstructorInvokerServiceProviderVintageAdapter implements ConstructorInvokerServiceProvider {

    @Override
    @Nullable
    public <C> ConstructorInvoker<C> createConstructorInvoker(@Nonnull Class<C> clazz, @Nullable Class<?>... parameterTypes) {
        VintageServiceProviders serviceProviders = VintageServiceProviders.getInstance();
        if (null == serviceProviders) {
            return null;
        } else {
            VintageConstructorInvokerServiceProvider constructorInvokerServiceProvider = serviceProviders.getConstructorInvokerServiceProvider();
            if (null == constructorInvokerServiceProvider) {
                return null;
            } else {
                return new ConstructorInvokerVintageAdapter<C>(constructorInvokerServiceProvider.createConstructorInvoker(clazz, parameterTypes));
            }
        }
    }

}

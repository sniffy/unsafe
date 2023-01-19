package tools.unsafe.spi;

import tools.unsafe.spi.constructor.MethodHandleConstructorInvokerServiceProviderImpl;
import tools.unsafe.spi.invoke.ReflectionMethodInvokerServiceProvider;

public class Java7Providers {

    private Java7Providers() {

    }

    public static void registerProviders() {
        ServiceProviders.getInstance().setConstructorInvokerServiceProvider(new MethodHandleConstructorInvokerServiceProviderImpl());
        ServiceProviders.getInstance().setMethodInvokerServiceProvider(new ReflectionMethodInvokerServiceProvider());
    }

}

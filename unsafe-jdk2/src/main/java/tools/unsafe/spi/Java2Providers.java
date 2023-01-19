package tools.unsafe.spi;

import tools.unsafe.spi.exception.ReflectionExceptionServiceProvider;
import tools.unsafe.spi.invoke.ReflectionMethodInvokerServiceProvider;
import tools.unsafe.spi.reflection.SimpleReflectionServiceProvider;

public class Java2Providers {

    private Java2Providers() {

    }

    public static void registerProviders() {
        VintageServiceProviders.getInstance().setExceptionServiceProvider(new ReflectionExceptionServiceProvider());
        VintageServiceProviders.getInstance().setReflectionServiceProvider(new SimpleReflectionServiceProvider());
        VintageServiceProviders.getInstance().setMethodInvokerServiceProvider(new ReflectionMethodInvokerServiceProvider());
    }

}

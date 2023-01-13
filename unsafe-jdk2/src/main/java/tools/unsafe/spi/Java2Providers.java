package tools.unsafe.spi;

import tools.unsafe.spi.exception.ReflectionExceptionServiceProvider;

public class Java2Providers {

    private Java2Providers() {

    }

    public static void registerProviders() {
        VintageServiceProviders.getInstance().setExceptionServiceProvider(new ReflectionExceptionServiceProvider());
    }

}

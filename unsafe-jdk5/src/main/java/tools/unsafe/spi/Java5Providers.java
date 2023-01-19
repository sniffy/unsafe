package tools.unsafe.spi;

import tools.unsafe.spi.constructor.ConstructorInvokerServiceProviderVintageAdapter;
import tools.unsafe.spi.exception.GenericsExceptionServiceProvider;
import tools.unsafe.spi.unsafe.SunMiscUnsafeServiceProviderVintageAdapter;

public class Java5Providers {

    private Java5Providers() {

    }

    public static void registerProviders() {
        ServiceProviders serviceProviders = ServiceProviders.getInstance();
        VintageServiceProviders vintageServiceProviders = VintageServiceProviders.getInstance();

        serviceProviders.setAssertionServiceProvider(vintageServiceProviders.getAssertionServiceProvider());
        serviceProviders.setExceptionServiceProvider(vintageServiceProviders.getExceptionServiceProvider());

        serviceProviders.setSunMiscUnsafeServiceProvider(new SunMiscUnsafeServiceProviderVintageAdapter());
        serviceProviders.setConstructorInvokerServiceProvider(new ConstructorInvokerServiceProviderVintageAdapter());

        serviceProviders.setExceptionServiceProvider(new GenericsExceptionServiceProvider());
    }

}

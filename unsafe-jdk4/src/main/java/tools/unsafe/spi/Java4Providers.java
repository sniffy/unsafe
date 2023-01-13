package tools.unsafe.spi;

import tools.unsafe.spi.assertion.AssertionServiceProviderImpl;
import tools.unsafe.spi.unsafe.SunMiscUnsafeServiceProviderImpl;

public class Java4Providers {

    private Java4Providers() {

    }

    public static void registerProviders() {
        VintageServiceProviders.getInstance().setAssertionServiceProvider(new AssertionServiceProviderImpl());
        VintageServiceProviders.getInstance().setSunMiscUnsafeServiceProvider(new SunMiscUnsafeServiceProviderImpl());

    }

}

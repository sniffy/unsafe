package tools.unsafe.spi;

import tools.unsafe.spi.assertion.NotAvailableInLegacyJavaAssertionServiceProvider;

public class Java0Providers {

    static {
    }

    private Java0Providers() {

    }

    public static void registerProviders() {
        VintageServiceProviders.getInstance().setAssertionServiceProvider(new NotAvailableInLegacyJavaAssertionServiceProvider());
    }

}

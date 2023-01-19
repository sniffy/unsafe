package tools.unsafe.spi;

import tools.unsafe.spi.instrument.AttachInstrumentationServiceProvider;

public class Java6Providers {

    private Java6Providers() {

    }

    public static void registerProviders() {
        ServiceProviders.getInstance().setInstrumentationServiceProvider(new AttachInstrumentationServiceProvider());
    }

}

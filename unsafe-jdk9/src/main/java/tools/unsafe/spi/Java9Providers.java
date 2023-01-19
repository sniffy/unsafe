package tools.unsafe.spi;

import tools.unsafe.spi.reflection.FakeAccessibleObjectReflectionServiceProvider;

public class Java9Providers {

    private Java9Providers() {

    }

    public static void registerProviders() {
        ServiceProviders.getInstance().setReflectionServiceProvider(new FakeAccessibleObjectReflectionServiceProvider());

    }


}

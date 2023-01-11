package io.sniffy.unsafe;

public class JDK3Providers {

    static {
        registerProviders();
    }

    private JDK3Providers() {

    }

    public static void registerProviders() {
        UnsafeToolsRegistry.getInstance().setAssertionEnabledProvider(new UnsupportedAssertionsProvider());
    }

}

package io.sniffy.unsafe;

public class JDK4Providers {

    static {
        registerProviders();
    }

    private JDK4Providers() {

    }

    public static void registerProviders() {
        UnsafeToolsRegistry.getInstance().setAssertionEnabledProvider(new AssertionsEnabledStatusProvider());
    }

}

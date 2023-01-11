package io.sniffy.unsafe;

public class UnsafeToolsRegistry {

    private static volatile UnsafeToolsRegistry INSTANCE = new UnsafeToolsRegistry();
    private volatile AssertionEnabledProvider assertionEnabledProvider;
    private volatile SunMiscUnsafeProvider sunMiscUnsafeProvider;

    public static UnsafeToolsRegistry getInstance() {
        return INSTANCE;
    }

    public static void setInstance(UnsafeToolsRegistry INSTANCE) {
        UnsafeToolsRegistry.INSTANCE = INSTANCE;
    }

    public AssertionEnabledProvider getAssertionEnabledProvider() {
        return assertionEnabledProvider;
    }

    public void setAssertionEnabledProvider(AssertionEnabledProvider assertionEnabledProvider) {
        this.assertionEnabledProvider = assertionEnabledProvider;
    }

    public SunMiscUnsafeProvider getSunMiscUnsafeProvider() {
        return sunMiscUnsafeProvider;
    }

    public void setSunMiscUnsafeProvider(SunMiscUnsafeProvider sunMiscUnsafeProvider) {
        this.sunMiscUnsafeProvider = sunMiscUnsafeProvider;
    }

}

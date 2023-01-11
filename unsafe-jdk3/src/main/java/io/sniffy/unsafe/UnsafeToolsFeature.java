package io.sniffy.unsafe;

public abstract class UnsafeToolsFeature {

    public final static UnsafeToolsFeature ASSERTIONS_CHECK = new UnsafeToolsFeature() {

        public boolean isAvailable() {
            return null != UnsafeToolsRegistry.getInstance().getAssertionEnabledProvider();
        }

    };

    private UnsafeToolsFeature() {

    }

    public abstract boolean isAvailable();

}

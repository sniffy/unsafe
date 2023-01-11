package io.sniffy.unsafe;

public class UnsupportedAssertionsProvider implements AssertionEnabledProvider {

    public boolean areAssertionsEnabled() {
        return false;
    }

    public boolean isSupported() {
        return true;
    }
}

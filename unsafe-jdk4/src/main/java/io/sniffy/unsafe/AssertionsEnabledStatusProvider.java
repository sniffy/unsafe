package io.sniffy.unsafe;

public class AssertionsEnabledStatusProvider implements AssertionEnabledProvider {

    public boolean areAssertionsEnabled() {
        boolean result = false;
        //noinspection AssertWithSideEffects,ReassignedVariable,AssertionCanBeIf
        assert result = true;
        //noinspection ConstantConditions
        return result;
    }

    public boolean isSupported() {
        return true;
    }
}

package tools.unsafe.spi.assertion;

public class AssertionServiceProviderImpl implements AssertionServiceProvider {

    public boolean assertionsEnabled() {
        boolean result = false;
        //noinspection AssertWithSideEffects, AssertionSideEffect, ReassignedVariable, AssertionCanBeIf
        assert result = true;
        //noinspection ConstantConditions
        return result;
    }

}

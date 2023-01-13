package tools.unsafe.spi.assertion;

public class NotAvailableInLegacyJavaAssertionServiceProvider implements AssertionServiceProvider {

    public boolean assertionsEnabled() {
        return false;
    }

}

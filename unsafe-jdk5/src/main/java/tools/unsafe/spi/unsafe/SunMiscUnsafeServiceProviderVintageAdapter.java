package tools.unsafe.spi.unsafe;

import tools.unsafe.Unsafe;

public class SunMiscUnsafeServiceProviderVintageAdapter implements SunMiscUnsafeServiceProvider {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getSunMiscUnsafe() {
        return (T) Unsafe.getSunMiscUnsafe();
    }

}

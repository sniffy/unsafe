package tools.unsafe;

import tools.unsafe.spi.VintageServiceProviders;
import tools.unsafe.spi.unsafe.VintageSunMiscUnsafeServiceProvider;

public final class Unsafe {

    private Unsafe() {

    }

    public static sun.misc.Unsafe getSunMiscUnsafe() {
        VintageServiceProviders serviceProviders = VintageServiceProviders.getInstance();
        if (null == serviceProviders) {
            return null;
        } else {
            VintageSunMiscUnsafeServiceProvider sunMiscUnsafeServiceProvider = serviceProviders.getSunMiscUnsafeServiceProvider();
            if (null == sunMiscUnsafeServiceProvider) {
                return null;
            } else {
                return (sun.misc.Unsafe) sunMiscUnsafeServiceProvider.getSunMiscUnsafe();
            }
        }
    }

}

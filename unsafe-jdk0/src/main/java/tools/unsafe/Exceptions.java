package tools.unsafe;

import tools.unsafe.spi.VintageServiceProviders;
import tools.unsafe.spi.exception.ExceptionServiceProvider;

public final class Exceptions {

    private Exceptions() {

    }

    public static /*@Nonnull*/ RuntimeException throwException(Throwable e) {
        VintageServiceProviders serviceProviders = VintageServiceProviders.getInstance();
        if (null == serviceProviders) {
            return null;
        } else {
            ExceptionServiceProvider exceptionServiceProvider = serviceProviders.getExceptionServiceProvider();
            if (null == exceptionServiceProvider) {
                return null;
            } else {
                return exceptionServiceProvider.throwException(e);
            }
        }
    }

}

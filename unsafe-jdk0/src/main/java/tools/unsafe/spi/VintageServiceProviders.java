package tools.unsafe.spi;

import tools.unsafe.spi.assertion.AssertionServiceProvider;
import tools.unsafe.spi.constructor.VintageConstructorInvokerServiceProvider;
import tools.unsafe.spi.exception.ExceptionServiceProvider;
import tools.unsafe.spi.unsafe.VintageSunMiscUnsafeServiceProvider;

public class VintageServiceProviders {

    private static volatile VintageServiceProviders INSTANCE;

    static {
        INSTANCE = new VintageServiceProviders();
        ProvidersBootstrap.registerProviders();
    }

    private volatile AssertionServiceProvider assertionServiceProvider;

    private volatile VintageConstructorInvokerServiceProvider constructorInvokerServiceProvider;

    private volatile VintageSunMiscUnsafeServiceProvider sunMiscUnsafeServiceProvider;
    private volatile ExceptionServiceProvider exceptionServiceProvider;

    private volatile /* tools.unsafe.spi.reflection.ReflectionServiceProvider */ Object reflectionServiceProvider;

    private volatile /* tools.unsafe.spi.invoke.MethodInvokerServiceProvider */ Object methodInvokerServiceProvider;

    public static VintageServiceProviders getInstance() {
        return INSTANCE;
    }

    public static void setInstance(VintageServiceProviders INSTANCE) {
        VintageServiceProviders.INSTANCE = INSTANCE;
    }

    public AssertionServiceProvider getAssertionServiceProvider() {
        return assertionServiceProvider;
    }

    public void setAssertionServiceProvider(AssertionServiceProvider assertionServiceProvider) {
        this.assertionServiceProvider = assertionServiceProvider;
    }

    public VintageConstructorInvokerServiceProvider getConstructorInvokerServiceProvider() {
        return constructorInvokerServiceProvider;
    }

    public void setConstructorInvokerServiceProvider(VintageConstructorInvokerServiceProvider constructorInvokerServiceProvider) {
        this.constructorInvokerServiceProvider = constructorInvokerServiceProvider;
    }

    public VintageSunMiscUnsafeServiceProvider getSunMiscUnsafeServiceProvider() {
        return sunMiscUnsafeServiceProvider;
    }

    public void setSunMiscUnsafeServiceProvider(VintageSunMiscUnsafeServiceProvider sunMiscUnsafeServiceProvider) {
        this.sunMiscUnsafeServiceProvider = sunMiscUnsafeServiceProvider;
    }

    public ExceptionServiceProvider getExceptionServiceProvider() {
        return exceptionServiceProvider;
    }

    public void setExceptionServiceProvider(ExceptionServiceProvider exceptionServiceProvider) {
        this.exceptionServiceProvider = exceptionServiceProvider;
    }

    public Object getReflectionServiceProvider() {
        return reflectionServiceProvider;
    }

    public void setReflectionServiceProvider(Object reflectionServiceProvider) {
        this.reflectionServiceProvider = reflectionServiceProvider;
    }

    public Object getMethodInvokerServiceProvider() {
        return methodInvokerServiceProvider;
    }

    public void setMethodInvokerServiceProvider(Object methodInvokerServiceProvider) {
        this.methodInvokerServiceProvider = methodInvokerServiceProvider;
    }

    // TODO: add methods to check if certain service provider is registered

}

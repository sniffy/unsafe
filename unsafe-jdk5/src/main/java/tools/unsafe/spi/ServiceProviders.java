package tools.unsafe.spi;

import tools.unsafe.spi.assertion.AssertionServiceProvider;
import tools.unsafe.spi.constructor.ConstructorInvokerServiceProvider;
import tools.unsafe.spi.exception.ExceptionServiceProvider;
import tools.unsafe.spi.instrument.InstrumentationServiceProvider;
import tools.unsafe.spi.invoke.MethodInvokerServiceProvider;
import tools.unsafe.spi.reflection.ReflectionServiceProvider;
import tools.unsafe.spi.unsafe.SunMiscUnsafeServiceProvider;

public class ServiceProviders {

    private static volatile ServiceProviders INSTANCE = new ServiceProviders();

    private volatile AssertionServiceProvider assertionServiceProvider;

    private volatile ConstructorInvokerServiceProvider constructorInvokerServiceProvider;

    private volatile SunMiscUnsafeServiceProvider sunMiscUnsafeServiceProvider;
    private volatile ExceptionServiceProvider exceptionServiceProvider;

    private volatile InstrumentationServiceProvider instrumentationServiceProvider;

    public static ServiceProviders getInstance() {
        return INSTANCE;
    }

    public static void setInstance(ServiceProviders INSTANCE) {
        ServiceProviders.INSTANCE = INSTANCE;
    }

    public AssertionServiceProvider getAssertionServiceProvider() {
        return assertionServiceProvider;
    }

    public void setAssertionServiceProvider(AssertionServiceProvider assertionServiceProvider) {
        this.assertionServiceProvider = assertionServiceProvider;
    }

    public ConstructorInvokerServiceProvider getConstructorInvokerServiceProvider() {
        return constructorInvokerServiceProvider;
    }

    public void setConstructorInvokerServiceProvider(ConstructorInvokerServiceProvider constructorInvokerServiceProvider) {
        this.constructorInvokerServiceProvider = constructorInvokerServiceProvider;
    }

    public SunMiscUnsafeServiceProvider getSunMiscUnsafeServiceProvider() {
        return sunMiscUnsafeServiceProvider;
    }

    public void setSunMiscUnsafeServiceProvider(SunMiscUnsafeServiceProvider sunMiscUnsafeServiceProvider) {
        this.sunMiscUnsafeServiceProvider = sunMiscUnsafeServiceProvider;
    }

    public ExceptionServiceProvider getExceptionServiceProvider() {
        return exceptionServiceProvider;
    }

    public void setExceptionServiceProvider(ExceptionServiceProvider exceptionServiceProvider) {
        this.exceptionServiceProvider = exceptionServiceProvider;
    }

    public ReflectionServiceProvider getReflectionServiceProvider() {
        return (ReflectionServiceProvider) VintageServiceProviders.getInstance().getReflectionServiceProvider();
    }

    public void setReflectionServiceProvider(ReflectionServiceProvider reflectionServiceProvider) {
        VintageServiceProviders.getInstance().setReflectionServiceProvider(reflectionServiceProvider);
    }


    public MethodInvokerServiceProvider getMethodInvokerServiceProvider() {
        return (MethodInvokerServiceProvider) VintageServiceProviders.getInstance().getMethodInvokerServiceProvider();
    }

    public void setMethodInvokerServiceProvider(MethodInvokerServiceProvider methodInvokerServiceProvider) {
        VintageServiceProviders.getInstance().setMethodInvokerServiceProvider(methodInvokerServiceProvider);
    }

    public InstrumentationServiceProvider getInstrumentationServiceProvider() {
        return instrumentationServiceProvider;
    }

    public void setInstrumentationServiceProvider(InstrumentationServiceProvider instrumentationServiceProvider) {
        this.instrumentationServiceProvider = instrumentationServiceProvider;
    }
}

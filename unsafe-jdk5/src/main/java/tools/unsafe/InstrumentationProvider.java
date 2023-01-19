package tools.unsafe;

import tools.unsafe.spi.ServiceProviders;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Future;

public final class InstrumentationProvider {

    private InstrumentationProvider() {
    }

    public static Future<Instrumentation> getInstrumentationFuture() {
        return ServiceProviders.getInstance().getInstrumentationServiceProvider().getInstrumentationFuture();
    }

}

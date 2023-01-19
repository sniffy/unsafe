package tools.unsafe.spi.instrument;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Future;

public interface InstrumentationServiceProvider {

    Future<Instrumentation> getInstrumentationFuture();

}

package tools.unsafe.spi.exception;

public interface ExceptionServiceProvider {

    RuntimeException throwException(Throwable exception);

}

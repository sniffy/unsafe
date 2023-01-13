package tools.unsafe.spi.exception;

public class ExceptionThrower {

    protected final static ThreadLocal exceptionPerThread = new ThreadLocal();

    public ExceptionThrower() throws Throwable {
        Object o = exceptionPerThread.get();
        if (o instanceof Throwable) {
            throw (Throwable) o;
        }
    }

}

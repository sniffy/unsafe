package tools.unsafe.spi.exception;

import javax.annotation.Nonnull;

public class GenericsExceptionServiceProvider implements ExceptionServiceProvider {

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void throwAny(@Nonnull Throwable e) throws E {
        throw (E) e;
    }

    @Override
    public RuntimeException throwException(Throwable exception) {
        GenericsExceptionServiceProvider.<RuntimeException>throwAny(exception);
        return new RuntimeException(exception);
    }

}

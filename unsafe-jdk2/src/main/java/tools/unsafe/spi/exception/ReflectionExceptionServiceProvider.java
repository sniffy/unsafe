package tools.unsafe.spi.exception;

import tools.unsafe.internal.UnsafeToolsLogging;

public class ReflectionExceptionServiceProvider implements ExceptionServiceProvider {

    public RuntimeException throwException(Throwable exception) {
        try {
            ExceptionThrower.exceptionPerThread.set(exception);
            ExceptionThrower.class.newInstance();
        } catch (InstantiationException e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
        } catch (Throwable e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
        } finally {
            ExceptionThrower.exceptionPerThread.set(null);
        }
        throw new RuntimeException("Should not have reached here; cause was" + exception);
    }

}

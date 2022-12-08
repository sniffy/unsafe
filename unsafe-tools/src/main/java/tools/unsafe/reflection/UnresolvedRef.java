package tools.unsafe.reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Wraps a pair of non-null object of class T and exception which could have been thrown when obtaining this object.
 * Nullable method `T getRef()` is missing intentionally since the idea is to enforcer developer to catch the potential checked exception of type {@link UnresolvedRefException}.
 *
 * @param <T>
 */
public class UnresolvedRef<T> {

    private final Future<T> future;
    private final AtomicReference<RefOrException<T>> refOrExceptionAtomicReference = new AtomicReference<RefOrException<T>>();

    protected UnresolvedRef(@Nonnull Callable<T> refSupplier) {
        future = new CallableFuture<T>(refSupplier);
    }

    public @Nonnull T resolve() throws UnresolvedRefException {
        RefOrException<T> refOrException = getRefOrException();
        if (isResolved()) {
            //noinspection ConstantConditions
            return refOrException.ref;
        } else {
            assert refOrException.throwable != null;
            throw new UnresolvedRefException(refOrException.throwable);
        }
    }

    private RefOrException<T> getRefOrException() {
        RefOrException<T> refOrException = refOrExceptionAtomicReference.get();
        if (null == refOrException) {
            synchronized (refOrExceptionAtomicReference) {
                refOrException = refOrExceptionAtomicReference.get();
                if (null == refOrException) {
                    T ref = null;
                    Throwable throwable = null;
                    try {
                        ref = future.get();
                    } catch (ExecutionException e) {
                        throwable = e.getCause();
                    } catch (InterruptedException e) {
                        throwable = e;
                    }
                    refOrException = new RefOrException<T>(ref, throwable);
                    refOrExceptionAtomicReference.set(refOrException);
                }
            }
        }
        return refOrException;
    }

    public @Nullable T resolveOrNull() {
        RefOrException<T> refOrException = getRefOrException();
        return null == refOrException.ref ? null : refOrException.ref;
    }

    public boolean isResolved() {
        return null == getResolveException();
    }

    public @Nullable Throwable getResolveException() {
        return getRefOrException().throwable;
    }

    private static class RefOrException<T> {

        private final T ref;
        private final Throwable throwable;

        public RefOrException(@Nullable T ref, @Nullable Throwable throwable) {
            assert null != ref || null != throwable;
            this.ref = ref;
            this.throwable = throwable;
        }

    }

    private static class CallableFuture<T> implements Future<T> {

        private final Callable<T> callable;

        public CallableFuture(Callable<T> callable) {
            this.callable = callable;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return true;
        }

        @Override
        public T get() throws InterruptedException, ExecutionException {
            try {
                return callable.call();
            } catch (Exception e) {
                throw new ExecutionException(e);
            }
        }

        @Override
        public T get(long timeout, @Nonnull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return get();
        }
    }

}

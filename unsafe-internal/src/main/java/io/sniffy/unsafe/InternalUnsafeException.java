package io.sniffy.unsafe;

public class InternalUnsafeException extends Exception {

    public InternalUnsafeException() {
    }

    public InternalUnsafeException(String message) {
        super(message);
    }

    public InternalUnsafeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalUnsafeException(Throwable cause) {
        super(cause);
    }
}

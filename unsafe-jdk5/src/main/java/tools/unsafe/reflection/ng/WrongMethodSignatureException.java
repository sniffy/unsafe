package tools.unsafe.reflection.ng;

public class WrongMethodSignatureException extends RuntimeException {

    // TODO: add proper fields


    public WrongMethodSignatureException() {
    }

    public WrongMethodSignatureException(String message) {
        super(message);
    }

    public WrongMethodSignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongMethodSignatureException(Throwable cause) {
        super(cause);
    }

}

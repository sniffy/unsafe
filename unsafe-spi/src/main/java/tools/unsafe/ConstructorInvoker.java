package tools.unsafe;

public interface ConstructorInvoker<C> {

    void invoke(C instance, Object... parameters) throws InternalUnsafeException;

}

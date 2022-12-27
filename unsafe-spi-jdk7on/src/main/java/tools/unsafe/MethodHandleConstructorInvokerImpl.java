package tools.unsafe;

import java.lang.invoke.MethodHandle;

/**
 * <a href="https://stackoverflow.com/questions/48616630/is-it-possible-to-call-constructor-on-existing-instance">See stackoverflow discussion</a>
 * <p>
 * TODO: doesn't work on J9
 */
public class MethodHandleConstructorInvokerImpl<C> implements ConstructorInvoker<C> {

    private final MethodHandle methodHandle;

    public MethodHandleConstructorInvokerImpl(MethodHandle methodHandle) {
        this.methodHandle = methodHandle;
    }

    @Override
    public void invoke(C instance, Object... parameters) throws InternalUnsafeException {
        try {
            if (null == parameters || 0 == parameters.length) {
                methodHandle.invoke(instance);
            } else {
                Object[] parametersWithInstance = new Object[parameters.length + 1];
                parametersWithInstance[0] = instance;
                System.arraycopy(parameters, 0, parametersWithInstance, 1, parameters.length);
                methodHandle.invokeWithArguments(parametersWithInstance);
            }
        } catch (Throwable e) {
            throw new InternalUnsafeException(e);
        }
    }

}

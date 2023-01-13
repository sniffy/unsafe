package tools.unsafe.spi.constructor;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ConstructorInvokerVintageAdapter<C> implements ConstructorInvoker<C> {

    @Nonnull
    private final VintageConstructorInvoker delegate;

    public ConstructorInvokerVintageAdapter(@Nonnull VintageConstructorInvoker delegate) {
        this.delegate = delegate;
    }

    @Override
    public void invoke(@Nonnull C instance, @Nullable Object... parameters) {
        delegate.invoke(instance, parameters);
    }

}

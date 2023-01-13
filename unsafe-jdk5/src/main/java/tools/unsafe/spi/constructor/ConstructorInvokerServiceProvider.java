package tools.unsafe.spi.constructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ConstructorInvokerServiceProvider {

    @Nullable
    <C> ConstructorInvoker<C> createConstructorInvoker(@Nonnull Class<C> clazz, @Nullable Class<?>... parameterTypes);

}

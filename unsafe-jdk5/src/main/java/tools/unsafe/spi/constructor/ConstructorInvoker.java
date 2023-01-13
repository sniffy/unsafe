package tools.unsafe.spi.constructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ConstructorInvoker<C> {

    void invoke(@Nonnull C instance, @Nullable Object... parameters);

}

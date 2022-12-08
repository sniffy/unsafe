package tools.unsafe.reflection.field.booleans.unresolved;

import tools.unsafe.reflection.field.booleans.resolved.ResolvedDynamicBooleanFieldRef;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public class UnresolvedDynamicBooleanFieldRef<C> extends AbstractUnresolvedDynamicBooleanFieldRef<ResolvedDynamicBooleanFieldRef<C>, C> {

    public UnresolvedDynamicBooleanFieldRef(@Nonnull Callable<ResolvedDynamicBooleanFieldRef<C>> refSupplier) {
        super(refSupplier);
    }
}

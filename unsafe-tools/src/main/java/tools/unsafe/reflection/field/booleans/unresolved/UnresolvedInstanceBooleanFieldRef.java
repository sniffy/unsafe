package tools.unsafe.reflection.field.booleans.unresolved;

import tools.unsafe.reflection.field.booleans.resolved.ResolvedInstanceBooleanFieldRef;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public class UnresolvedInstanceBooleanFieldRef<C> extends AbstractUnresolvedBooleanFieldRef<ResolvedInstanceBooleanFieldRef<C>, C> {

    public UnresolvedInstanceBooleanFieldRef(@Nonnull Callable<ResolvedInstanceBooleanFieldRef<C>> refSupplier) {
        super(refSupplier);
    }
}

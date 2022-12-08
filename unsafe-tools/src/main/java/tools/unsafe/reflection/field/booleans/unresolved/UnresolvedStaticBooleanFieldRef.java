package tools.unsafe.reflection.field.booleans.unresolved;

import tools.unsafe.reflection.field.booleans.resolved.ResolvedStaticBooleanFieldRef;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public class UnresolvedStaticBooleanFieldRef<C> extends AbstractUnresolvedBooleanFieldRef<ResolvedStaticBooleanFieldRef<C>, C> {

    public UnresolvedStaticBooleanFieldRef(@Nonnull Callable<ResolvedStaticBooleanFieldRef<C>> refSupplier) {
        super(refSupplier);
    }
}

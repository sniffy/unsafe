package tools.unsafe.reflection.field.objects.unresolved;

import tools.unsafe.reflection.field.objects.resolved.ResolvedInstanceObjectFieldRef;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public class UnresolvedInstanceObjectFieldRef<C, T> extends AbstractUnresolvedObjectFieldRef<ResolvedInstanceObjectFieldRef<C, T>, C, T> {

    public UnresolvedInstanceObjectFieldRef(@Nonnull Callable<ResolvedInstanceObjectFieldRef<C, T>> refSupplier) {
        super(refSupplier);
    }
}

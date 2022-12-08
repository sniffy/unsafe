package tools.unsafe.reflection.field.objects.unresolved;

import tools.unsafe.reflection.field.booleans.resolved.ResolvedDynamicBooleanFieldRef;
import tools.unsafe.reflection.field.booleans.unresolved.UnresolvedDynamicBooleanFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedDynamicObjectFieldRef;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public class UnresolvedDynamicObjectFieldRef<C, T> extends AbstractUnresolvedDynamicObjectFieldRef<ResolvedDynamicObjectFieldRef<C, T>, C, T> {

    public UnresolvedDynamicObjectFieldRef(@Nonnull Callable<ResolvedDynamicObjectFieldRef<C, T>> refSupplier) {
        super(refSupplier);
    }

    public UnresolvedDynamicBooleanFieldRef<C> asBooleanFieldRef() {
        return new UnresolvedDynamicBooleanFieldRef<C>(new Callable<ResolvedDynamicBooleanFieldRef<C>>() {
            @Override
            public ResolvedDynamicBooleanFieldRef<C> call() throws Exception {
                return resolve().asBooleanFieldRef();
            }
        });
    }
}

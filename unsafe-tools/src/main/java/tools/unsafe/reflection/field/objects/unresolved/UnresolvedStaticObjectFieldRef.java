package tools.unsafe.reflection.field.objects.unresolved;

import tools.unsafe.reflection.field.booleans.resolved.ResolvedStaticBooleanFieldRef;
import tools.unsafe.reflection.field.booleans.unresolved.UnresolvedStaticBooleanFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedStaticObjectFieldRef;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public class UnresolvedStaticObjectFieldRef<C, T> extends AbstractUnresolvedObjectFieldRef<ResolvedStaticObjectFieldRef<C, T>, C, T> {

    public UnresolvedStaticObjectFieldRef(@Nonnull Callable<ResolvedStaticObjectFieldRef<C, T>> refSupplier) {
        super(refSupplier);
    }

    public UnresolvedStaticBooleanFieldRef<C> asBooleanFieldRef() {
        return new UnresolvedStaticBooleanFieldRef<C>(new Callable<ResolvedStaticBooleanFieldRef<C>>() {
            @Override
            public ResolvedStaticBooleanFieldRef<C> call() throws Exception {
                return resolve().asBooleanFieldRef();
            }
        });
    }

}

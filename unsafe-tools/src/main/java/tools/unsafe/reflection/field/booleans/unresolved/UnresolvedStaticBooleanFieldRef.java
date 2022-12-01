package tools.unsafe.reflection.field.booleans.unresolved;

import tools.unsafe.reflection.field.booleans.resolved.ResolvedStaticBooleanFieldRef;

public class UnresolvedStaticBooleanFieldRef<C> extends AbstractUnresolvedBooleanFieldRef<ResolvedStaticBooleanFieldRef<C>, C> {

    public UnresolvedStaticBooleanFieldRef(ResolvedStaticBooleanFieldRef<C> ref, Throwable throwable) {
        super(ref, throwable);
    }

}

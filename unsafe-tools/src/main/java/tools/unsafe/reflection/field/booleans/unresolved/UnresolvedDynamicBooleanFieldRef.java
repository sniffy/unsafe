package tools.unsafe.reflection.field.booleans.unresolved;

import tools.unsafe.reflection.field.booleans.resolved.ResolvedDynamicBooleanFieldRef;

public class UnresolvedDynamicBooleanFieldRef<C> extends AbstractUnresolvedDynamicBooleanFieldRef<ResolvedDynamicBooleanFieldRef<C>, C> {

    public UnresolvedDynamicBooleanFieldRef(ResolvedDynamicBooleanFieldRef<C> ref, Throwable throwable) {
        super(ref, throwable);
    }

}

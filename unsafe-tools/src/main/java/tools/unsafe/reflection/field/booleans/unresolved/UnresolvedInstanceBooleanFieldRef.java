package tools.unsafe.reflection.field.booleans.unresolved;

import tools.unsafe.reflection.field.booleans.resolved.ResolvedInstanceBooleanFieldRef;

public class UnresolvedInstanceBooleanFieldRef<C> extends AbstractUnresolvedBooleanFieldRef<ResolvedInstanceBooleanFieldRef<C>, C> {

    public UnresolvedInstanceBooleanFieldRef(ResolvedInstanceBooleanFieldRef<C> ref, Throwable throwable) {
        super(ref, throwable);
    }

}

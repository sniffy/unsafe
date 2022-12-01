package tools.unsafe.reflection.field.unresolved;

import tools.unsafe.reflection.field.resolved.ResolvedInstanceObjectFieldRef;

public class UnresolvedInstanceObjectFieldRef<C,T> extends AbstractUnresolvedObjectFieldRef<ResolvedInstanceObjectFieldRef<C,T>, C, T> {

    public UnresolvedInstanceObjectFieldRef(ResolvedInstanceObjectFieldRef<C,T> ref, Throwable throwable) {
        super(ref, throwable);
    }

}

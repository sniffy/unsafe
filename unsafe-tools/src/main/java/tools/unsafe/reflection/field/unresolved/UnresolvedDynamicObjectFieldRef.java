package tools.unsafe.reflection.field.unresolved;

import tools.unsafe.reflection.field.resolved.ResolvedDynamicObjectFieldRef;

public class UnresolvedDynamicObjectFieldRef<C,T> extends AbstractUnresolvedDynamicObjectFieldRef<ResolvedDynamicObjectFieldRef<C,T>, C, T> {

    public UnresolvedDynamicObjectFieldRef(ResolvedDynamicObjectFieldRef<C,T> ref, Throwable throwable) {
        super(ref, throwable);
    }

}

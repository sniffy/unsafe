package tools.unsafe.reflection.field.objects.unresolved;

import tools.unsafe.reflection.field.objects.resolved.ResolvedInstanceObjectFieldRef;

public class UnresolvedInstanceObjectFieldRef<C, T> extends AbstractUnresolvedObjectFieldRef<ResolvedInstanceObjectFieldRef<C, T>, C, T> {

    public UnresolvedInstanceObjectFieldRef(ResolvedInstanceObjectFieldRef<C, T> ref, Throwable throwable) {
        super(ref, throwable);
    }

}

package tools.unsafe.reflection.field.objects.unresolved;

import tools.unsafe.reflection.field.objects.resolved.ResolvedStaticObjectFieldRef;

public class UnresolvedStaticObjectFieldRef<C,T> extends AbstractUnresolvedObjectFieldRef<ResolvedStaticObjectFieldRef<C,T>, C, T> {

    public UnresolvedStaticObjectFieldRef(ResolvedStaticObjectFieldRef<C,T> ref, Throwable throwable) {
        super(ref, throwable);
    }

}

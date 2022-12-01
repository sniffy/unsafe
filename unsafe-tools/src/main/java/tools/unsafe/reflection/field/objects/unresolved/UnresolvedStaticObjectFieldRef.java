package tools.unsafe.reflection.field.objects.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.field.booleans.resolved.ResolvedStaticBooleanFieldRef;
import tools.unsafe.reflection.field.booleans.unresolved.UnresolvedStaticBooleanFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedStaticObjectFieldRef;

public class UnresolvedStaticObjectFieldRef<C,T> extends AbstractUnresolvedObjectFieldRef<ResolvedStaticObjectFieldRef<C,T>, C, T> {

    public UnresolvedStaticObjectFieldRef(ResolvedStaticObjectFieldRef<C,T> ref, Throwable throwable) {
        super(ref, throwable);
    }

    public UnresolvedStaticBooleanFieldRef<C> asBooleanFieldRef() {
        ResolvedStaticBooleanFieldRef<C> resolvedStaticBooleanFieldRef = null;
        Exception exception = null;
        try {
            resolvedStaticBooleanFieldRef = resolve().asBooleanFieldRef();
        } catch (UnresolvedRefException e) {
            exception = e;
        }
        return new UnresolvedStaticBooleanFieldRef<C>(resolvedStaticBooleanFieldRef, exception);
    }

}

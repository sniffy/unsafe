package tools.unsafe.reflection.field.objects.unresolved;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.field.booleans.resolved.ResolvedDynamicBooleanFieldRef;
import tools.unsafe.reflection.field.booleans.unresolved.UnresolvedDynamicBooleanFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedDynamicObjectFieldRef;

public class UnresolvedDynamicObjectFieldRef<C,T> extends AbstractUnresolvedDynamicObjectFieldRef<ResolvedDynamicObjectFieldRef<C,T>, C, T> {

    public UnresolvedDynamicObjectFieldRef(ResolvedDynamicObjectFieldRef<C,T> ref, Throwable throwable) {
        super(ref, throwable);
    }

    public UnresolvedDynamicBooleanFieldRef<C> asBooleanFieldRef() {
        ResolvedDynamicBooleanFieldRef<C> resolvedStaticBooleanFieldRef = null;
        Exception exception = null;
        try {
            resolvedStaticBooleanFieldRef = resolve().asBooleanFieldRef();
        } catch (UnresolvedRefException e) {
            exception = e;
        }
        return new UnresolvedDynamicBooleanFieldRef<C>(resolvedStaticBooleanFieldRef, exception);
    }
}

package tools.unsafe.reflection.fluent;

import tools.unsafe.reflection.field.objects.resolved.ResolvedInstanceObjectFieldRef;
import tools.unsafe.reflection.method.resolved.ResolvedInstanceMethodRef;
import tools.unsafe.reflection.object.ObjectRef;

/**
 * Represents one of FieldRef, MethodRef or ObjectRef
 */
public class FluentRef<C,T> {

    private final ResolvedInstanceObjectFieldRef<C,T> fieldRef;
    private final ResolvedInstanceMethodRef<C> methodRef;
    private final ObjectRef<C> objectRef;

    public FluentRef(ResolvedInstanceObjectFieldRef<C, T> fieldRef, ResolvedInstanceMethodRef<C> methodRef, ObjectRef<C> objectRef) {
        this.fieldRef = fieldRef;
        this.methodRef = methodRef;
        this.objectRef = objectRef;
    }

    public <T> T get() {
        return null;
    }

    public <T> void set(T value) {

    }



}

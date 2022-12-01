package tools.unsafe.reflection.fluent;

import tools.unsafe.reflection.field.ObjectFieldRef;
import tools.unsafe.reflection.method.ObjectNonVoidMethodRef;
import tools.unsafe.reflection.object.ObjectRef;

/**
 * Represents one of FieldRef, MethodRef or ObjectRef
 */
public class FluentRef<C,T> {

    private final ObjectFieldRef<C,T> fieldRef;
    private final ObjectNonVoidMethodRef<C,T> methodRef;
    private final ObjectRef<C> objectRef;

    public FluentRef(ObjectFieldRef<C, T> fieldRef, ObjectNonVoidMethodRef<C, T> methodRef, ObjectRef<C> objectRef) {
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

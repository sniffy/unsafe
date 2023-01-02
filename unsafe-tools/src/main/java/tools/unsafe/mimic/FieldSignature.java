package tools.unsafe.mimic;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedInstanceObjectFieldRef;
import tools.unsafe.reflection.field.objects.unresolved.UnresolvedDynamicObjectFieldRef;
import tools.unsafe.reflection.field.objects.unresolved.UnresolvedStaticObjectFieldRef;
import tools.unsafe.reflection.object.ObjectRef;

public class FieldSignature<T> {

    // TODO: remove if not required
    private final Class<T> fieldType;
    private final String fieldName;

    // TODO: provide factory methods
    public FieldSignature(Class<T> fieldType, String fieldName) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
    }

    public T getField(Object instance) throws UnresolvedRefException, UnsafeInvocationException {
        // TODO: automatically detect static vs instance fields
        return bindTo(ObjectRef.of(instance)).get();
    }

    public void setField(Object instance, T value) throws UnresolvedRefException, UnsafeInvocationException {
        // TODO: automatically detect static vs instance fields
        bindTo(ObjectRef.of(instance)).set(value);
    }

    public <C> UnresolvedStaticObjectFieldRef<C, T> staticBindTo(ClassRef<C> classRef) {
        return classRef.staticField(fieldName);
    }

    public <C> UnresolvedDynamicObjectFieldRef<C, T> bindTo(ClassRef<C> classRef) {
        return classRef.field(fieldName);
    }

    // TODO: it should return resolved instance and should not throw exception
    public <C> ResolvedInstanceObjectFieldRef<C, T> bindTo(ObjectRef<C> objectRef) throws UnresolvedRefException {
        return objectRef.field(fieldName);
    }

}

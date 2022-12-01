package tools.unsafe.reflection.field;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;

public class ResolvedInstanceObjectFieldRef<C, T> extends AbstractFieldRef<C> implements FieldRef<C>, ObjectFieldRef<T> {

    private final ResolvedNonStaticObjectFieldRef<C, T> resolvedNonStaticObjectFieldRef;
    private final C object;

    public ResolvedInstanceObjectFieldRef(ClassRef<C> classRef, ResolvedNonStaticObjectFieldRef<C, T> resolvedNonStaticObjectFieldRef, C object) {
        super(classRef, resolvedNonStaticObjectFieldRef.getField());
        this.resolvedNonStaticObjectFieldRef = resolvedNonStaticObjectFieldRef;
        this.object = object;
    }

    public boolean compareAndSet(T oldValue, T newValue) throws UnsafeInvocationException {
        return resolvedNonStaticObjectFieldRef.compareAndSet(object, oldValue, newValue);
    }

    public T get() throws UnsafeInvocationException {
        return resolvedNonStaticObjectFieldRef.get(object);
    }

    public void set(T value) throws UnsafeInvocationException {
        resolvedNonStaticObjectFieldRef.set(object, value);
    }

}

package tools.unsafe.reflection.field.objects.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;

public class ResolvedInstanceObjectFieldRef<C, T> extends AbstractObjectFieldRef<C, T> {

    private final ResolvedDynamicObjectFieldRef<C, T> resolvedNonStaticObjectFieldRef;
    private final C object;

    public ResolvedInstanceObjectFieldRef(ClassRef<C> classRef, ResolvedDynamicObjectFieldRef<C, T> resolvedNonStaticObjectFieldRef, C object) {
        super(classRef, resolvedNonStaticObjectFieldRef.getField(), UNSAFE.objectFieldOffset(resolvedNonStaticObjectFieldRef.getField()));
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

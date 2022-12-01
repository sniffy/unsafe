package tools.unsafe.reflection.field.booleans.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;

public class ResolvedInstanceBooleanFieldRef<C> extends AbstractBooleanFieldRef<C> {

    private final ResolvedDynamicBooleanFieldRef<C> resolvedNonStaticBooleanFieldRef;
    private final C object;

    public ResolvedInstanceBooleanFieldRef(ClassRef<C> classRef, ResolvedDynamicBooleanFieldRef<C> resolvedNonStaticBooleanFieldRef, C object) {
        super(classRef, resolvedNonStaticBooleanFieldRef.getField(), UNSAFE.objectFieldOffset(resolvedNonStaticBooleanFieldRef.getField()));
        this.resolvedNonStaticBooleanFieldRef = resolvedNonStaticBooleanFieldRef;
        this.object = object;
    }

    public boolean compareAndSet(boolean oldValue, boolean newValue) throws UnsafeInvocationException {
        return resolvedNonStaticBooleanFieldRef.compareAndSet(object, oldValue, newValue);
    }

    public boolean get() throws UnsafeInvocationException {
        return resolvedNonStaticBooleanFieldRef.get(object);
    }

    public void set(boolean value) throws UnsafeInvocationException {
        resolvedNonStaticBooleanFieldRef.set(object, value);
    }

}

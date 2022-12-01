package tools.unsafe.reflection.field.booleans.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.field.AbstractFieldRef;
import tools.unsafe.reflection.field.booleans.DynamicBooleanFieldRef;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public abstract class AbstractDynamicBooleanFieldRef<C> extends AbstractFieldRef<C> implements DynamicBooleanFieldRef<C> {

    public AbstractDynamicBooleanFieldRef(ClassRef<C> declaringClassRef, Field field, long offset) {
        super(declaringClassRef, field, offset);
        assert field.getType() == Boolean.TYPE;
    }

    @Override
    public abstract boolean get(C instance) throws UnsafeInvocationException;

    @Override
    public abstract void set(C instance, boolean value) throws UnsafeInvocationException;

    @Override
    public boolean compareAndSet(C instance, boolean oldValue, boolean newValue) throws UnsafeInvocationException {
        // TODO: implement proper CAS for boolean

        boolean currentValue = get(instance);
        if (currentValue == oldValue) {
            set(instance, newValue);
            return true;
        } else {
            return false;
        }
    }

    public void copy(@Nonnull C from, @Nonnull C to) throws UnsafeInvocationException {
        set(to, get(from));
    }

}

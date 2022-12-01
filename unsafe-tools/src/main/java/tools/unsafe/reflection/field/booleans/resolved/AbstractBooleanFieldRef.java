package tools.unsafe.reflection.field.booleans.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.field.AbstractFieldRef;
import tools.unsafe.reflection.field.booleans.BooleanFieldRef;

import java.lang.reflect.Field;

public abstract class AbstractBooleanFieldRef<C> extends AbstractFieldRef<C> implements BooleanFieldRef {
    public AbstractBooleanFieldRef(ClassRef<C> declaringClassRef, Field field, long offset) {
        super(declaringClassRef, field, offset);
        assert field.getType() == Boolean.TYPE;
    }

    @Override
    public abstract boolean get() throws UnsafeInvocationException;

    @Override
    public abstract void set(boolean value) throws UnsafeInvocationException;

    @Override
    public boolean compareAndSet(boolean oldValue, boolean newValue) throws UnsafeInvocationException {
        // TODO: implement proper CAS for boolean

        boolean currentValue = get();
        if (currentValue == oldValue) {
            set(newValue);
            return true;
        } else {
            return false;
        }
    }
}

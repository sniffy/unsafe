package tools.unsafe.reflection.field.booleans.resolved;

import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ResolvedStaticBooleanFieldRef<C> extends AbstractBooleanFieldRef<C> {

    private final Object object = field.getDeclaringClass();

    public ResolvedStaticBooleanFieldRef(ClassRef<C> classRef, Field field) {
        super(classRef, field, UNSAFE.staticFieldOffset(field));
    }

    public void set(boolean value) throws UnsafeInvocationException {
        try {
            if (Modifier.isVolatile(field.getModifiers())) {
                UNSAFE.putBooleanVolatile(object, offset, value);
            } else {
                UNSAFE.putBoolean(object, offset, value);
            }
        } catch (Throwable e) {
            throw new UnsafeInvocationException(e);
        }
    }

    public boolean get() throws UnsafeInvocationException {
        try {
            if (Modifier.isVolatile(field.getModifiers())) {
                return UNSAFE.getBooleanVolatile(object, offset);
            } else {
                return UNSAFE.getBoolean(object, offset);
            }
        } catch (Throwable e) {
            throw new UnsafeInvocationException(e);
        }
    }

}

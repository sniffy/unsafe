package tools.unsafe.reflection.field;

import tools.unsafe.Unsafe;
import tools.unsafe.reflection.clazz.ClassRef;

import java.lang.reflect.Field;

public abstract class AbstractFieldRef<C> implements FieldRef<C> {

    protected final static sun.misc.Unsafe UNSAFE = Unsafe.getSunMiscUnsafe();

    protected final ClassRef<C> declaringClassRef;
    protected final Field field;

    protected final long offset;

    public AbstractFieldRef(ClassRef<C> declaringClassRef, Field field, long offset) {
        this.declaringClassRef = declaringClassRef;
        this.field = field;
        this.offset = offset;

        // Ensure the given class has been initialized. This is often needed in conjunction with obtaining the static field base of a class.
        declaringClassRef.ensureClassInitialized();
    }

    public Field getField() {
        return field;
    }

    public ClassRef<C> getDeclaringClassRef() {
        return declaringClassRef;
    }

}

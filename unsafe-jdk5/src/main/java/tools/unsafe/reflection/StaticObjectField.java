package tools.unsafe.reflection;

import tools.unsafe.Exceptions;
import tools.unsafe.Reflections;
import tools.unsafe.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;

public class StaticObjectField<T> {

    private final Field field;
    private final long offset;

    public StaticObjectField(Callable<Field> fieldSupplier, FieldOffsetSupplier fieldOffsetSupplier) {
        this.field = getDeclaredField(fieldSupplier);
        this.offset = getDeclaredFieldOffset(fieldOffsetSupplier);
    }

    public StaticObjectField(Callable<Field> fieldSupplier) {
        this.field = getDeclaredField(fieldSupplier);
        this.offset = -1;
    }

    private static Field getDeclaredField(Callable<Field> fieldSupplier) {
        try {
            return fieldSupplier.call();
        } catch (Exception e) {
            throw Exceptions.throwException(e);
        }
    }

    private static long getDeclaredFieldOffset(FieldOffsetSupplier fieldOffsetSupplier) {
        try {
            return fieldOffsetSupplier.offset(Unsafe.getSunMiscUnsafe());
        } catch (Throwable e) {
            throw Exceptions.throwException(e);
        }
    }

    public T get() {
        Reflections.setAccessible(field);
        Reflections.removeFinalModifier(field); // TODO: it is cached

        try {
            //noinspection unchecked
            return (T) field.get(null);
        } catch (IllegalAccessException e) {
            return (T) Unsafe.getSunMiscUnsafe().getObject(field.getDeclaringClass(), offset > -1 ? offset : Unsafe.getSunMiscUnsafe().staticFieldOffset(field));
            /*assert false : e;
            return null;*/
        }
    }

    public void set(T value) {

        if (-1 != offset) {
            Unsafe.getSunMiscUnsafe().putObject(field.getDeclaringClass(), offset, value);
            return;
        }

        Unsafe.getSunMiscUnsafe().putObject(field.getDeclaringClass(), Unsafe.getSunMiscUnsafe().staticFieldOffset(field), value);

        if (true) return;

        Reflections.setAccessible(field);
        Reflections.removeFinalModifier(field); // TODO: do it conditionally
        try {
            field.set(null, value);
        } catch (IllegalAccessException e) {
            assert false : e;
        }

        // TODO: do it via reflection as a fallback

        /*if (null != unsafeObjectSetter) {
            try {
                unsafeObjectSetter.set(Unsafe.getSunMiscUnsafe(), value);
                return;
            } catch (NoSuchFieldException e) {
                throw Unsafe.throwException(e);
            }
        }

        ensureAccessible();

        if (Modifier.isFinal(field.getModifiers()) && (tryGetJavaVersion(8) >= 16 || ANDROID == UnsafeVirtualMachine.getFamily())) { // TODO: print warning here
            if (GRAALVM_NATIVE == UnsafeVirtualMachine.getFamily()) { // TODO: add version check (or not?)
                throw new RuntimeException("Please extend from StaticFinalObjectFieldRef"); // TODO: come with a proper exception here
            } else {
                // set via Unsafe
                //noinspection deprecation
                getSunMiscUnsafe().putObject(field.getDeclaringClass(), getSunMiscUnsafe().staticFieldOffset(field), value);
            }
        } else {
            // set via reflection
            try {
                field.set(null, value);
            } catch (IllegalAccessException e) {
                assert false : e;
            }
        }*/

    }

}

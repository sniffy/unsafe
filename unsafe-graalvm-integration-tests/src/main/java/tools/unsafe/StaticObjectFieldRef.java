package tools.unsafe;

import tools.unsafe.reflection.UnsafeException;
import tools.unsafe.vm.UnsafeVirtualMachine;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static tools.unsafe.Unsafe.getSunMiscUnsafe;
import static tools.unsafe.Unsafe.tryGetJavaVersion;
import static tools.unsafe.vm.VirtualMachineFamily.GRAALVM_NATIVE;

public class StaticObjectFieldRef<T> {

    private final Field field;

    public StaticObjectFieldRef(Field field) {
        this.field = field;
    }

    private void ensureAccessible() {
        if (!field.isAccessible()) {
            try {
                Unsafe.setAccessible(field);
            } catch (UnsafeException e) {
                assert false : e;
            }
        }
    }

    public T get() {
        ensureAccessible();
        try {
            //noinspection unchecked
            return (T) field.get(null);
        } catch (IllegalAccessException e) {
            assert false : e;
            return null;
        }
    }

    public void set(T value) {
        ensureAccessible();

        if (Modifier.isFinal(field.getModifiers()) && tryGetJavaVersion(8) >= 16) { // TODO: print warning here
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
        }

    }

}

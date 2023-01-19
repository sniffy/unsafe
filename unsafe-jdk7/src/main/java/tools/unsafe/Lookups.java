package tools.unsafe;

import tools.unsafe.internal.UnsafeToolsLogging;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public final class Lookups {

    private Lookups() {
    }

    // TODO: try fallback to privateLookupIn
    public static MethodHandles.Lookup trustedLookup() {

        try {
            @SuppressWarnings("BlockedPrivateApi") Field declaredField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            Reflections.setAccessible(declaredField);
            return (java.lang.invoke.MethodHandles.Lookup) declaredField.get(null);
        } catch (Throwable e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
            throw Exceptions.throwException(e);
        }
    }

    public static MethodHandles.Lookup privateLookupIn(Class<?> sampleClassClass) {
        return trustedLookup(); // TODO try fallback to public lookup
        // TODO: add proper implementation in java 9
    }

    // TODO: implement Lookup privateLookupIn(Class<?> targetClass, Lookup lookup) shim

}

package tools.unsafe;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

/**
 * Provides basic functionality around Unsafe object which is used by other modules internally in order to avoid circular dependencies
 * It should not be used by other applications explicitly
 * TODO: Think about protecting it with package private access modifier
 */
final class InternalUnsafe {

    private InternalUnsafe() {
    }

    static int tryGetJavaVersion(int fallbackJavaVersion) {
        try {
            String version = System.getProperty("java.version");

            if (version.startsWith("1.")) {
                version = version.substring(2, 3);
            } else {
                int dot = version.indexOf(".");
                if (dot != -1) {
                    version = version.substring(0, dot);
                }
            }
            if (version.contains("-")) {
                version = version.substring(0, version.indexOf("-"));
            }
            return Integer.parseInt(version);
        } catch (Exception e) {
            return fallbackJavaVersion;
        }
    }

    // Consider also jdk.internal.misc.Unsafe and jdk.internal.reflect.Unsafe
    static @Nonnull sun.misc.Unsafe getSunMiscUnsafe() {
        return SunMiscUnsafeHolder.UNSAFE; // TODO: silence all warning produces by JVM
    }

    private static class SunMiscUnsafeHolder {

        private final static @Nonnull sun.misc.Unsafe UNSAFE;

        static {
            sun.misc.Unsafe unsafe = null;
            try {
                Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe"); // TODO: check THE_ONE for Android as well
                f.setAccessible(true);
                unsafe = (sun.misc.Unsafe) f.get(null);
            } catch (Throwable e) {
                e.printStackTrace();
                assert false : e;
            }
            UNSAFE = unsafe;
        }

    }

}

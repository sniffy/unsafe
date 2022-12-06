package tools.unsafe;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.ReflectPermission;

/**
 * Provides basic functionality around Unsafe object which is used by other modules internally in order to avoid circular dependencies
 * It should not be used by other applications explicitly
 * TODO: Think about protecting it with package private access modifier
 */
@SuppressWarnings({"Convert2Diamond"})
final class InternalUnsafe {

    private InternalUnsafe() {
    }

    @Deprecated
    private static final int FALLBACK_JAVA_VERSION = 8;

    protected static int tryGetJavaVersion() {
        return tryGetJavaVersion(FALLBACK_JAVA_VERSION);
    }

    protected static int tryGetJavaVersion(int fallbackJavaVersion) {
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

    protected static @Nonnull RuntimeException throwException(@Nonnull Throwable e) {
        InternalUnsafe.<RuntimeException>throwAny(e);
        return new RuntimeException(e);
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void throwAny(@Nonnull Throwable e) throws E {
        throw (E) e;
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

    // Consider also jdk.internal.misc.Unsafe and jdk.internal.reflect.Unsafe
    protected static @Nonnull sun.misc.Unsafe getSunMiscUnsafe() {
        return SunMiscUnsafeHolder.UNSAFE;
    }

    protected static boolean setAccessible(@Nonnull AccessibleObject ao) throws InternalUnsafeException {

        //noinspection deprecation
        if (ao.isAccessible()) {
            return true;
        }

        if (tryGetJavaVersion() >= 16) {

            try {
                long overrideOffset = getSunMiscUnsafe().objectFieldOffset(FakeAccessibleObject.class.getDeclaredField("override"));
                getSunMiscUnsafe().putBoolean(ao, overrideOffset, true);
            } catch (NoSuchFieldException e) {
                throw new InternalUnsafeException(e);
            }

            //noinspection deprecation
            return ao.isAccessible();
        }

        ao.setAccessible(true);
        return true;

    }

    /**
     * FakeAccessibleObject class has similar layout as {@link AccessibleObject} and can be used for calculating offsets
     */
    @SuppressWarnings({"unused", "NullableProblems"})
    private static class FakeAccessibleObject implements AnnotatedElement {

        /**
         * The Permission object that is used to check whether a client
         * has sufficient privilege to defeat Java language access
         * control checks.
         */
        static final private java.security.Permission ACCESS_PERMISSION =
                new ReflectPermission("suppressAccessChecks");

        // Indicates whether language-level access checks are overridden
        // by this object. Initializes to "false". This field is used by
        // Field, Method, and Constructor.
        //
        // NOTE: for security purposes, this field must not be visible
        // outside this package.
        boolean override;

        //@Override
        public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
            return false;
        }

        //@Override
        public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
            return null;
        }

        //@Override
        public Annotation[] getAnnotations() {
            return new Annotation[0];
        }

        //@Override
        public Annotation[] getDeclaredAnnotations() {
            return new Annotation[0];
        }

        // Reflection factory used by subclasses for creating field,
        // method, and constructor accessors. Note that this is called
        // very early in the bootstrapping process.
        static final Object reflectionFactory = new Object();

        volatile Object securityCheckCache;

    }

}

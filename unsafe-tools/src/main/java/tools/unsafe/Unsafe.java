package tools.unsafe;

import tools.unsafe.reflection.UnsafeException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.clazz.UnresolvedClassRef;
import tools.unsafe.reflection.object.ObjectRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.ReflectPermission;
import java.security.ProtectionDomain;
import java.util.concurrent.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * List of ideas:
 * - Convenient way to access sun.misc.Unsafe and other "Unsafe"s
 * - Safe wrapper (or multiple implementations) around sun.misc.Unsafe
 * - Reflection library based on Unsafe (or on safe wrapper above):
 * -- add support of Method (both static and non-static)
 * -- add support of ObjectRef, ObjectFieldRef, ObjectMethodRef, etc.
 * - Tooling for attaching agent to self
 * - Tooling for disabling Jigsaw
 * - Tooling for obtaining Instrumentation instance
 * - SizeOf
 * - Esoteric stuff (invoke constructor again, invoke static constructors, etc.)
 * - Whatever is required by other tools like caches (sizeof), mocks (power reflection), etc.
 */
@SuppressWarnings({"Convert2Diamond"})
public final class Unsafe {

    private Unsafe() {
    }

    @Deprecated
    public static final int FALLBACK_JAVA_VERSION = 8;

    public static int tryGetJavaVersion() {
        return tryGetJavaVersion(FALLBACK_JAVA_VERSION);
    }

    public static int tryGetJavaVersion(int fallbackJavaVersion) {
        try {
            return getJavaVersion();
        } catch (Exception e) {
            return fallbackJavaVersion;
        }
    }

    public static int getJavaVersion() {
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
    }

    public static @Nonnull RuntimeException throwException(@Nonnull Throwable e) {
        Unsafe.<RuntimeException>throwAny(e);
        return new RuntimeException(e);
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void throwAny(@Nonnull Throwable e) throws E {
        throw (E)e;
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
    public static @Nonnull sun.misc.Unsafe getSunMiscUnsafe() {
        return SunMiscUnsafeHolder.UNSAFE;
    }

    public static int getPid() {
        try {

            if (Unsafe.getJavaVersion() >= 9) {
                return $("java.lang.ProcessHandle").getNonStaticMethod(Long.TYPE, "pid").invoke(
                        $("java.lang.ProcessHandle").getStaticMethod("current").invoke()
                ).intValue();
                //return (int) ProcessHandle.current().pid();
            } else {
                return $(ManagementFactory.getRuntimeMXBean()).$("jvm").$(Integer.TYPE, "getProcessId");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static volatile Future<Instrumentation> instrumentationFuture;

    private static final ExecutorService attachAgentExecutor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new SynchronousQueue<Runnable>(),
            new ThreadFactory() {
                //@Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                }
            });

    public static Future<Instrumentation> getInstrumentationFuture() {
        if (null == instrumentationFuture || instrumentationFuture.isCancelled()) {
            synchronized (Unsafe.class) {
                if (null == instrumentationFuture || instrumentationFuture.isCancelled()) {
                    instrumentationFuture = attachAgentExecutor.submit(new Callable<Instrumentation>() {
                        //@Override
                        public Instrumentation call() throws Exception {
                            $("sun.tools.attach.HotSpotVirtualMachine").getStaticField("ALLOW_ATTACH_SELF").trySet(true);


                            System.out.println("Hello world!");
                            UnresolvedClassRef<Object> vmClassRef = $("com.sun.tools.attach.VirtualMachine");
                            Object vm = vmClassRef.getStaticMethod(Object.class, "attach", String.class).invoke(String.valueOf(getPid()));
                            System.out.println("Attached to " + vm);

                            Manifest manifest = new Manifest();
                            //manifest.getMainAttributes().putValue("Premain-Class", "io.sniffy.unsafe.agent.UnsafeAgent");
                            manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
                            manifest.getMainAttributes().put(new Attributes.Name("Agent-Class"), "tools.unsafe.agent.UnsafeAgent");
                            manifest.getMainAttributes().put(new Attributes.Name("Can-Retransform-Classes"), "true");

                            File tempFile = File.createTempFile("unsafe-agent-", ".jar");
                            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                            JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream, manifest);
                            JarEntry jarEntry = new JarEntry("tools/unsafe/agent/UnsafeAgent.class");
                            jarOutputStream.putNextEntry(jarEntry);

                            InputStream resourceAsStream = Unsafe.class.getClassLoader().getResourceAsStream("tools/unsafe/agent/UnsafeAgent.class");
                            byte[] buffer = new byte[1024];
                            while (true) {
                                int count = resourceAsStream.read(buffer);
                                if (count == -1) {
                                    break;
                                }
                                jarOutputStream.write(buffer, 0, count);
                            }

                            jarOutputStream.closeEntry();
                            jarOutputStream.close();

                            if (null != vm) {
                                System.out.println("Attaching " + tempFile.getCanonicalPath() + " to " + vm);
                                vmClassRef.getNonStaticMethod("loadAgent", String.class).invoke(vm, tempFile.getCanonicalPath().replaceAll("\\\\", "/"));
                            }

                            while(!Thread.currentThread().isInterrupted()){
                                //do something
                                UnresolvedClassRef<Object> classRef = $("tools.unsafe.agent.UnsafeAgent");
                                if (classRef.isResolved()) {
                                    attachAgentExecutor.shutdown();
                                    return classRef.<Instrumentation>tryGetStaticField("instrumentation").getOrDefault(null);
                                } else {
                                    Thread.sleep(1);
                                }
                            }

                            return null;
                            // Instrumentation instrumentation =
                            //                $("io.sniffy.unsafe.agent.UnsafeAgent").<Instrumentation>tryGetStaticField("instrumentation").getOrDefault(null);
                        }
                    });
                }
            }
        }
        return instrumentationFuture;
    }

    @SuppressWarnings("unused")
    public static void defineSystemClass(@Nonnull @Deprecated String className, @Nonnull byte[] bytes) throws UnsafeException {
        try {
            $(sun.misc.Unsafe.class).
                    getNonStaticMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ClassLoader.class, ProtectionDomain.class).
                    invoke(getSunMiscUnsafe(), className, bytes, 0, bytes.length, null, null);
        } catch (Exception e) {
            throw new UnsafeException(e);
        }
    }

    public static @Nonnull <C> UnresolvedClassRef<C> $(@Nonnull String className) {
        try {
            //noinspection unchecked
            Class<C> clazz = (Class<C>)Class.forName(className);
            return new UnresolvedClassRef<C>(new ClassRef<C>( clazz), null);
        } catch (Throwable e) {
            return new UnresolvedClassRef<C>(null, e);
        }
    }

    public static <C> ObjectRef<C> $(@Nonnull C object) {
        //noinspection unchecked
        return new ObjectRef<C>(
                (ClassRef<C>) $(object.getClass(), Object.class),
                object
        );
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    public static <C,C1 extends C> ClassRef<C> $(@Nonnull Class<C1> clazz, @SuppressWarnings("unused") @Nullable Class<C> cast) {
        return (ClassRef<C>) $(clazz);
    }

    @Nonnull
    public static <C> UnresolvedClassRef<C> $(@Nonnull String className, @SuppressWarnings("unused") @Nullable Class<C> cast) {
        return $(className);
    }

    // TODO: introduce caching
    @Nonnull
    public static <C> ClassRef<C> $(@Nonnull Class<C> clazz) {
        return new ClassRef<C>(clazz);
    }

    public static boolean setAccessible(@Nonnull AccessibleObject ao) throws UnsafeException {

        //noinspection deprecation
        if (ao.isAccessible()) {
            return true;
        }

        if (tryGetJavaVersion() >= 16) {

            try {
                long overrideOffset = getSunMiscUnsafe().objectFieldOffset(FakeAccessibleObject.class.getDeclaredField("override"));
                getSunMiscUnsafe().putBoolean(ao, overrideOffset, true);
            } catch (NoSuchFieldException e) {
                throw new UnsafeException(e);
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

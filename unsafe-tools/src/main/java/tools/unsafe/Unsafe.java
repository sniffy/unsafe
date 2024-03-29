package tools.unsafe;

import tools.unsafe.reflection.UnsafeException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.clazz.UnresolvedClassRef;
import tools.unsafe.spi.ProvidersBootstrap;
import tools.unsafe.vm.UnsafeVirtualMachine;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.instrument.Instrumentation;
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
public final class Unsafe {

    private Unsafe() {
    }

    public static int tryGetJavaVersion(int fallbackJavaVersion) {
        return Java.versionWithFallback(fallbackJavaVersion);
    }

    public static @Nonnull RuntimeException throwException(@Nonnull Throwable e) {
        return Exceptions.throwException(e);
    }

    // Consider also jdk.internal.misc.Unsafe and jdk.internal.reflect.Unsafe
    public static @Nonnull sun.misc.Unsafe getSunMiscUnsafe() {
        return Unsafe.getSunMiscUnsafe();
    }

    private static volatile Future<Instrumentation> instrumentationFuture;

    private static final ExecutorService attachAgentExecutor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new SynchronousQueue<Runnable>(),
            new ThreadFactory() {
                //@Override
                public Thread newThread(@Nonnull Runnable r) {
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

                            UnsafeVirtualMachine.attachToSelf();

                            UnresolvedClassRef.of("sun.tools.attach.HotSpotVirtualMachine").staticBooleanField("ALLOW_ATTACH_SELF").trySet(true);


                            System.out.println("Hello world!");
                            UnresolvedClassRef<Object> vmClassRef = UnresolvedClassRef.of("com.sun.tools.attach.VirtualMachine");
                            Object vm = vmClassRef.staticMethod(Object.class, "attach", String.class).invoke(String.valueOf(UnsafeVirtualMachine.getPid()));
                            System.out.println("Attached to " + vm);

                            Manifest manifest = new Manifest();
                            manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
                            manifest.getMainAttributes().put(new Attributes.Name("Agent-Class"), "tools.unsafe.agent.UnsafeAgent");
                            manifest.getMainAttributes().put(new Attributes.Name("Can-Retransform-Classes"), "true");

                            File tempFile = File.createTempFile("unsafe-agent-", ".jar");
                            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                            JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream, manifest);
                            JarEntry jarEntry = new JarEntry("tools/unsafe/agent/UnsafeAgent.class");
                            jarOutputStream.putNextEntry(jarEntry);

                            InputStream resourceAsStream = Unsafe.class.getClassLoader().getResourceAsStream("tools/unsafe/agent/UnsafeAgent.class");
                            // TODO: do not build JAR manually
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
                                vmClassRef.method("loadAgent", String.class).invoke(vm, tempFile.getCanonicalPath().replaceAll("\\\\", "/"));
                            }

                            while (!Thread.currentThread().isInterrupted()) {
                                //do something
                                UnresolvedClassRef<Object> classRef = UnresolvedClassRef.of("tools.unsafe.agent.UnsafeAgent");
                                if (classRef.isResolved()) {
                                    attachAgentExecutor.shutdown();
                                    return classRef.<Instrumentation>staticField("instrumentation").getOrDefault(null);
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
            ClassRef.of(sun.misc.Unsafe.class).
                    method("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ClassLoader.class, ProtectionDomain.class).
                    invoke(getSunMiscUnsafe(), className, bytes, 0, bytes.length, null, null);
        } catch (Exception e) {
            throw new UnsafeException(e);
        }
    }

    public static boolean setAccessible(@Nonnull AccessibleObject ao) throws UnsafeException {

        //noinspection RedundantSuppression
        {
            //noinspection deprecation
            if (ao.isAccessible()) {
                return true;
            }
        }

        if (tryGetJavaVersion(8) >= 16) {

            try {
                long overrideOffset = getSunMiscUnsafe().objectFieldOffset(FakeAccessibleObject.class.getDeclaredField("override"));
                getSunMiscUnsafe().putBoolean(ao, overrideOffset, true);
                System.out.println("Making " + ao + " accessible via Unsafe and FakeAccessibleObject");
            } catch (NoSuchFieldException e) {
                throw new UnsafeException(e);
            }

            //noinspection RedundantSuppression
            {
                //noinspection deprecation
                return ao.isAccessible();
            }
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

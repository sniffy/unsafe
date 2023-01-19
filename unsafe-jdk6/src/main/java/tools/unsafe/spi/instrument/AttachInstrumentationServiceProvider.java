package tools.unsafe.spi.instrument;

import tools.unsafe.Unsafe;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Future;

public class AttachInstrumentationServiceProvider implements InstrumentationServiceProvider {

    private static volatile Future<Instrumentation> instrumentationFuture;

    @Override
    public Future<Instrumentation> getInstrumentationFuture() {
        if (null == instrumentationFuture || instrumentationFuture.isCancelled()) {
            synchronized (Unsafe.class) {
                if (null == instrumentationFuture || instrumentationFuture.isCancelled()) {
                    /*instrumentationFuture = attachAgentExecutor.submit(new Callable<Instrumentation>() {
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
                    });*/
                }
            }
        }
        return instrumentationFuture;
    }

}

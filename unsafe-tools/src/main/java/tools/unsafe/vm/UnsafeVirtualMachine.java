package tools.unsafe.vm;

import tools.unsafe.reflection.UnsafeException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.UnresolvedClassRef;
import tools.unsafe.reflection.object.ObjectRef;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class UnsafeVirtualMachine {

    private final /*com.sun.tools.attach.VirtualMachine*/ Object virtualMachine;

    public UnsafeVirtualMachine(/*VirtualMachine*/ Object virtualMachine) {
        this.virtualMachine = virtualMachine;
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

    public static int getPid() {
        try {

            if (getJavaVersion() >= 9) {
                return UnresolvedClassRef.of("java.lang.ProcessHandle").method(Long.TYPE, "pid").invoke(
                        UnresolvedClassRef.of("java.lang.ProcessHandle").staticMethod(Integer.TYPE, "current").invoke()
                ).intValue();
                //return (int) ProcessHandle.current().pid();
            } else {
                return ObjectRef.<RuntimeMXBean>of(ManagementFactory.getRuntimeMXBean()).field("jvm").objectRef().invoke(Integer.TYPE, "getProcessId", new Class<?>[0], new Object[0]);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static UnsafeVirtualMachine attachToSelf() throws UnsafeException {

        if (getJavaVersion() <= 8) {
            try {
                String binPath = System.getProperty("sun.boot.library.path");
                // remove jre/bin, replace with lib
                String libPath = binPath.substring(0, binPath.length() - 7) + "lib";
                //URLClassLoader loader = (URLClassLoader) UnsafeVirtualMachine.class.getClassLoader();
                URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addURLMethod.setAccessible(true);
                File toolsJar = new File(libPath + "/tools.jar");
                if (!toolsJar.exists()) throw new RuntimeException(toolsJar.getAbsolutePath() + " does not exist");
                addURLMethod.invoke(loader, new File(libPath + "/tools.jar").toURI().toURL());
            } catch (MalformedURLException e) {
                throw new UnsafeException(e);
            } catch (InvocationTargetException e) {
                throw new UnsafeException(e);
            } catch (NoSuchMethodException e) {
                throw new UnsafeException(e);
            } catch (IllegalAccessException e) {
                throw new UnsafeException(e);
            }
        }


        UnresolvedClassRef.of("sun.tools.attach.HotSpotVirtualMachine").staticBooleanField("ALLOW_ATTACH_SELF").trySet(true);
        UnresolvedClassRef<Object> vmClassRef = UnresolvedClassRef.of("com.sun.tools.attach.VirtualMachine");
        Object vm = null;
        try {
            vm = vmClassRef.staticMethod(Object.class, "attach", String.class).invoke(String.valueOf(UnsafeVirtualMachine.getPid()));
        } catch (UnsafeInvocationException e) {
            throw new UnsafeException(e);
        } catch (InvocationTargetException e) {
            throw new UnsafeException(e);
        }
        return new UnsafeVirtualMachine(/*(com.sun.tools.attach.VirtualMachine)*/ vm);
    }

}

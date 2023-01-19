package tools.unsafe;

import tools.unsafe.internal.UnsafeToolsLogging;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Modules {

    public static final Method IMPL_ADD_OPENS;

    static {
        Method method = null;
        try {
            method = Module.class.getDeclaredMethod("implAddOpens", String.class);
            Reflections.setAccessible(method);
        } catch (Throwable e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
            throw Exceptions.throwException(e);
        }
        IMPL_ADD_OPENS = method;
    }

    /*public static final UnresolvedVoidDynamicOneParamMethodRef<Object, String> IMPL_ADD_OPENS_METHOD =
            UnresolvedClassRef.of(JAVA_LANG_MODULE).method("implAddOpens", String.class);

    static {
        assert UnsafeVirtualMachine.getJavaVersion() < 9 || IMPL_ADD_OPENS_METHOD.isResolved();
    }*/

    // Do not link statically to java.land.Module since it's not available before Java 9
    private final Module module;

    public Modules(Module module) {
        this.module = module;
    }

    public void addOpens(@Nonnull String packageName) throws InvocationTargetException, IllegalAccessException {
        IMPL_ADD_OPENS.invoke(module, packageName);
    }

    public boolean tryAddOpens(@Nonnull String packageName) {
        try {
            addOpens(packageName);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

}

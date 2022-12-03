package tools.unsafe.reflection.module;

import tools.unsafe.Unsafe;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.method.generic.unresolved.UnresolvedDynamicMethodRef;
import tools.unsafe.reflection.method.voidresult.unresolved.UnresolvedVoidDynamicMethodRef;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;

import static tools.unsafe.Unsafe.$;

public class ModuleRef {

    public static final String JAVA_LANG_MODULE = "java.lang.Module";
    public static final UnresolvedVoidDynamicMethodRef<Object> IMPL_ADD_OPENS_METHOD =
            $(JAVA_LANG_MODULE).method("implAddOpens", String.class);

    static {
        assert Unsafe.getJavaVersion() < 9 || IMPL_ADD_OPENS_METHOD.isResolved();
    }

    // Do not link statically to java.land.Module since it's not available before Java 9
    private final /* Module */ Object module;

    public ModuleRef(/* Module */ @Nonnull Object module) {
        assert JAVA_LANG_MODULE.equals(module.getClass().getName());
        this.module = module;
    }

    public void addOpens(@Nonnull String packageName) throws UnsafeInvocationException, InvocationTargetException {
        IMPL_ADD_OPENS_METHOD.invoke(module, packageName);
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

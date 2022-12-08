package tools.unsafe.reflection.module;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class UnresolvedModuleRef extends UnresolvedRef<ModuleRef> {

    public UnresolvedModuleRef(@Nonnull Callable<ModuleRef> refSupplier) {
        super(refSupplier);
    }

    public void addOpens(String packageName) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        resolve().addOpens(packageName);
    }

    public boolean tryAddOpens(String packageName) {
        try {
            return resolve().tryAddOpens(packageName);
        } catch (UnresolvedRefException e) {
            return false;
        }
    }

}

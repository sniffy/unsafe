package tools.unsafe.reflection.clazz;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.constructor.UnresolvedZeroArgsClassConstructorRef;
import tools.unsafe.reflection.constructor.ZeroArgsClassConstructorRef;
import tools.unsafe.reflection.field.FieldFilter;
import tools.unsafe.reflection.field.booleans.resolved.ResolvedDynamicBooleanFieldRef;
import tools.unsafe.reflection.field.booleans.resolved.ResolvedStaticBooleanFieldRef;
import tools.unsafe.reflection.field.booleans.unresolved.UnresolvedDynamicBooleanFieldRef;
import tools.unsafe.reflection.field.booleans.unresolved.UnresolvedStaticBooleanFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedDynamicObjectFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedStaticObjectFieldRef;
import tools.unsafe.reflection.field.objects.unresolved.UnresolvedDynamicObjectFieldRef;
import tools.unsafe.reflection.field.objects.unresolved.UnresolvedStaticObjectFieldRef;
import tools.unsafe.reflection.method.typedresult.resolved.ResolvedDynamicTypedMethodRef;
import tools.unsafe.reflection.method.typedresult.resolved.ResolvedStaticTypedMethodRef;
import tools.unsafe.reflection.method.typedresult.unresolved.UnresolvedDynamicTypedMethodRef;
import tools.unsafe.reflection.method.typedresult.unresolved.UnresolvedStaticTypedMethodRef;
import tools.unsafe.reflection.method.voidresult.oneparam.resolved.ResolvedVoidDynamicOneParamMethodRef;
import tools.unsafe.reflection.method.voidresult.oneparam.unresolved.UnresolvedVoidDynamicOneParamMethodRef;
import tools.unsafe.reflection.method.voidresult.resolved.ResolvedStaticVoidMethodRef;
import tools.unsafe.reflection.method.voidresult.resolved.ResolvedVoidDynamicMethodRef;
import tools.unsafe.reflection.method.voidresult.unresolved.UnresolvedStaticVoidMethodRef;
import tools.unsafe.reflection.method.voidresult.unresolved.UnresolvedVoidDynamicMethodRef;
import tools.unsafe.reflection.module.ModuleRef;
import tools.unsafe.reflection.module.UnresolvedModuleRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Unresolved reference to class
 *
 * @param <C>
 * @see ClassRef
 * @see UnresolvedRef
 */
public class UnresolvedClassRef<C> extends UnresolvedRef<ClassRef<C>> {

    public UnresolvedClassRef(@Nonnull Callable<ClassRef<C>> refSupplier) {
        super(refSupplier);
    }

    public static @Nonnull <C> UnresolvedClassRef<C> of(@Nonnull final String className) {
        return new UnresolvedClassRef<C>(new Callable<ClassRef<C>>() {
            @Override
            public ClassRef<C> call() throws Exception {
                //noinspection unchecked
                return new ClassRef<C>((Class<C>) Class.forName(className));
            }
        });
    }

    @Nonnull
    public static <C> UnresolvedClassRef<C> of(@Nonnull String className, @SuppressWarnings("unused") @Nullable Class<C> cast) {
        return of(className);
    }

    public @Nonnull UnresolvedModuleRef getModuleRef() /*throws UnresolvedRefException*/ {
        return new UnresolvedModuleRef(new Callable<ModuleRef>() {
            @Override
            public ModuleRef call() throws Exception {
                return resolve().getModuleRef().resolve();
            }
        });
    }


    public @Nonnull UnresolvedZeroArgsClassConstructorRef<C> getConstructor() throws UnresolvedRefException {
        return new UnresolvedZeroArgsClassConstructorRef<C>(new Callable<ZeroArgsClassConstructorRef<C>>() {
            @Override
            public ZeroArgsClassConstructorRef<C> call() throws Exception {
                return resolve().getConstructor().resolve();
            }
        });
    }

    // new fields methods start

    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C, T> staticField(@Nonnull final String fieldName) {
        return new UnresolvedStaticObjectFieldRef<C, T>(new Callable<ResolvedStaticObjectFieldRef<C, T>>() {
            @Override
            public ResolvedStaticObjectFieldRef<C, T> call() throws Exception {
                return resolve().<T>staticField(fieldName).resolve();
            }
        });
    }

    public @Nonnull <T> UnresolvedDynamicObjectFieldRef<C, T> field(@Nonnull final String fieldName) {
        return new UnresolvedDynamicObjectFieldRef<C, T>(new Callable<ResolvedDynamicObjectFieldRef<C, T>>() {
            @Override
            public ResolvedDynamicObjectFieldRef<C, T> call() throws Exception {
                return resolve().<T>field(fieldName).resolve();
            }
        });
    }

    public @Nonnull UnresolvedStaticBooleanFieldRef<C> staticBooleanField(@Nonnull final String fieldName) {
        return new UnresolvedStaticBooleanFieldRef<C>(new Callable<ResolvedStaticBooleanFieldRef<C>>() {
            @Override
            public ResolvedStaticBooleanFieldRef<C> call() throws Exception {
                return resolve().staticBooleanField(fieldName).resolve();
            }
        });
    }

    public @Nonnull UnresolvedDynamicBooleanFieldRef<C> booleanField(@Nonnull final String fieldName) {
        return new UnresolvedDynamicBooleanFieldRef<C>(new Callable<ResolvedDynamicBooleanFieldRef<C>>() {
            @Override
            public ResolvedDynamicBooleanFieldRef<C> call() throws Exception {
                return resolve().booleanField(fieldName).resolve();
            }
        });
    }

    // new fields methods end


    public @Nonnull <T> UnresolvedDynamicObjectFieldRef<C, T> findFirstNonStaticField(@Nullable FieldFilter fieldFilter, boolean recursive) throws UnresolvedRefException {
        return resolve().findFirstNonStaticField(fieldFilter, recursive);
    }

    public @Nonnull Map<String, ResolvedDynamicObjectFieldRef<C, Object>> findNonStaticFields(@Nullable FieldFilter fieldFilter, boolean recursive) throws UnresolvedRefException {
        return resolve().findNonStaticFields(fieldFilter, recursive);
    }

    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C, T> findFirstStaticField(@Nullable FieldFilter fieldFilter, boolean recursive) throws UnresolvedRefException {
        return resolve().findFirstStaticField(fieldFilter, recursive);
    }

    public @Nonnull Map<String, ResolvedStaticObjectFieldRef<C, Object>> findStaticFields(@Nullable FieldFilter fieldFilter, boolean recursive) throws UnresolvedRefException {
        return resolve().findStaticFields(fieldFilter, recursive);
    }

    // methods

    public @Nonnull UnresolvedVoidDynamicMethodRef<C> method(@Nonnull final String methodName, @Nonnull final Class<?>... parameterTypes) {
        return new UnresolvedVoidDynamicMethodRef<C>(new Callable<ResolvedVoidDynamicMethodRef<C>>() {
            @Override
            public ResolvedVoidDynamicMethodRef<C> call() throws Exception {
                return resolve().method(methodName, parameterTypes).resolve();
            }
        });
    }

    public @Nonnull UnresolvedStaticVoidMethodRef<C> staticMethod(@Nonnull final String methodName, @Nonnull final Class<?>... parameterTypes) {
        return new UnresolvedStaticVoidMethodRef<C>(new Callable<ResolvedStaticVoidMethodRef<C>>() {
            @Override
            public ResolvedStaticVoidMethodRef<C> call() throws Exception {
                return resolve().staticMethod(methodName, parameterTypes).resolve();
            }
        });
    }

    public @Nonnull <T> UnresolvedStaticTypedMethodRef<C, T> staticMethod(@Nonnull final Class<T> returnType, @Nonnull final String methodName, @Nonnull final Class<?>... parameterTypes) {
        return new UnresolvedStaticTypedMethodRef<C, T>(new Callable<ResolvedStaticTypedMethodRef<C, T>>() {
            @Override
            public ResolvedStaticTypedMethodRef<C, T> call() throws Exception {
                return resolve().staticMethod(returnType, methodName, parameterTypes).resolve();
            }
        });
    }

    public @Nonnull <T> UnresolvedDynamicTypedMethodRef<C, T> method(@Nonnull final Class<T> returnType, @Nonnull final String methodName, @Nonnull final Class<?>... parameterTypes) {
        return new UnresolvedDynamicTypedMethodRef<C, T>(new Callable<ResolvedDynamicTypedMethodRef<C, T>>() {
            @Override
            public ResolvedDynamicTypedMethodRef<C, T> call() throws Exception {
                return resolve().method(returnType, methodName, parameterTypes).resolve();
            }
        });
    }

    // one param methods


    public @Nonnull <P1> UnresolvedVoidDynamicOneParamMethodRef<C, P1> method(@Nonnull final String methodName, @Nonnull final Class<P1> C1) {
        return new UnresolvedVoidDynamicOneParamMethodRef<C, P1>(new Callable<ResolvedVoidDynamicOneParamMethodRef<C, P1>>() {
            @Override
            public ResolvedVoidDynamicOneParamMethodRef<C, P1> call() throws Exception {
                return resolve().method(methodName, C1).resolve();
            }
        });
    }

    // other methods

    @SuppressWarnings("unused")
    public void retransform() throws ExecutionException, InterruptedException, UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        resolve().retransform();
    }

    /**
     * Usefull to survive shade plugin
     *
     * @param className
     * @param <S>
     * @return
     */
    public @Nonnull <S> UnresolvedClassRef<S> siblingClass(@Nonnull final String className) {
        // TODO: introduce PackageRef
        return new UnresolvedClassRef<S>(new Callable<ClassRef<S>>() {
            @Override
            public ClassRef<S> call() throws Exception {
                //noinspection unchecked
                return new ClassRef<S>((Class<S>) Class.forName(resolve().getClazz().getPackage().getName() + "." + className));
            }
        });
    }

}
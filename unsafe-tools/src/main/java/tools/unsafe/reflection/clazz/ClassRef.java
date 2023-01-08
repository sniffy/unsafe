package tools.unsafe.reflection.clazz;

import tools.unsafe.Unsafe;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.constructor.UnresolvedZeroArgsClassConstructorRef;
import tools.unsafe.reflection.constructor.ZeroArgsClassConstructorRef;
import tools.unsafe.reflection.field.FieldFilter;
import tools.unsafe.reflection.field.FieldFilters;
import tools.unsafe.reflection.field.booleans.unresolved.UnresolvedDynamicBooleanFieldRef;
import tools.unsafe.reflection.field.booleans.unresolved.UnresolvedStaticBooleanFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedDynamicObjectFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedStaticObjectFieldRef;
import tools.unsafe.reflection.field.objects.unresolved.UnresolvedDynamicObjectFieldRef;
import tools.unsafe.reflection.field.objects.unresolved.UnresolvedStaticObjectFieldRef;
import tools.unsafe.reflection.method.MethodFilter;
import tools.unsafe.reflection.method.MethodKey;
import tools.unsafe.reflection.method.genericresult.resolved.ResolvedDynamicMethodRef;
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
import tools.unsafe.reflection.object.ObjectRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

// TODO: use declaredFields0 and declaredMethods0 to also cover super system methods

/**
 * Provides a set of convenient methods for working with classes via reflection
 *
 * @param <C>
 */
public class ClassRef<C> {

    // TODO: add functionality to create new instance without invoking constructor
    // TODO: add functionality to invoke static initializer

    private final @Nonnull Class<C> clazz;

    public ClassRef(@Nonnull Class<C> clazz) {
        this.clazz = clazz;
    }

    @Nonnull
    public static <C> ClassRef<C> of(@Nonnull Class<C> clazz) {
        return new ClassRef<C>(clazz);
    }

    @Nonnull
    public static <C, C1 extends C> ClassRef<C> of(@Nonnull Class<C1> clazz, @SuppressWarnings("unused") @Nullable Class<C> cast) {
        //noinspection unchecked
        return (ClassRef<C>) of(clazz);
    }

    public @Nonnull UnresolvedModuleRef getModuleRef() {
        return new UnresolvedModuleRef(new Callable<ModuleRef>() {
            @Override
            public ModuleRef call() throws Exception {
                //noinspection rawtypes
                ClassRef<Class> classClassRef = of(Class.class);
                Object module = classClassRef.method(UnresolvedClassRef.of("java.lang.Module"), "getModule").invoke(clazz);
                return new ModuleRef(module);
            }
        });
    }

    public @Nonnull <T> UnresolvedDynamicObjectFieldRef<C, T> findFirstNonStaticField(@Nullable final FieldFilter fieldFilter, final boolean recursive) {
        return new UnresolvedDynamicObjectFieldRef<C, T>(new Callable<ResolvedDynamicObjectFieldRef<C, T>>() {
            @Override
            public ResolvedDynamicObjectFieldRef<C, T> call() {
                Class<? super C> clazz = getClazz();
                while (clazz != Object.class) {
                    Field[] declaredFields = clazz.getDeclaredFields();
                    for (final Field declaredField : declaredFields) {
                        if (!Modifier.isStatic(declaredField.getModifiers()) && (null == fieldFilter || fieldFilter.include(declaredField.getName(), declaredField))) {
                            return new ResolvedDynamicObjectFieldRef<C, T>(ClassRef.this, declaredField);
                        }
                    }
                    if (recursive) {
                        clazz = clazz.getSuperclass();
                    } else {
                        break;
                    }
                }
                throw new NoSuchFieldError();
            }
        });
    }

    public @Nonnull Map<String, ResolvedDynamicObjectFieldRef<C, Object>> findNonStaticFields(@Nullable FieldFilter fieldFilter, boolean recursive) {
        Map<String, ResolvedDynamicObjectFieldRef<C, Object>> fields = new HashMap<String, ResolvedDynamicObjectFieldRef<C, Object>>();
        Class<? super C> clazz = this.clazz;
        while (clazz != Object.class) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                if (!Modifier.isStatic(field.getModifiers()) && (null == fieldFilter || fieldFilter.include(field.getName(), field))) {
                    fields.put(field.getName(), new ResolvedDynamicObjectFieldRef<C, Object>(this, field));
                }
            }
            if (recursive) {
                clazz = clazz.getSuperclass();
            } else {
                break;
            }
        }
        return fields;
    }

    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C, T> findFirstStaticField(@Nullable final FieldFilter fieldFilter, final boolean recursive) {
        return new UnresolvedStaticObjectFieldRef<C, T>(new Callable<ResolvedStaticObjectFieldRef<C, T>>() {
            @Override
            public ResolvedStaticObjectFieldRef<C, T> call() {
                Class<? super C> clazz = getClazz();
                while (clazz != Object.class) {
                    Field[] declaredFields = clazz.getDeclaredFields();
                    for (final Field declaredField : declaredFields) {
                         if (Modifier.isStatic(declaredField.getModifiers()) && (null == fieldFilter || fieldFilter.include(declaredField.getName(), declaredField))) {
                             return new ResolvedStaticObjectFieldRef<C, T>(ClassRef.this, declaredField);
                         }
                    }
                    if (recursive) {
                        clazz = clazz.getSuperclass();
                    } else {
                        break;
                    }
                }
                throw new NoSuchFieldError();
            }
        });
    }

    public @Nonnull Map<String, ResolvedStaticObjectFieldRef<C, Object>> findStaticFields(@Nullable FieldFilter fieldFilter, boolean recursive) {
        Map<String, ResolvedStaticObjectFieldRef<C, Object>> fields = new HashMap<String, ResolvedStaticObjectFieldRef<C, Object>>();
        Class<? super C> clazz = this.clazz;
        while (clazz != Object.class) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                // TODO: filter out synthetic members here and everywhere
                if (Modifier.isStatic(field.getModifiers()) && (null == fieldFilter || fieldFilter.include(field.getName(), field))) {
                    fields.put(field.getName(), new ResolvedStaticObjectFieldRef<C, Object>(this, field));
                }
            }
            if (recursive) {
                clazz = clazz.getSuperclass();
            } else {
                break;
            }
        }
        return fields;
    }

    public void copyFields(@Nonnull C from, @Nonnull C to) throws UnsafeInvocationException {

        // TODO: introduce caching here
        for (ResolvedDynamicObjectFieldRef<C, Object> fieldRef : findNonStaticFields(null, true).values()) {
            fieldRef.copy(from, to);
        }

    }

    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C, T> staticField(@Nonnull String fieldName) {
        return findFirstStaticField(FieldFilters.byName(fieldName), true);
    }

    public @Nonnull <T> UnresolvedDynamicObjectFieldRef<C, T> field(@Nonnull String fieldName) {
        return findFirstNonStaticField(FieldFilters.byName(fieldName), true);
    }

    public @Nonnull UnresolvedStaticBooleanFieldRef<C> staticBooleanField(@Nonnull String fieldName) {
        return findFirstStaticField(
                FieldFilters.and(
                        FieldFilters.ofType(Boolean.TYPE),
                        FieldFilters.byName(fieldName)
                ), true
        ).asBooleanFieldRef();
    }

    public @Nonnull UnresolvedDynamicBooleanFieldRef<C> booleanField(@Nonnull String fieldName) {
        return findFirstNonStaticField(
                FieldFilters.and(
                        FieldFilters.ofType(Boolean.TYPE),
                        FieldFilters.byName(fieldName)
                ), true
        ).asBooleanFieldRef();
    }

    // TODO: should parameters be nullable ?
    public @Nonnull UnresolvedStaticVoidMethodRef<C> staticMethod(@Nonnull final String methodName, @Nonnull final Class<?>... parameters) {
        return new UnresolvedStaticVoidMethodRef<C>(new Callable<ResolvedStaticVoidMethodRef<C>>() {
            @Override
            public ResolvedStaticVoidMethodRef<C> call() throws Exception {
                return new ResolvedStaticVoidMethodRef<C>(ClassRef.this, getDeclaredMethod(methodName, parameters));
            }
        });
    }

    // TODO: should parameters be nullable ?
    public @Nonnull <T> UnresolvedStaticTypedMethodRef<C, T> staticMethod(@SuppressWarnings("unused") @Nonnull Class<T> returnType, @Nonnull final String methodName, @Nonnull final Class<?>... parameters) {
        return new UnresolvedStaticTypedMethodRef<C, T>(new Callable<ResolvedStaticTypedMethodRef<C, T>>() {
            @Override
            public ResolvedStaticTypedMethodRef<C, T> call() throws Exception {
                // TODO: validate it is static
                return new ResolvedStaticTypedMethodRef<C, T>(ClassRef.this, getDeclaredMethod(methodName, parameters));
            }
        });
    }

    public @Nonnull UnresolvedVoidDynamicMethodRef<C> method(@Nonnull final String methodName, @Nonnull final Class<?>... parameters) {
        return new UnresolvedVoidDynamicMethodRef<C>(new Callable<ResolvedVoidDynamicMethodRef<C>>() {
            @Override
            public ResolvedVoidDynamicMethodRef<C> call() throws Exception {
                return new ResolvedVoidDynamicMethodRef<C>(ClassRef.this, getDeclaredMethod(methodName, parameters));
            }
        });
    }

    public @Nonnull <T> UnresolvedDynamicTypedMethodRef<C, T> method(@SuppressWarnings("unused") @Nonnull Class<T> returnType, @Nonnull final String methodName, @Nonnull final Class<?>... parameters) {
        return new UnresolvedDynamicTypedMethodRef<C, T>(new Callable<ResolvedDynamicTypedMethodRef<C, T>>() {
            @Override
            public ResolvedDynamicTypedMethodRef<C, T> call() throws Exception {
                return new ResolvedDynamicTypedMethodRef<C, T>(ClassRef.this, getDeclaredMethod(methodName, parameters));
            }
        });
    }

    public @Nonnull <T> UnresolvedDynamicTypedMethodRef<C, T> method(@SuppressWarnings("unused") @Nonnull ClassRef<T> returnType, @Nonnull final String methodName, @Nonnull final Class<?>... parameters) {
        return new UnresolvedDynamicTypedMethodRef<C, T>(new Callable<ResolvedDynamicTypedMethodRef<C, T>>() {
            @Override
            public ResolvedDynamicTypedMethodRef<C, T> call() throws Exception {
                return new ResolvedDynamicTypedMethodRef<C, T>(ClassRef.this, getDeclaredMethod(methodName, parameters));
            }
        });
    }

    public @Nonnull <T> UnresolvedDynamicTypedMethodRef<C, T> method(@SuppressWarnings("unused") @Nonnull UnresolvedClassRef<T> returnType, @Nonnull final String methodName, @Nonnull final Class<?>... parameters) {
        return new UnresolvedDynamicTypedMethodRef<C, T>(new Callable<ResolvedDynamicTypedMethodRef<C, T>>() {
            @Override
            public ResolvedDynamicTypedMethodRef<C, T> call() throws Exception {
                return new ResolvedDynamicTypedMethodRef<C, T>(ClassRef.this, getDeclaredMethod(methodName, parameters));
            }
        });
    }

    // one param methods

    public @Nonnull <P1> UnresolvedVoidDynamicOneParamMethodRef<C, P1> method(@Nonnull final String methodName, @Nonnull final Class<P1> C1) {
        return new UnresolvedVoidDynamicOneParamMethodRef<C, P1>(new Callable<ResolvedVoidDynamicOneParamMethodRef<C, P1>>() {
            @Override
            public ResolvedVoidDynamicOneParamMethodRef<C, P1> call() throws Exception {
                return new ResolvedVoidDynamicOneParamMethodRef<C, P1>(ClassRef.this, getDeclaredMethod(methodName, C1));
            }
        });
    }

    // TODO: add "field" and "method" method which would return generic ref working with both static and non static members

    // TODO: add helper methods for getting constructors with arguments


    public @Nonnull UnresolvedZeroArgsClassConstructorRef<C> getConstructor() {
        return new UnresolvedZeroArgsClassConstructorRef<C>(new Callable<ZeroArgsClassConstructorRef<C>>() {
            @Override
            public ZeroArgsClassConstructorRef<C> call() throws Exception {
                Constructor<C> declaredConstructor = clazz.getDeclaredConstructor();
                if (Unsafe.setAccessible(declaredConstructor)) {
                    return new ZeroArgsClassConstructorRef<C>(declaredConstructor);
                } else {
                    throw new UnresolvedRefException("Constructor " + clazz.getName() + "." + declaredConstructor.getName() + "() is not accessible");
                }
            }
        });
    }

    // methods


    public @Nonnull Map<MethodKey, ResolvedDynamicMethodRef<C>> getNonStaticMethods(@Nullable Class<? super C> upperBound, @Nullable MethodFilter methodFilter) {
        Map<MethodKey, ResolvedDynamicMethodRef<C>> methodRefs = new HashMap<MethodKey, ResolvedDynamicMethodRef<C>>();
        Class<? super C> clazz = this.clazz;
        while (clazz != (null == upperBound ? Object.class : upperBound)) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    MethodKey methodKey = new MethodKey(method);
                    if (null == methodFilter || methodFilter.include(methodKey, method)) {
                        methodRefs.put(methodKey, new ResolvedDynamicMethodRef<C>(this, method));
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }

        return methodRefs;
    }

    private @Nonnull Method getDeclaredMethod(@Nonnull String methodName, @Nonnull Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?> clazz = this.clazz;
        while (null != clazz) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameterTypes)) {
                    return method;
                }
            }
            if (clazz == clazz.getSuperclass()) {
                clazz = null;
            } else {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException();
    }


    @SuppressWarnings("SpellCheckingInspection")
    public void retransform() throws ExecutionException, InterruptedException, UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        Instrumentation instrumentation = Unsafe.getInstrumentationFuture().get();
        // invoke Instrumentation.retransformClasses via reflection since it's now available on Java 1.5
        ObjectRef.of(instrumentation).invoke(Void.class, "retransformClasses",
                new Class<?>[]{Class[].class},
                new Object[]{new Class[]{clazz}}
        );
        // TODO: use redefine method on Java 1.5
    }

    public void ensureClassInitialized() {
        Unsafe.getSunMiscUnsafe().ensureClassInitialized(clazz);
    }

    public ObjectRef<C> objectRef(C object) {
        return new ObjectRef<C>(this, object);
    }

    /**
     * Usefull to survive shade plugin
     *
     * @param className
     * @param <S>
     * @return
     */
    public @Nonnull <S> UnresolvedClassRef<S> siblingClass(@Nonnull String className) {
        // TODO: introduce PackageRef
        return UnresolvedClassRef.of(this.clazz.getPackage().getName() + "." + className);
    }

    @Nonnull
    public Class<C> getClazz() {
        return clazz;
    }
}

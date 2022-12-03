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
import tools.unsafe.reflection.method.generic.resolved.ResolvedDynamicMethodRef;
import tools.unsafe.reflection.method.typed.resolved.ResolvedDynamicTypedMethodRef;
import tools.unsafe.reflection.method.typed.resolved.ResolvedStaticTypedMethodRef;
import tools.unsafe.reflection.method.typed.unresolved.UnresolvedDynamicTypedMethodRef;
import tools.unsafe.reflection.method.typed.unresolved.UnresolvedStaticTypedMethodRef;
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
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

// TODO: use declaredFields0 and declaredMethods0 to also cover super system methods

/**
 * Provides a set of convenient methods for working with classes via reflection
 *
 * @param <C>
 */
@SuppressWarnings({"Convert2Diamond"})
public class ClassRef<C> {

    private final @Nonnull Class<C> clazz;

    public ClassRef(@Nonnull Class<C> clazz) {
        this.clazz = clazz;
    }

    public @Nonnull UnresolvedModuleRef getModuleRef() {
        try {
            Class<?> moduleClass = Class.forName("java.lang.Module");
            //noinspection rawtypes
            ClassRef<Class> classClassRef = Unsafe.$(Class.class);
            Object module = classClassRef.method(Object.class, "getModule").invoke(clazz);
            return new UnresolvedModuleRef(new ModuleRef(module), null);
        } catch (Throwable e) {
            return new UnresolvedModuleRef(null, e);
        }
    }

    public @Nonnull <T> UnresolvedDynamicObjectFieldRef<C, T> findFirstNonStaticField(@Nullable FieldFilter fieldFilter, boolean recursive) {
        try {
            Class<? super C> clazz = this.clazz;
            while (clazz != Object.class) {
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    if (!Modifier.isStatic(declaredField.getModifiers()) && (null == fieldFilter || fieldFilter.include(declaredField.getName(), declaredField))) {
                        return new UnresolvedDynamicObjectFieldRef<C, T>(new ResolvedDynamicObjectFieldRef<C, T>(this, declaredField), null);
                    }
                }
                if (recursive) {
                    clazz = clazz.getSuperclass();
                } else {
                    break;
                }
            }
            return new UnresolvedDynamicObjectFieldRef<C, T>(null, new NoSuchFieldError());
        } catch (Throwable e) {
            return new UnresolvedDynamicObjectFieldRef<C, T>(null, e);
        }
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

    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C, T> findFirstStaticField(@Nullable FieldFilter fieldFilter, boolean recursive) {
        try {
            Class<? super C> clazz = this.clazz;
            while (clazz != Object.class) {
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    if (Modifier.isStatic(declaredField.getModifiers()) && (null == fieldFilter || fieldFilter.include(declaredField.getName(), declaredField))) {
                        return new UnresolvedStaticObjectFieldRef<C, T>(new ResolvedStaticObjectFieldRef<C, T>(this, declaredField), null);
                    }
                }
                if (recursive) {
                    clazz = clazz.getSuperclass();
                } else {
                    break;
                }
            }
            return new UnresolvedStaticObjectFieldRef<C, T>(null, new NoSuchFieldError());
        } catch (Throwable e) {
            return new UnresolvedStaticObjectFieldRef<C, T>(null, e);
        }
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
    public @Nonnull UnresolvedStaticVoidMethodRef<C> staticMethod(@Nonnull String methodName, @Nonnull Class<?>... parameters) {
        ResolvedStaticVoidMethodRef<C> resolvedStaticMethodRef = null;
        Exception exception = null;
        try {
            // TODO: validate it is static
            resolvedStaticMethodRef = new ResolvedStaticVoidMethodRef<C>(this, getDeclaredMethod(methodName, parameters));
        } catch (NoSuchMethodException e) {
            exception = e;
        }
        return new UnresolvedStaticVoidMethodRef<C>(resolvedStaticMethodRef, exception);
    }

    // TODO: should parameters be nullable ?
    public @Nonnull <T> UnresolvedStaticTypedMethodRef<C, T> staticMethod(@Nonnull Class<T> returnType, @Nonnull String methodName, @Nonnull Class<?>... parameters) {
        ResolvedStaticTypedMethodRef<C, T> resolvedStaticMethodRef = null;
        Exception exception = null;
        try {
            // TODO: validate it is static
            resolvedStaticMethodRef = new ResolvedStaticTypedMethodRef<C, T>(this, getDeclaredMethod(methodName, parameters));
        } catch (NoSuchMethodException e) {
            exception = e;
        }
        return new UnresolvedStaticTypedMethodRef<C, T>(resolvedStaticMethodRef, exception);
    }

    public @Nonnull UnresolvedVoidDynamicMethodRef<C> method(@Nonnull String methodName, @Nonnull Class<?>... parameters) {
        ResolvedVoidDynamicMethodRef<C> resolvedVoidDynamicMethodRef = null;
        Exception exception = null;
        try {
            // TODO: validate it is static
            resolvedVoidDynamicMethodRef = new ResolvedVoidDynamicMethodRef<C>(this, getDeclaredMethod(methodName, parameters));
        } catch (NoSuchMethodException e) {
            exception = e;
        }
        return new UnresolvedVoidDynamicMethodRef<C>(resolvedVoidDynamicMethodRef, exception);
    }

    public @Nonnull <T> UnresolvedDynamicTypedMethodRef<C, T> method(@Nonnull Class<T> returnType, @Nonnull String methodName, @Nonnull Class<?>... parameters) {
        ResolvedDynamicTypedMethodRef<C, T> resolvedDynamicTypedMethodRef = null;
        Exception exception = null;
        try {
            // TODO: validate it is static
            resolvedDynamicTypedMethodRef = new ResolvedDynamicTypedMethodRef<C, T>(this, getDeclaredMethod(methodName, parameters));
        } catch (NoSuchMethodException e) {
            exception = e;
        }
        return new UnresolvedDynamicTypedMethodRef<C, T>(resolvedDynamicTypedMethodRef, exception);
    }

    // one param methods

    public <P1> @Nonnull UnresolvedVoidDynamicOneParamMethodRef<C, P1> method(@Nonnull String methodName, @Nonnull Class<P1> C1) {
        ResolvedVoidDynamicOneParamMethodRef<C, P1> resolvedVoidDynamicMethodRef = null;
        Exception exception = null;
        try {
            // TODO: validate it is static
            resolvedVoidDynamicMethodRef = new ResolvedVoidDynamicOneParamMethodRef<C, P1>(this, getDeclaredMethod(methodName, C1));
        } catch (NoSuchMethodException e) {
            exception = e;
        }
        return new UnresolvedVoidDynamicOneParamMethodRef<C, P1>(resolvedVoidDynamicMethodRef, exception);
    }

    // TODO: add "field" and "method" method which would return generic ref working with both static and non static members

    // TODO: add helper methods for getting constructors with arguments

    public @Nonnull UnresolvedZeroArgsClassConstructorRef<C> getConstructor() {
        try {
            Constructor<C> declaredConstructor = clazz.getDeclaredConstructor();
            if (Unsafe.setAccessible(declaredConstructor)) {
                return new UnresolvedZeroArgsClassConstructorRef<C>(
                        new ZeroArgsClassConstructorRef<C>(declaredConstructor),
                        null
                );
            } else {
                return new UnresolvedZeroArgsClassConstructorRef<C>(null, new UnresolvedRefException("Constructor " + clazz.getName() + "." + declaredConstructor.getName() + "() is not accessible"));
            }
        } catch (Throwable e) {
            return new UnresolvedZeroArgsClassConstructorRef<C>(null, e);
        }
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
    public void retransform() throws UnmodifiableClassException, ExecutionException, InterruptedException {
        Unsafe.getInstrumentationFuture().get().retransformClasses(clazz);
    }

    public void ensureClassInitialized() {
        Unsafe.getSunMiscUnsafe().ensureClassInitialized(clazz);
    }

    public ObjectRef<C> $(C object) {
        return new ObjectRef<C>(this, object);
    }

}

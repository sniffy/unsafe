package tools.unsafe.reflection.clazz;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.Unsafe;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.constructor.UnresolvedZeroArgsClassConstructorRef;
import tools.unsafe.reflection.constructor.ZeroArgsClassConstructorRef;
import tools.unsafe.reflection.field.*;
import tools.unsafe.reflection.field.resolved.ResolvedDynamicObjectFieldRef;
import tools.unsafe.reflection.field.resolved.ResolvedStaticObjectFieldRef;
import tools.unsafe.reflection.field.unresolved.UnresolvedDynamicObjectFieldRef;
import tools.unsafe.reflection.field.unresolved.UnresolvedStaticObjectFieldRef;
import tools.unsafe.reflection.method.*;
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

import static tools.unsafe.Unsafe.$;

// TODO: use declaredFields0 and declaredMethods0 to also cover super system methods

/**
 * Provides a set of convenient methods for working with classes via reflection
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
            //noinspection rawtypes
            UnresolvedNonStaticNonVoidMethodRef<Class, ?> getModuleMethodRef = classClassRef.getNonStaticMethod(moduleClass, "getModule");
            Object module = getModuleMethodRef.invoke(clazz);
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

    public @Nonnull Map<String, ResolvedDynamicObjectFieldRef<C,Object>> findNonStaticFields(@Nullable FieldFilter fieldFilter, boolean recursive) {
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

    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C,T> findFirstStaticField(@Nullable FieldFilter fieldFilter, boolean recursive) {
        try {
            Class<? super C> clazz = this.clazz;
            while (clazz != Object.class) {
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    if (Modifier.isStatic(declaredField.getModifiers()) && (null == fieldFilter || fieldFilter.include(declaredField.getName(), declaredField))) {
                        return new UnresolvedStaticObjectFieldRef<C,T>(new ResolvedStaticObjectFieldRef<C,T>(this, declaredField), null);
                    }
                }
                if (recursive) {
                    clazz = clazz.getSuperclass();
                } else {
                    break;
                }
            }
            return new UnresolvedStaticObjectFieldRef<C,T>(null, new NoSuchFieldError());
        } catch (Throwable e) {
            return new UnresolvedStaticObjectFieldRef<C,T>(null, e);
        }
    }

    public @Nonnull Map<String, ResolvedStaticObjectFieldRef<C,Object>> findStaticFields(@Nullable FieldFilter fieldFilter, boolean recursive) {
        Map<String, ResolvedStaticObjectFieldRef<C,Object>> fields = new HashMap<String, ResolvedStaticObjectFieldRef<C,Object>>();
        Class<? super C> clazz = this.clazz;
        while (clazz != Object.class) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                // TODO: filter out synthetic members here and everywhere
                if (Modifier.isStatic(field.getModifiers()) && (null == fieldFilter || fieldFilter.include(field.getName(), field))) {
                    fields.put(field.getName(), new ResolvedStaticObjectFieldRef<C,Object>(this,field));
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
        for (ResolvedDynamicObjectFieldRef<C,Object> fieldRef : findNonStaticFields(null, true).values()) {
            fieldRef.copy(from, to);
        }

    }

    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C,T> getStaticField(@Nonnull String fieldName) {
        return findFirstStaticField(FieldFilters.byName(fieldName), true);
    }

    public @Nonnull <T> UnresolvedDynamicObjectFieldRef<C,T> getNonStaticField(@Nonnull String fieldName) {
        return findFirstNonStaticField(FieldFilters.byName(fieldName), true);
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


    public @Nonnull Map<MethodKey, NonStaticMethodRef<C>> getNonStaticMethods(@Nullable Class<? super C> upperBound, @Nullable MethodFilter methodFilter) {
        Map<MethodKey, NonStaticMethodRef<C>> methodRefs = new HashMap<MethodKey, NonStaticMethodRef<C>>();
        Class<? super C> clazz = this.clazz;
        while (clazz != (null == upperBound ? Object.class : upperBound)) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    MethodKey methodKey = new MethodKey(method);
                    if (null == methodFilter || methodFilter.include(methodKey, method)) {
                        methodRefs.put(methodKey, new NonStaticMethodRef<C>(method));
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

    // new method factories



    @SuppressWarnings("Convert2Diamond")
    public @Nonnull UnresolvedNonStaticMethodRef<C> getNonStaticMethod(@Nonnull String methodName, @Nonnull Class<?>... parameterTypes) {
        try {
            Method declaredMethod = getDeclaredMethod(methodName, parameterTypes);
            if (Unsafe.setAccessible(declaredMethod)) {
                return new UnresolvedNonStaticMethodRef<C>(new NonStaticMethodRef<C>(declaredMethod), null);
            } else {
                return new UnresolvedNonStaticMethodRef<C>(null, new UnresolvedRefException("Method " + clazz.getName() + "." + methodName + "() is not accessible"));
            }
        } catch (Throwable e) {
            return new UnresolvedNonStaticMethodRef<C>(null, e);
        }
    }

    @SuppressWarnings("Convert2Diamond")
    public @Nonnull <T> UnresolvedNonStaticNonVoidMethodRef<C,T> getNonStaticMethod(
            @SuppressWarnings("unused") @Nullable Class<T> returnType,
            @Nonnull String methodName, @Nonnull Class<?>... parameterTypes) {
        try {
            Method declaredMethod = getDeclaredMethod(methodName, parameterTypes);
            if (Unsafe.setAccessible(declaredMethod)) {
                return new UnresolvedNonStaticNonVoidMethodRef<C,T>(new NonStaticNonVoidMethodRef<C,T>(declaredMethod), null);
            } else {
                return new UnresolvedNonStaticNonVoidMethodRef<C,T>(null, new UnresolvedRefException("Method " + clazz.getName() + "." + methodName + "() is not accessible"));
            }
        } catch (Throwable e) {
            return new UnresolvedNonStaticNonVoidMethodRef<C,T>(null, e);
        }
    }
    public @Nonnull UnresolvedStaticMethodRef getStaticMethod(@Nonnull String methodName, @Nonnull Class<?>... parameterTypes) {
        try {
            Method declaredMethod = getDeclaredMethod(methodName, parameterTypes);
            if (Unsafe.setAccessible(declaredMethod)) {
                return new UnresolvedStaticMethodRef(new StaticMethodRef(declaredMethod), null);
            } else {
                return new UnresolvedStaticMethodRef(null, new UnresolvedRefException("Method " + clazz.getName() + "." + methodName + "() is not accessible"));
            }
        } catch (Throwable e) {
            return new UnresolvedStaticMethodRef(null, e);
        }
    }

    public @Nonnull <T> UnresolvedStaticNonVoidMethodRef<T> getStaticMethod(
            @SuppressWarnings("unused") @Nullable Class<T> returnType,
            @Nonnull String methodName, @Nonnull Class<?>... parameterTypes) {
        try {
            Method declaredMethod = getDeclaredMethod(methodName, parameterTypes);
            if (Unsafe.setAccessible(declaredMethod)) {
                return new UnresolvedStaticNonVoidMethodRef<T>(new StaticNonVoidMethodRef<T>(declaredMethod), null);
            } else {
                return new UnresolvedStaticNonVoidMethodRef<T>(null, new UnresolvedRefException("Method " + clazz.getName() + "." + methodName + "() is not accessible"));
            }
        } catch (Throwable e) {
            return new UnresolvedStaticNonVoidMethodRef<T>(null, e);
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    public void retransform() throws UnmodifiableClassException, ExecutionException, InterruptedException {
        Unsafe.getInstrumentationFuture().get().retransformClasses(clazz);
    }

    public ObjectRef<C> $(C object) {
        return new ObjectRef<C>(this, object);
    }

}

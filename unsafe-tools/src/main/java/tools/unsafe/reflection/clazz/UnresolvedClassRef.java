package tools.unsafe.reflection.clazz;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.constructor.UnresolvedZeroArgsClassConstructorRef;
import tools.unsafe.reflection.field.*;
import tools.unsafe.reflection.field.booleans.unresolved.UnresolvedDynamicBooleanFieldRef;
import tools.unsafe.reflection.field.booleans.unresolved.UnresolvedStaticBooleanFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedDynamicObjectFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedStaticObjectFieldRef;
import tools.unsafe.reflection.field.objects.unresolved.UnresolvedDynamicObjectFieldRef;
import tools.unsafe.reflection.field.objects.unresolved.UnresolvedStaticObjectFieldRef;
import tools.unsafe.reflection.method.resolved.ResolvedDynamicMethodRef;
import tools.unsafe.reflection.method.resolved.ResolvedDynamicTypedMethodRef;
import tools.unsafe.reflection.method.resolved.ResolvedStaticMethodRef;
import tools.unsafe.reflection.method.unresolved.*;
import tools.unsafe.reflection.module.UnresolvedModuleRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Unresolved reference to class
 * @see ClassRef
 * @see UnresolvedRef
 * @param <C>
 */
public class UnresolvedClassRef<C> extends UnresolvedRef<ClassRef<C>> {

    public UnresolvedClassRef(@Nullable ClassRef<C> ref, @Nullable Throwable throwable) {
        super(ref, throwable);
    }

    public @Nonnull UnresolvedModuleRef tryGetModuleRef() {
        try {
            return getModuleRef();
        } catch (UnresolvedRefException e) {
            return new UnresolvedModuleRef(null, e);
        }
    }

    public @Nonnull UnresolvedModuleRef getModuleRef() throws UnresolvedRefException {
        return resolve().getModuleRef();
    }

    public @Nonnull UnresolvedZeroArgsClassConstructorRef<C> tryGetConstructor() {
        try {
            return getConstructor();
        } catch (UnresolvedRefException e) {
            return new UnresolvedZeroArgsClassConstructorRef<C>(null, e);
        }
    }

    public @Nonnull UnresolvedZeroArgsClassConstructorRef<C> getConstructor() throws UnresolvedRefException {
        return resolve().getConstructor();
    }

    // new fields methods start

    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C,T> staticField(@Nonnull String fieldName) {
        try {
            return new UnresolvedStaticObjectFieldRef<C,T>(resolve().<T>staticField(fieldName).resolve(),null);
        } catch (UnresolvedRefException e) {
            return new UnresolvedStaticObjectFieldRef<C,T>(null,e);
        }
    }

    public @Nonnull <T> UnresolvedDynamicObjectFieldRef<C,T> field(@Nonnull String fieldName) {
        try {
            return new UnresolvedDynamicObjectFieldRef<C,T>(resolve().<T>field(fieldName).resolve(),null);
        } catch (UnresolvedRefException e) {
            return new UnresolvedDynamicObjectFieldRef<C,T>(null,e);
        }
    }

    public @Nonnull UnresolvedStaticBooleanFieldRef<C> staticBooleanField(@Nonnull String fieldName) {
        try {
            return new UnresolvedStaticBooleanFieldRef<C>(resolve().staticBooleanField(fieldName).resolve(),null);
        } catch (UnresolvedRefException e) {
            return new UnresolvedStaticBooleanFieldRef<C>(null,e);
        }
    }

    public @Nonnull UnresolvedDynamicBooleanFieldRef<C> booleanField(@Nonnull String fieldName) {
        try {
            return new UnresolvedDynamicBooleanFieldRef<C>(resolve().booleanField(fieldName).resolve(),null);
        } catch (UnresolvedRefException e) {
            return new UnresolvedDynamicBooleanFieldRef<C>(null,e);
        }
    }

    // new fields methods end


    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C,T> tryGetStaticField(@Nonnull String fieldName) {
        try {
            return getStaticField(fieldName);
        } catch (Throwable e) {
            return new UnresolvedStaticObjectFieldRef<C,T>(null, e);
        }
    }

    @Nonnull
    public <T> UnresolvedStaticObjectFieldRef<C,T> getStaticField(@Nonnull String fieldName) throws UnresolvedRefException {
        return resolve().staticField(fieldName);
    }

    @Nonnull
    public <T> UnresolvedDynamicObjectFieldRef<C, T> tryGetNonStaticField(@Nonnull String fieldName) {
        try {
            return getNonStaticField(fieldName);
        } catch (Throwable e) {
            return new UnresolvedDynamicObjectFieldRef<C, T>(null, e);
        }
    }

    public @Nonnull <T> UnresolvedDynamicObjectFieldRef<C, T> getNonStaticField(@Nonnull String fieldName) throws UnresolvedRefException {
        return resolve().field(fieldName);
    }

    public @Nonnull <T> UnresolvedDynamicObjectFieldRef<C, T> findFirstNonStaticField(@Nullable FieldFilter fieldFilter, boolean recursive) throws UnresolvedRefException {
        return resolve().findFirstNonStaticField(fieldFilter, recursive);
    }

    public @Nonnull Map<String, ResolvedDynamicObjectFieldRef<C, Object>> findNonStaticFields(@Nullable FieldFilter fieldFilter, boolean recursive) throws UnresolvedRefException {
        return resolve().findNonStaticFields(fieldFilter, recursive);
    }

    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C,T> tryFindFirstStaticField(@Nullable FieldFilter fieldFilter, boolean recursive) {
        try {
            return findFirstStaticField(fieldFilter, recursive);
        } catch (Throwable e) {
            return new UnresolvedStaticObjectFieldRef<C,T>(null, e);
        }
    }

    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C,T> findFirstStaticField(@Nullable FieldFilter fieldFilter, boolean recursive) throws UnresolvedRefException {
        return resolve().findFirstStaticField(fieldFilter, recursive);
    }

    public @Nonnull Map<String, ResolvedStaticObjectFieldRef<C,Object>> findStaticFields(@Nullable FieldFilter fieldFilter, boolean recursive) throws UnresolvedRefException {
        return resolve().findStaticFields(fieldFilter, recursive);
    }

    // methods

    public @Nonnull UnresolvedDynamicMethodRef<C> method(@Nonnull String methodName, @Nonnull Class<?>... parameterTypes) {
        try {
            return resolve().method(methodName, parameterTypes);
        } catch (UnresolvedRefException e) {
            return new UnresolvedDynamicMethodRef<C>(null, e);
        }
    }

    public @Nonnull UnresolvedStaticMethodRef<C> staticMethod(@Nonnull String methodName, @Nonnull Class<?>... parameterTypes) {
        try {
            return resolve().staticMethod(methodName, parameterTypes);
        } catch (UnresolvedRefException e) {
            return new UnresolvedStaticMethodRef<C>(null, e);
        }
    }

    public @Nonnull <T> UnresolvedStaticTypedMethodRef<C,T> staticMethod(@Nonnull Class<T> returnType, @Nonnull String methodName, @Nonnull Class<?>... parameterTypes) {
        try {
            return resolve().staticMethod(returnType, methodName, parameterTypes);
        } catch (UnresolvedRefException e) {
            return new UnresolvedStaticTypedMethodRef<C,T>(null, e);
        }
    }

    public @Nonnull <T> UnresolvedDynamicTypedMethodRef<C,T> method(@Nonnull Class<T> returnType, @Nonnull String methodName, @Nonnull Class<?>... parameterTypes) {
        try {
            return resolve().method(returnType, methodName, parameterTypes);
        } catch (UnresolvedRefException e) {
            return new UnresolvedDynamicTypedMethodRef<C, T>(null, e);
        }
    }

    @SuppressWarnings("unused")
    public void retransform() throws UnmodifiableClassException, UnresolvedRefException, ExecutionException, InterruptedException {
        resolve().retransform();
    }

}
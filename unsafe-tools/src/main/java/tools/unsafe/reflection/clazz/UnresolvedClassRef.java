package tools.unsafe.reflection.clazz;

import tools.unsafe.reflection.UnresolvedRef;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.constructor.UnresolvedZeroArgsClassConstructorRef;
import tools.unsafe.reflection.field.*;
import tools.unsafe.reflection.method.UnresolvedNonStaticMethodRef;
import tools.unsafe.reflection.method.UnresolvedNonStaticNonVoidMethodRef;
import tools.unsafe.reflection.method.UnresolvedStaticMethodRef;
import tools.unsafe.reflection.method.UnresolvedStaticNonVoidMethodRef;
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

    public @Nonnull <T> UnresolvedStaticObjectFieldRef<C,T> tryGetStaticField(@Nonnull String fieldName) {
        try {
            return getStaticField(fieldName);
        } catch (Throwable e) {
            return new UnresolvedStaticObjectFieldRef<C,T>(null, e);
        }
    }

    @Nonnull
    public <T> UnresolvedStaticObjectFieldRef<C,T> getStaticField(@Nonnull String fieldName) throws UnresolvedRefException {
        return resolve().getStaticField(fieldName);
    }

    @Nonnull
    public <T> UnresolvedNonStaticObjectFieldRef<C, T> tryGetNonStaticField(@Nonnull String fieldName) {
        try {
            return getNonStaticField(fieldName);
        } catch (Throwable e) {
            return new UnresolvedNonStaticObjectFieldRef<C, T>(null, e);
        }
    }

    public @Nonnull <T> UnresolvedNonStaticObjectFieldRef<C, T> getNonStaticField(@Nonnull String fieldName) throws UnresolvedRefException {
        return resolve().getNonStaticField(fieldName);
    }

    public @Nonnull <T> UnresolvedNonStaticObjectFieldRef<C, T> findFirstNonStaticField(@Nullable FieldFilter fieldFilter, boolean recursive) throws UnresolvedRefException {
        return resolve().findFirstNonStaticField(fieldFilter, recursive);
    }

    public @Nonnull Map<String, ResolvedNonStaticObjectFieldRef<C, Object>> findNonStaticFields(@Nullable FieldFilter fieldFilter, boolean recursive) throws UnresolvedRefException {
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

    public @Nonnull UnresolvedNonStaticMethodRef<C> method(@Nonnull String methodName, @Nonnull Class<?>... parameterTypes) {
        return getNonStaticMethod(methodName, parameterTypes);
    }

    public @Nonnull UnresolvedNonStaticMethodRef<C> getNonStaticMethod(@Nonnull String methodName, @Nonnull Class<?>... parameterTypes) {
        try {
            return resolve().getNonStaticMethod(methodName, parameterTypes);
        } catch (UnresolvedRefException e) {
            return new UnresolvedNonStaticMethodRef<C>(null, e);
        }
    }

    public @Nonnull <T> UnresolvedNonStaticNonVoidMethodRef<C, T> getNonStaticMethod(@Nullable Class<T> returnType, @Nonnull String methodName, @Nonnull Class<?>... parameterTypes) throws UnresolvedRefException {
        return resolve().getNonStaticMethod(returnType, methodName, parameterTypes);
    }

    public @Nonnull UnresolvedStaticMethodRef getStaticMethod(@Nonnull String methodName, @Nonnull Class<?>... parameterTypes) throws UnresolvedRefException {
        return resolve().getStaticMethod(methodName, parameterTypes);
    }

    public @Nonnull <T> UnresolvedStaticNonVoidMethodRef<T> getStaticMethod(@Nullable Class<T> returnType, @Nonnull String methodName, @Nonnull Class<?>... parameterTypes) throws UnresolvedRefException {
        return resolve().getStaticMethod(returnType, methodName, parameterTypes);
    }

    @SuppressWarnings("unused")
    public void retransform() throws UnmodifiableClassException, UnresolvedRefException, ExecutionException, InterruptedException {
        resolve().retransform();
    }

}
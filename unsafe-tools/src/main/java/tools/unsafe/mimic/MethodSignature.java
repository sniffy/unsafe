package tools.unsafe.mimic;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.method.typedresult.resolved.ResolvedInstanceTypedMethodRef;
import tools.unsafe.reflection.method.typedresult.unresolved.UnresolvedDynamicTypedMethodRef;
import tools.unsafe.reflection.method.typedresult.unresolved.UnresolvedStaticTypedMethodRef;
import tools.unsafe.reflection.object.ObjectRef;

import java.lang.reflect.InvocationTargetException;

public class MethodSignature<R> {

    private final Class<R> returnType;
    private final Class<?>[] parameterTypes; // TODO: generate classes with types parameters

    public MethodSignature(Class<R> returnType, Class<?>[] parameterTypes) {
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }

    public NamedMethodSignature<R> named(String methodName) {
        return new NamedMethodSignature<R>(returnType, methodName, parameterTypes);
    }

    public R invokeOn(Object instance, String methodName, Object... parameters) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        return bindTo(ObjectRef.of(instance), methodName).invoke(parameters);
    }

    public <C> R staticInvokeOn(Class<C> clazz, String methodName, Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        return staticBindTo(ClassRef.of(clazz), methodName).invoke(parameters);
    }

    public <C> UnresolvedStaticTypedMethodRef<C, R> staticBindTo(ClassRef<C> classRef, String methodName) {
        return classRef.staticMethod(returnType, methodName, parameterTypes);
    }

    public <C> UnresolvedDynamicTypedMethodRef<C, R> bindTo(ClassRef<C> classRef, String methodName) {
        return classRef.method(returnType, methodName, parameterTypes);
    }

    public <C> ResolvedInstanceTypedMethodRef<C, R> bindTo(ObjectRef<C> objectRef, String methodName) throws UnresolvedRefException {
        return objectRef.method(returnType, methodName, parameterTypes);
    }

}

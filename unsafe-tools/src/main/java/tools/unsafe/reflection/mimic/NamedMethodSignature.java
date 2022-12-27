package tools.unsafe.reflection.mimic;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.method.typedresult.resolved.ResolvedInstanceTypedMethodRef;
import tools.unsafe.reflection.method.typedresult.unresolved.UnresolvedDynamicTypedMethodRef;
import tools.unsafe.reflection.method.typedresult.unresolved.UnresolvedStaticTypedMethodRef;
import tools.unsafe.reflection.object.ObjectRef;

import java.lang.reflect.InvocationTargetException;

public class NamedMethodSignature<R> {

    private final Class<R> returnType;
    private final String methodName;
    private final Class<?>[] parameterTypes;

    public NamedMethodSignature(Class<R> returnType, String methodName, Class<?>[] parameterTypes) {
        this.returnType = returnType;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }

    public R invokeOn(Object instance, Object... parameters) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        return bindTo(ObjectRef.of(instance)).invoke(parameters);
    }

    public <C> R staticInvokeOn(Class<C> clazz, Object... parameters) throws UnsafeInvocationException, InvocationTargetException {
        return staticBindTo(ClassRef.of(clazz)).invoke(parameters);
    }

    public <C> UnresolvedStaticTypedMethodRef<C, R> staticBindTo(ClassRef<C> classRef) {
        return classRef.staticMethod(returnType, methodName, parameterTypes);
    }

    public <C> UnresolvedDynamicTypedMethodRef<C, R> bindTo(ClassRef<C> classRef) {
        return classRef.method(returnType, methodName, parameterTypes);
    }

    public <C> ResolvedInstanceTypedMethodRef<C, R> bindTo(ObjectRef<C> objectRef) throws UnresolvedRefException {
        return objectRef.method(returnType, methodName, parameterTypes);
    }

}

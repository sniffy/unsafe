package tools.unsafe.ng;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodRef extends AbstractMethodRef {

    public MethodRef(Method method) {
        super(method);
    }

    public Object invoke(Object obj, Object... args) throws Throwable {
        try {
            return method.invoke(obj, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private void validateInstanceClass(Class<?> clazz) {
        if (!method.getDeclaringClass().isAssignableFrom(clazz)) {
            throw new WrongMethodSignatureException();
        }
    }

    private void validateVoidSignature(Class<?> clazz, Class<?>... parameterTypes) {
        validateParameterTypes(parameterTypes);
        validateInstanceClass(clazz);
    }

    private void validateNonVoidSignature(Class<?> returnTypeClass, Class<?> clazz, Class<?>... parameterTypes) {
        validateParameterTypes(parameterTypes);
        validateInstanceClass(clazz);
        validateReturnClass(returnTypeClass);
    }

    // cast methods

    public <C> MethodRef.VoidNoParams<C> asVoidMethod(Class<C> clazz) {
        validateVoidSignature(clazz);
        return new MethodRef.VoidNoParams<C>();
    }

    public <C, P1> MethodRef.VoidOneParam<C, P1> asVoidMethod(Class<C> clazz, Class<P1> p1) {
        validateVoidSignature(clazz, p1);
        return new MethodRef.VoidOneParam<C, P1>();
    }

    public <R, C> MethodRef.NonVoidNoParams<R, C> asNonVoidMethod(Class<R> r, Class<C> c) {
        validateNonVoidSignature(r, c);
        return new MethodRef.NonVoidNoParams<R, C>();
    }

    public <R, C, P1> MethodRef.NonVoidOneParam<R, C, P1> asNonVoidMethod(Class<R> r, Class<C> c, Class<P1> p1) {
        validateNonVoidSignature(r, c, p1);
        return new MethodRef.NonVoidOneParam<R, C, P1>();
    }

    public class VoidNoParams<C> {
        public void invoke(C instance) throws Throwable {
            MethodRef.this.invoke(instance);
        }
    }

    public class VoidOneParam<C, P1> {
        public void invoke(C instance, P1 p1) throws Throwable {
            MethodRef.this.invoke(instance, p1);
        }
    }

    public class NonVoidNoParams<R, C> {
        @SuppressWarnings("unchecked")
        public R invoke(C instance) throws Throwable {
            return (R) MethodRef.this.invoke(instance);
        }
    }

    public class NonVoidOneParam<R, C, P1> {
        @SuppressWarnings("unchecked")
        public R invoke(C instance, P1 p1) throws Throwable {
            return (R) MethodRef.this.invoke(instance, p1);
        }
    }

}

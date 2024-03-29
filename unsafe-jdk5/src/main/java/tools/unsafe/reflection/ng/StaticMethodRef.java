package tools.unsafe.reflection.ng;

import tools.unsafe.Reflections;

import java.lang.reflect.Method;

public class StaticMethodRef extends AbstractMethodRef {

    public StaticMethodRef(Method method) {
        super(method);
    }

    // TODO: change to RuntimeException probably ?
    // or may be not if filosophy is to keep MethodHandles =-like API
    public Object invoke(Object... args) throws Throwable {
        return Reflections.getMethodInvoker(method).invoke(null, args);
//        try {
//            //return method.invoke(null, args);
//            return new MethodHandleMethodInvoker(method).invoke(null, args);
//        } catch (/*InvocationTargetException*/ Throwable e) {
//            //throw e.getCause();
//            throw e;
//        }
    }

    private void validateVoidSignature(Class<?>... parameterTypes) {
        validateParameterTypes(parameterTypes);
    }

    private void validateNonVoidSignature(Class<?> returnTypeClass, Class<?>... parameterTypes) {
        validateParameterTypes(parameterTypes);
        validateReturnClass(returnTypeClass);
    }

    // cast methods

    public VoidNoParams asVoidMethod() {
        validateVoidSignature();
        return new VoidNoParams();
    }

    public <P1> VoidOneParam<P1> asVoidMethod(Class<P1> p1) {
        validateVoidSignature(p1);
        return new VoidOneParam<P1>();
    }

    public <R> NonVoidNoParams<R> asNonVoidMethod(Class<R> r) {
        validateNonVoidSignature(r);
        return new NonVoidNoParams<R>();
    }

    public <R, P1> NonVoidOneParam<R, P1> asNonVoidMethod(Class<R> r, Class<P1> p1) {
        validateNonVoidSignature(r, p1);
        return new NonVoidOneParam<R, P1>();
    }

    public class VoidNoParams {
        public void invoke() throws Throwable {
            StaticMethodRef.this.invoke();
        }
    }

    public class VoidOneParam<P1> {
        public void invoke(P1 p1) throws Throwable {
            StaticMethodRef.this.invoke(p1);
        }
    }

    public class NonVoidNoParams<R> {
        @SuppressWarnings("unchecked")
        public R invoke() throws Throwable {
            return (R) StaticMethodRef.this.invoke();
        }
    }

    public class NonVoidOneParam<R, P1> {
        @SuppressWarnings("unchecked")
        public R invoke(P1 p1) throws Throwable {
            return (R) StaticMethodRef.this.invoke(p1);
        }
    }

}

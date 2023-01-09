package tools.unsafe.ng;

import tools.unsafe.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorRef<C> extends ExecutableRef {

    private final Constructor<C> constructor;

    public ConstructorRef(Constructor<C> constructor) {
        try {
            Unsafe.setAccessible(constructor); // TODO: make it lazy
        } catch (Exception e) {
            throw Unsafe.throwException(e);
        }
        this.constructor = constructor;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return constructor.getParameterTypes();
    }

    public Object invoke(Object... args) throws Throwable {
        try {
            return constructor.newInstance(args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    // cast methods

    public NoParams noParameters() {
        validateParameterTypes();
        return new NoParams();
    }

    public <P1> OneParam<P1> parameterTypes(Class<P1> p1) {
        validateParameterTypes(p1);
        return new OneParam<P1>();
    }

    public class NoParams {
        public void invoke() throws Throwable {
            ConstructorRef.this.invoke();
        }
    }

    public class OneParam<P1> {
        public void invoke(P1 p1) throws Throwable {
            ConstructorRef.this.invoke(p1);
        }
    }

}

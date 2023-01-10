package tools.unsafe.ng;

import tools.unsafe.Unsafe;
import tools.unsafe.reflection.UnsafeException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

// TODO: create a way to create wrappers which throw certain Exceptions
public class Methods {

    private final static Method foo;
    private final static MethodHandle MH;

    static {
        Method a;
        try {
            a = Methods.class.getDeclaredMethod("foo", String.class);
        } catch (Throwable e) {
            a = null;
        }
        foo = a;

        MethodHandle b;
        try {
            b = MethodHandles.lookup().findStatic(Methods.class, "foo", MethodType.methodType(int.class, String.class));
        } catch (Throwable e) {
            b = null;
        }
        MH = b;
    }


    /*public static int foo(String a) {
        System.out.println(a);
        return a.length();
    }

    public static void main(String... args) throws Throwable {

        MH.invoke(123, 4564564, "dfgdfgdf", new Object()); // IntelliJ fails to highlight issues here, but compilation (with java 8 will actually fail - TODO: investigate
        foo.invoke(null, 34); // IntelliJ fails to highlight issues here

        MethodHandle mh = MethodHandles.lookup().
                findStatic(Methods.class, "foo", MethodType.methodType(int.class, String.class));

        //int r = (int) mh.invoke("fg"); // this works only on new Javas
        Integer r = (Integer) mh.invoke("fg");

        MethodRef.NonVoidOneParam<Integer, Methods, String> method =
                Methods.methodRef(Methods.class.getDeclaredMethod("foo", String.class)).asNonVoidMethod(
                        Integer.class,
                        Methods.class,
                        String.class
                );
        Integer res = method.invoke(new Methods(), "fdgsdfgs");
    }*/

    // TODO: return Lookup using multi-release jars
    // TODO: move to separate Java 7+ only class
    public static MethodHandles.Lookup privateLookupIn(Class<?> context) {
        //return null;
        try {
            @SuppressWarnings("BlockedPrivateApi") Field declaredField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            Unsafe.setAccessible(declaredField);
            MethodHandles.Lookup implLookup = (MethodHandles.Lookup) declaredField.get(null);
            return implLookup.in(context);
        } catch (Exception e) {
            throw Unsafe.throwException(e);
        }
    }

    public static AccessibleObject accessibleObject(AccessibleObject accessibleObject) {
        makeAccessible(accessibleObject);
        return accessibleObject;
    }

    // TODO: move to fields class
    public static Field accessibleField(Field field) {
        makeAccessible(field);
        return field;
    }

    public static Method accessibleMethod(Method method) {
        makeAccessible(method);
        return method;
    }

    // TODO: move to constructors class
    @SuppressWarnings("rawtypes")
    public static Constructor accessibleConstructor(Constructor cons) {
        makeAccessible(cons);
        return cons;
    }

    public static void makeAccessible(AccessibleObject accessibleObject) {
        try {
            Unsafe.setAccessible(accessibleObject);
        } catch (UnsafeException e) {
            throw Unsafe.throwException(e);
        }
    }

    public static MethodRef methodRef(Method method) {
        return new MethodRef(method);
    }

    public static MethodRef methodRef(Class<?> clazz, Method method) {
        return new MethodRef(method);
    }

    public static StaticMethodRef staticMethodRef(Callable<Method> methodCallable) {
        // TODO: validate static flag
        return new StaticMethodRef(getMethod(methodCallable));
    }

    private static Method getMethod(Callable<Method> methodCallable) {
        try {
            return methodCallable.call();
        } catch (Exception e) {
            throw Unsafe.throwException(e);
        }
    }

    public static StaticMethodRef staticMethodRef(Method method) {
        // TODO: validate static flag
        return new StaticMethodRef(method);
    }

    public static StaticMethodRef staticMethodRef(Class<?> clazz, Method method) {
        return new StaticMethodRef(method);
    }

    public static <C> ConstructorRef<C> constructorRef(Constructor<C> constructor) {
        return new ConstructorRef<C>(constructor);
    }

}



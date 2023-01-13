package tools.unsafe.spi;

import java.lang.reflect.Method;

public class Java9Providers {

    private Java9Providers() {

    }

    public static void registerProviders() {
    }

    public static void test() throws Exception {
        BiFunction<String, Class<?>[], Method> stringMethodBiFunction = Java9Providers.class::getDeclaredMethod;
    }

    public static void test2() throws Exception {
        Java9Providers.class::getDeclaredMethod ("register");
    }

    //@FunctionalInterface
    public static interface BiFunction<T, U, R> {

        /**
         * Applies this function to the given arguments.
         *
         * @param t the first function argument
         * @param u the second function argument
         * @return the function result
         */
        R apply(T t, U u) throws NoSuchMethodException, SecurityException;

    }

}

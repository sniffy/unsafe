package io.sniffy.unsafe;

public class UnsafeToolsSPIs {

    private static volatile ConstructorInvokerFactory constructorInvokerFactory = new UnsupportedConstructorInvokerFactory();

    public static ConstructorInvokerFactory getConstructorInvokerFactory() {
        return constructorInvokerFactory;
    }

    public static void setConstructorInvokerFactory(ConstructorInvokerFactory constructorInvokerFactory) {
        UnsafeToolsSPIs.constructorInvokerFactory = constructorInvokerFactory;
    }

}

package io.sniffy.unsafe;

public class UnsafeToolsJDK7SPIProvider {

    static {
        UnsafeToolsSPIs.setConstructorInvokerFactory(new MethodHandleConstructorInvokerFactoryImpl());
    }

}

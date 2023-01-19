package tools.unsafe.spi.invoke;

import java.lang.reflect.Method;

// TODO: implement via CodeGeneration
public interface MethodInvokerServiceProvider {

    MethodInvoker createMethodInvoker(Method method);

}

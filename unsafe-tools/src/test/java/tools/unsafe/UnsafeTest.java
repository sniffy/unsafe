package tools.unsafe;

import sun.security.jca.ProviderList;
import sun.security.jca.Providers;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import org.junit.jupiter.api.Test;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.constructor.ConstructorMethodHandleBuilder;
import tools.unsafe.reflection.field.objects.resolved.ResolvedStaticObjectFieldRef;

import javax.net.ssl.SSLContext;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.unsafe.fluent.Fluent.$;

class UnsafeTest {

    private static AtomicInteger counter = new AtomicInteger();

    public UnsafeTest() {
        counter.incrementAndGet();
    }

    @Test
    public void testInvokeConstructor() throws Throwable {
        UnsafeTest ut = new UnsafeTest();
        counter.set(0);
        MethodHandle methodHandle = ConstructorMethodHandleBuilder.constructorMethodHandle(UnsafeTest.class);
        methodHandle.invoke(ut);
        methodHandle.invoke(ut);
        methodHandle.invoke(ut);
        assertEquals(3, counter.get());
    }

    @Test
    public void testObjectRef() throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        Object object = new Object();
        assertEquals(object.hashCode(), $(object).invoke(Integer.TYPE, "hashCode", new Class<?>[0], new Object[0]));
    }

    @Test
    public void testProviderList() throws Exception {
        SSLContext.getDefault();

        long start = System.currentTimeMillis();

        Future<Instrumentation> instrumentationFuture = Unsafe.getInstrumentationFuture();

        Instrumentation instrumentation = instrumentationFuture.get();

        long end = System.currentTimeMillis();

        System.out.println("Instrumentation obtained in " + (end - start) + " milliseconds");

        assert null != instrumentation;

        ClassRef<Object> classRef = $("sun.security.jca.Providers").resolve();
        classRef.getModuleRef().tryAddOpens("sun.security.jca");

        ResolvedStaticObjectFieldRef<Object,ThreadLocal<ProviderList>> threadLists = classRef.<ThreadLocal<ProviderList>>staticField("threadLists").resolve();
        ResolvedStaticObjectFieldRef<Object,Integer> threadListsUsed = classRef.<Integer>staticField("threadListsUsed").resolve();

        final ProviderList IT = ProviderList.newList();

        threadListsUsed.set(1);
        threadLists.set(new ThreadLocal<ProviderList>() {

            @Override
            protected ProviderList initialValue() {
                System.out.println("initialValue()");
                return IT;
            }

            @Override
            public ProviderList get() {
                System.out.println("get()");
                return IT;
            }

            @Override
            public void set(ProviderList value) {
                System.out.println("set(" + value + ")");
            }

            @Override
            public void remove() {
                System.out.println("remove");
            }
        });

        $(Providers.class).retransform();

        ProviderList providerList = Providers.getProviderList();

        System.out.println(providerList);

        assertEquals(0, providerList.size());
    }

}
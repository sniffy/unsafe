package tools.unsafe;

import sun.security.jca.ProviderList;
import sun.security.jca.Providers;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import org.junit.jupiter.api.Test;
import tools.unsafe.reflection.clazz.UnresolvedClassRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedStaticObjectFieldRef;

import javax.net.ssl.SSLContext;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.unsafe.Unsafe.$;

class UnsafeTest {

    @Test
    public void testObjectRef() throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        Object object = new Object();
        assertEquals(object.hashCode(), Unsafe.$(object).invoke(Integer.TYPE, "hashCode", new Class<?>[0], new Object[0]));
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

        UnresolvedClassRef<Object> classRef = Unsafe.$("sun.security.jca.Providers");
        classRef.getModuleRef().tryAddOpens("sun.security.jca");

        ResolvedStaticObjectFieldRef<Object,ThreadLocal<ProviderList>> threadLists = classRef.<ThreadLocal<ProviderList>>getStaticField("threadLists").resolve();
        ResolvedStaticObjectFieldRef<Object,Integer> threadListsUsed = classRef.<Integer>getStaticField("threadListsUsed").resolve();

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

        Unsafe.$(Providers.class).retransform();

        ProviderList providerList = Providers.getProviderList();

        System.out.println(providerList);

        assertEquals(0, providerList.size());
    }

}
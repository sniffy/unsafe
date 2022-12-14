package tools.unsafe;

import org.junit.Test;
import sun.security.jca.ProviderList;
import sun.security.jca.Providers;
import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.constructor.UnresolvedZeroArgsClassConstructorRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedStaticObjectFieldRef;
import tools.unsafe.vm.UnsafeVirtualMachine;

import javax.net.ssl.SSLContext;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static tools.unsafe.fluent.Fluent.$;
import static tools.unsafe.vm.UnsafeVirtualMachine.getJavaVersion;
import static tools.unsafe.vm.VirtualMachineFamily.J9;

public class UnsafeTest {

    private static AtomicInteger counter = new AtomicInteger();

    public UnsafeTest() {
        counter.incrementAndGet();
    }

    @Test
    public void testGetUnsafe() throws Exception {
        // assertNothingWrittenToSystemErr(() -> {
        sun.misc.Unsafe sunMiscUnsafe = Unsafe.getSunMiscUnsafe();
        assertNotNull(sunMiscUnsafe);
    }

    @Test
    public void testInvokeConstructor() throws Throwable {

        if (UnsafeVirtualMachine.getFamily() == J9) {
            return;
        }

        // TODO: it should actually fail
        //assertNothingWrittenToSystemErr(() -> {
        try {
            UnsafeTest ut = new UnsafeTest();
            counter.set(0);
            if (getJavaVersion() >= 7) {
                UnresolvedZeroArgsClassConstructorRef<UnsafeTest> constructor = $(UnsafeTest.class).getConstructor();
                constructor.invokeOnInstance(ut);
                constructor.invokeOnInstance(ut);
                constructor.invokeOnInstance(ut);
                assertEquals(3, counter.get());
            } else {
                assertEquals(0, counter.get());
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Test
    public void testObjectRef() throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        Object object = new Object();
        assertEquals(object.hashCode(), $(object).invoke(Integer.TYPE, "hashCode", new Class<?>[0], new Object[0]).intValue());
    }

    @Test
    public void testProviderList() throws Exception {

        if (getJavaVersion() <= 5) return;

        SSLContext.getInstance("Default");

        long start = System.currentTimeMillis();

        Future<Instrumentation> instrumentationFuture = Unsafe.getInstrumentationFuture();

        Instrumentation instrumentation = instrumentationFuture.get();

        long end = System.currentTimeMillis();

        System.out.println("Instrumentation obtained in " + (end - start) + " milliseconds");

        assert null != instrumentation;

        ClassRef<Object> classRef = $("sun.security.jca.Providers").resolve();
        classRef.getModuleRef().tryAddOpens("sun.security.jca");

        ResolvedStaticObjectFieldRef<Object, ThreadLocal<ProviderList>> threadLists = classRef.<ThreadLocal<ProviderList>>staticField("threadLists").resolve();
        ResolvedStaticObjectFieldRef<Object, Integer> threadListsUsed = classRef.<Integer>staticField("threadListsUsed").resolve();

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

        threadListsUsed.set(0);
        threadLists.set(new InheritableThreadLocal<ProviderList>());
    }

}
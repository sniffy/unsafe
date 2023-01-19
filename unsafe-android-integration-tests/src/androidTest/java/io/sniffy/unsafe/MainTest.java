package io.sniffy.unsafe;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import tools.unsafe.reflection.x.StaticReferenceField;
import tools.unsafe.reflection.x.StaticReferenceFieldV2;
import tools.unsafe.reflection.x.StaticReferenceFieldV3;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Test
    public void testSystemProperties() {
        Properties p = System.getProperties();
        Enumeration keys = p.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = (String) p.get(key);
            System.out.println(key + " >>>> " + value);
        }
    }

    @Test
    public void testUnsafe() throws Exception {
        for (Method method : Class.forName("sun.misc.Unsafe").getDeclaredMethods()) {
            System.out.println(method.getName());
        }


    }

    // TODO: uncomment and implement
    /*@Test
    public void testMimic() {
        String str = "foo";
        Measurable measurable = Mimic.as(
                Measurable.class,
                str
        );

        assertEquals(str.length(), measurable.length());

    }*/

    /*@Test
    public void testVirtualMachinePID() throws Exception {

        long pid = UnsafeVirtualMachine.getPid();

        assertNotNull(pid);

    }

    @Test
    public void testVirtualMachineAttach() throws Exception {

        UnsafeVirtualMachine unsafeVirtualMachine = UnsafeVirtualMachine.attachToSelf();

        assertNotNull(unsafeVirtualMachine);

    }*/

    // TODO: uncomment and implement
    /*@Test
    public void testFinalField() throws Exception {

        UnresolvedDynamicObjectFieldRef<FinalFoo, Number> baz = ClassRef.of(FinalFoo.class).field("baz");

        FinalFoo foo = new FinalFoo(42);

        baz.set(foo, 42);

        assertEquals(42, Objects.requireNonNull(baz.get(foo)).intValue());

        baz.set(foo, 13);

        assertEquals(13, Objects.requireNonNull(baz.get(foo)).intValue());

        assertEquals(13, foo.baz); // TODO: fails on Android

    }*/

   /* public void testFinalFieldReflection() throws Exception {

        FinalFoo foo = new FinalFoo(42);

    }*/

    // TODO: uncomment and implement
    /*@Test
    public void testField() throws Exception {

        UnresolvedDynamicObjectFieldRef<Foo, Number> baz = ClassRef.of(Foo.class).field("baz");

        Foo foo = new Foo();

        baz.set(foo, 42);

        assertEquals(42, Objects.requireNonNull(baz.get(foo)).intValue());

        baz.set(foo, 13);

        assertEquals(13, Objects.requireNonNull(baz.get(foo)).intValue());

        assertEquals(13, foo.baz);

    }*/


    private final static StaticReferenceField REF = new StaticReferenceField(
            () -> SampleClass.class.getDeclaredField("foo"),
            (unsafe) -> unsafe.staticFieldBase(SampleClass.class.getDeclaredField("foo")),
            (unsafe) -> unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo"))
    );

    @Test
    public void testStaticReferenceField() throws Throwable {

        tools.unsafe.Unsafe.getSunMiscUnsafe().ensureClassInitialized(SampleClass.class);

        StaticReferenceField ref = new StaticReferenceField(
                () -> SampleClass.class.getDeclaredField("foo"),
                (unsafe) -> unsafe.staticFieldBase(SampleClass.class.getDeclaredField("foo")),
                (unsafe) -> unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo"))
        );

        Object o = new Object();

        ref.set(o);

        assertEquals(o, SampleClass.getFoo());

    }

    @Test
    public void testStaticReferenceFieldV2() throws Throwable {

        Unsafe unsafe = tools.unsafe.Unsafe.getSunMiscUnsafe();
        unsafe.ensureClassInitialized(SampleClass.class);

        StaticReferenceFieldV2 ref = new StaticReferenceFieldV2(
                unsafe.staticFieldBase(SampleClass.class.getDeclaredField("foo")),
                unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo"))
        );

        Object o = new Object();

        ref.set(o);

        assertEquals(o, SampleClass.getFoo());

    }

    @Test
    public void testStaticReferenceFieldV2_field() throws Throwable {

        Object o = new Object();

        REF.set(o);

        assertEquals(o, SampleClass.getFoo());

    }


    @Test
    public void testStaticReferenceFieldV3() throws Throwable {

        Unsafe unsafe = tools.unsafe.Unsafe.getSunMiscUnsafe();
        unsafe.ensureClassInitialized(SampleClass.class);

        StaticReferenceFieldV3 ref = new StaticReferenceFieldV3(
                SampleClass.class.getDeclaredField("foo")
        );

        Object o = new Object();

        ref.set(o);

        assertEquals(o, SampleClass.getFoo());

    }

}

package tools.unsafe;

import org.junit.Test;
import sun.misc.Unsafe;
import tools.unsafe.reflection.x.StaticReferenceField;
import tools.unsafe.reflection.x.StaticReferenceFieldV2;
import tools.unsafe.reflection.x.StaticReferenceFieldV3;

import static org.junit.Assert.assertEquals;

public class SampleClassTest {

    @Test
    public void testSystemProperties() {
        System.out.println(System.getProperties());

    }

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

    /*@Test
    public void testCodeSource() {
        System.out.println("Location is = " + Unsafe.class.getProtectionDomain().getCodeSource().getLocation());
    }*/

    @Test
    public void testStaticReferenceFieldV2() throws Throwable {

        Unsafe unsafe = tools.unsafe.Unsafe.getSunMiscUnsafe();
        //unsafe.ensureClassInitialized(SampleClass.class);

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
        //unsafe.ensureClassInitialized(SampleClass.class);

        StaticReferenceFieldV3 ref = new StaticReferenceFieldV3(
                SampleClass.class.getDeclaredField("foo")
        );

        Object o = new Object();

        ref.set(o);

        assertEquals(o, SampleClass.getFoo());

    }

}

package tools.unsafe;

import org.junit.Test;
import tools.unsafe.reflection.x.StaticReferenceField;
import tools.unsafe.reflection.x.StaticReferenceFieldV2;
import tools.unsafe.reflection.x.StaticReferenceFieldV3;

import static org.junit.Assert.assertEquals;

public class SampleClassTest {

    // Consider also jdk.internal.misc.Unsafe and jdk.internal.reflect.Unsafe


    private final static StaticReferenceField REF = new StaticReferenceField(
            () -> SampleClass.class.getDeclaredField("foo"),
            (unsafe) -> unsafe.staticFieldBase(SampleClass.class.getDeclaredField("foo")),
            (unsafe) -> unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo"))
    );

    @Test
    public void testSystemProperties() {
        System.out.println(System.getProperties());

    }

    @Test
    public void testStaticReferenceFieldV2_field() throws Throwable {

        Object o = new Object();

        REF.set(o);

        assertEquals(o, SampleClass.getFoo());

    }

    @Test
    public void testStaticReferenceField() throws Throwable {

        UnsafeProvider.getSunMiscUnsafe().ensureClassInitialized(SampleClass.class);

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

        UnsafeProvider.getSunMiscUnsafe().ensureClassInitialized(SampleClass.class);

        //unsafe.ensureClassInitialized(SampleClass.class);

        StaticReferenceFieldV2 ref = new StaticReferenceFieldV2(
                UnsafeProvider.getSunMiscUnsafe().staticFieldBase(SampleClass.class.getDeclaredField("foo")),
                UnsafeProvider.getSunMiscUnsafe().staticFieldOffset(SampleClass.class.getDeclaredField("foo"))
        );

        Object o = new Object();

        ref.set(o);

        assertEquals(o, SampleClass.getFoo());

    }

    @Test
    public void testStaticReferenceFieldV3() throws Throwable {

        UnsafeProvider.getSunMiscUnsafe().ensureClassInitialized(SampleClass.class);

        //unsafe.ensureClassInitialized(SampleClass.class);

        StaticReferenceFieldV3 ref = new StaticReferenceFieldV3(
                SampleClass.class.getDeclaredField("foo")
        );

        Object o = new Object();

        ref.set(o);

        assertEquals(o, SampleClass.getFoo());

    }

}

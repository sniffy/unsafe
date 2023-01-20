package tools.unsafe;

import org.junit.Test;
import tools.unsafe.reflection.x.StaticReferenceField;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class SampleClassTest {

    // Consider also jdk.internal.misc.Unsafe and jdk.internal.reflect.Unsafe
    public static sun.misc.Unsafe getSunMiscUnsafe() {
        return SunMiscUnsafeHolder.UNSAFE;
    }

    @Test
    public void testStaticReferenceField() throws Throwable {

        getSunMiscUnsafe().ensureClassInitialized(SampleClass.class);

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
    public void testSystemProperties() {
        System.out.println(System.getProperties());

    }

    /*private final static StaticReferenceField REF = new StaticReferenceField(
            () -> SampleClass.class.getDeclaredField("foo"),
            (unsafe) -> unsafe.staticFieldBase(SampleClass.class.getDeclaredField("foo")),
            (unsafe) -> unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo"))
    );*/

    private static class SunMiscUnsafeHolder {

        private final static sun.misc.Unsafe UNSAFE;

        static {
            sun.misc.Unsafe unsafe = null;
            try {
                Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe"); // TODO: check THE_ONE for Android as well
                f.setAccessible(true);
                unsafe = (sun.misc.Unsafe) f.get(null);
            } catch (Throwable e) {
                e.printStackTrace();
                assert false : e;
            }
            UNSAFE = unsafe;
        }

    }
/*
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

    }*/

}

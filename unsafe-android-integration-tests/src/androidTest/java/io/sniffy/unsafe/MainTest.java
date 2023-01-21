package io.sniffy.unsafe;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import tools.unsafe.reflection.x.StaticReferenceField;

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

}

package tools.unsafe;

import org.junit.Test;
import tools.unsafe.reflection.ReferenceFieldHandle;

import static org.junit.Assert.assertEquals;

public class SampleClassTest {

    // Consider also jdk.internal.misc.Unsafe and jdk.internal.reflect.Unsafe

    private final static ReferenceFieldHandle<Object> fieldHandle = new ReferenceFieldHandle<>(() -> SampleClass.class.getDeclaredField("foo"));

    @Test
    public void testReferenceFieldHandle() {
        Object o = new Object();
        fieldHandle.set(o);
        assertEquals(o, SampleClass.getFoo());
        assertEquals(SampleClass.getFoo(), fieldHandle.get());

    }

    @Test
    public void testSystemProperties() {
        System.out.println(System.getProperties());

    }

}

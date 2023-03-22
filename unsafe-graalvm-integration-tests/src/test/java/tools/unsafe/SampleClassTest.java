package tools.unsafe;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import tools.unsafe.field.FieldHandles;
import tools.unsafe.sut.SampleClass;

import static org.junit.Assert.assertEquals;

public class SampleClassTest {

    // Consider also jdk.internal.misc.Unsafe and jdk.internal.reflect.Unsafe

    //private final static ReferenceFieldHandle<Object> fieldHandle = new ReferenceFieldHandle<>(() -> SampleClass.class.getDeclaredField("foo"));
    private final static FieldHandles.ReferenceStaticFieldHandle<Object> fieldHandle = FieldHandles.staticReferenceField(() -> SampleClass.class.getDeclaredField("foo"));

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

    @Test
    public void testCompatibilityKit() {
        JUnitCore.runClasses(ReferenceStaticFieldHandleTest.class);
    }

}

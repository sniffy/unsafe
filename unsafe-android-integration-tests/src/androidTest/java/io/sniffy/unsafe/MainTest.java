package io.sniffy.unsafe;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import tools.unsafe.mimic.Mimic;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.field.objects.unresolved.UnresolvedDynamicObjectFieldRef;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;
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
    public void testMimic() {
        String str = "foo";
        Measurable measurable = Mimic.as(
                Measurable.class,
                str
        );

        assertEquals(str.length(), measurable.length());

    }

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

    @Test
    public void testFinalField() throws Exception {

        UnresolvedDynamicObjectFieldRef<FinalFoo, Number> baz = ClassRef.of(FinalFoo.class).field("baz");

        FinalFoo foo = new FinalFoo(42);

        baz.set(foo, 42);

        assertEquals(42, Objects.requireNonNull(baz.get(foo)).intValue());

        baz.set(foo, 13);

        assertEquals(13, Objects.requireNonNull(baz.get(foo)).intValue());

        assertEquals(13, foo.baz); // TODO: fails on Android

    }

   /* public void testFinalFieldReflection() throws Exception {

        FinalFoo foo = new FinalFoo(42);

    }*/

    @Test
    public void testField() throws Exception {

        UnresolvedDynamicObjectFieldRef<Foo, Number> baz = ClassRef.of(Foo.class).field("baz");

        Foo foo = new Foo();

        baz.set(foo, 42);

        assertEquals(42, Objects.requireNonNull(baz.get(foo)).intValue());

        baz.set(foo, 13);

        assertEquals(13, Objects.requireNonNull(baz.get(foo)).intValue());

        assertEquals(13, foo.baz);

    }

    public interface Measurable {

        int length();

    }

    public static class FinalFoo {

        private final int baz;

        public FinalFoo(int baz) {
            this.baz = baz;
        }
    }

    public static class Foo {

        private int baz = 42;

    }

}

package tools.unsafe;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;
import tools.unsafe.vm.UnsafeVirtualMachine;
import tools.unsafe.vm.VirtualMachineFamily;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

public class SampleClassTest {

    @Test
    public void testSystemProperties() {
        System.out.println(System.getProperties());

    }

    /*@Test
    public void testCodeSource() {
        System.out.println("Location is = " + Unsafe.class.getProtectionDomain().getCodeSource().getLocation());
    }*/

    @Test
    public void testStaticObjectFieldRef() throws Exception {

        Object object = SampleClass.getFoo();

        StaticObjectFieldRef<Object> foo = new StaticObjectFieldRef<>(SampleClass.class.getDeclaredField("foo"));

        Assertions.assertEquals(object, foo.get());

        if (UnsafeVirtualMachine.getFamily() == VirtualMachineFamily.GRAALVM_NATIVE) {
            if (UnsafeVirtualMachine.getJavaVersion() < 16) {
                Object newObject = new Object();
                foo.set(newObject);
                Assertions.assertEquals(newObject, foo.get());
            } else {
                //noinspection CatchMayIgnoreException
                try {
                    foo.set(new Object());
                    fail("Should have failed");
                } catch (Throwable e) {
                    Assertions.assertNotNull(e);


                    Field declaredField = SampleClass.class.getDeclaredField("foo");
                    long fieldOffset = tools.unsafe.Unsafe.getSunMiscUnsafe().staticFieldOffset(declaredField);

                    StaticFinalObjectFieldRef<Object> finalFoo = new StaticFinalObjectFieldRef<>(declaredField) {

                        @Override
                        public void set(Unsafe unsafe, Object value) throws NoSuchFieldException {
                            unsafe.putObject(SampleClass.class, fieldOffset, value);
                        }

                    };

                    Object newObject = new Object();
                    finalFoo.set(newObject);
                    Assertions.assertEquals(newObject, foo.get());

                }
            }
        } else {
            Object newObject = new Object();
            foo.set(newObject);
            Assertions.assertEquals(newObject, foo.get());
        }

    }

    /*//@Test
    public void testMethodHandles() throws Throwable {

        Class<?> memberNameClass = Class.forName("java.lang.invoke.MemberName");
        Field modField = memberNameClass.getDeclaredField("flags");

        Constructor<?> constructor = memberNameClass.getConstructor(
                Field.class, boolean.class
        );
        Unsafe.setAccessible(constructor);

        Object memberName = constructor.newInstance(
                SampleClass.class.getDeclaredField("bar"),
                true
        );

        Method getDirectFieldNoSecurityManager = MethodHandles.Lookup.class.getDeclaredMethod(
                "getDirectFieldNoSecurityManager",
                byte.class,
                Class.class,
                memberNameClass
        );

        Unsafe.setAccessible(getDirectFieldNoSecurityManager);

        MethodHandle mh = (MethodHandle) getDirectFieldNoSecurityManager.invoke(
                MethodHandles.lookup(),
                (byte) 4, // REF_putStatic
                SampleClassTest.class,
                memberName
        );

        System.out.println(mh);

        System.out.println(SampleClass.getBar());

        mh.invoke(new Object());

        System.out.println(SampleClass.getBar());

    }

    //@Test
    public void testSetFinalStaticField() throws Exception {

        Unsafe.getSunMiscUnsafe().ensureClassInitialized(SampleClass.class);

        System.out.println("Original value is " + SampleClass.getFoo());


        Field declaredField = SampleClass.class.getDeclaredField("foo");
        System.out.println(declaredField);
        System.out.println("ïsVolatile="+Modifier.isVolatile(declaredField.getModifiers()));
        System.out.println("ïsFinal="+Modifier.isFinal(declaredField.getModifiers()));

        long l = Unsafe.getSunMiscUnsafe().staticFieldOffset(declaredField);
        l++;
        l--;

        System.out.println("static fielf oddset is " + l);

        Unsafe.getSunMiscUnsafe().putObjectVolatile(SampleClass.class, l, new Object());
        Unsafe.getSunMiscUnsafe().putObject(SampleClass.class, l, new Object());

        Unsafe.getSunMiscUnsafe().putObjectVolatile(SampleClass.class, l, new Object());

        System.out.println("Updated value via Unsafe is " + SampleClass.getFoo());

        Unsafe.setAccessible(declaredField);

        Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
        Unsafe.setAccessible(getDeclaredFields0);
        Field[] privateFields = (Field[]) getDeclaredFields0.invoke(Field.class, false);

        System.out.println(Arrays.toString(privateFields));

        Field modifiersField = null;

        for (Field privateField : privateFields) {
            if ("modifiers".equals(privateField.getName())) {
                modifiersField = privateField;
                break;
            }
        }

        //Field modifiersField = Field.class.getDeclaredField("modifiers");
        //modifiersField.setAccessible(true);
        Unsafe.setAccessible(modifiersField);
        modifiersField.setInt(declaredField, declaredField.getModifiers() & ~Modifier.FINAL);
        modifiersField.setInt(declaredField, declaredField.getModifiers() & ~Modifier.PRIVATE);
        modifiersField.setInt(declaredField, declaredField.getModifiers() & Modifier.PUBLIC);
        modifiersField.setInt(declaredField, declaredField.getModifiers() & Modifier.FINAL);

        MethodHandles.lookup().unreflectSetter(declaredField);

        declaredField.set(null, new Object());



        System.out.println("Updated value via reflection is " + SampleClass.getFoo());

        ResolvedStaticObjectFieldRef<SampleClass, Object> fieldRef = FieldRefs.of(declaredField);

        Unsafe.getSunMiscUnsafe().putObjectVolatile(SampleClass.class, l, new Object());

        System.out.println("Unsafe.getSunMiscUnsafe().putObjectVolatile(fieldRef.getObject(), fieldRef.getOffset(), new Object())=" + SampleClass.getFoo());

        //fieldRef.set(new Object());

        System.out.println(SampleClass.getFoo());

        System.out.println(ClassRef.of(SampleClass.class));
    }*/

}

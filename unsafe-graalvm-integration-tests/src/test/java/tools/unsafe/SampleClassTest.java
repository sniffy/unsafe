package tools.unsafe;

import org.junit.Test;
import sun.misc.Unsafe;
import tools.unsafe.ng.Methods;
import tools.unsafe.ng.StaticMethodRef;
import tools.unsafe.vm.UnsafeVirtualMachine;
import tools.unsafe.vm.VirtualMachineFamily;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class SampleClassTest {

    @Test
    public void testSystemProperties() {
        System.out.println(System.getProperties());

    }

    /*@Test
    public void testCodeSource() {
        System.out.println("Location is = " + Unsafe.class.getProtectionDomain().getCodeSource().getLocation());
    }*/

    private static <T> T privateLookup(Class clazz) throws IllegalAccessException, NoSuchMethodException {
        return (T) Methods.privateLookupIn(SampleClass.class);
        // code below works on JAva 9+ only
        /*return (T) MethodHandles.privateLookupIn(
                SampleClass.class,
                MethodHandles.lookup()
        );*/
    }

    @Test
    public void testPrivateMethod() throws Exception {

        Method privateMethod = SampleClass.class.getDeclaredMethod("privateMethod", String.class);

        tools.unsafe.Unsafe.setAccessible(privateMethod);

        privateMethod.invoke(null, "argument");

    }

    @Test
    public void testMethodHandle() throws Throwable {

        Method privateMethod = SampleClass.class.getDeclaredMethod("privateMethod", String.class);
        tools.unsafe.Unsafe.setAccessible(privateMethod);

        MethodHandle unreflect = MethodHandles.lookup().unreflect(privateMethod);
        unreflect.invoke("argument");

    }

    @Test
    public void testMethosLookup() throws Throwable {

        if (VirtualMachineFamily.GRAALVM_NATIVE == UnsafeVirtualMachine.getFamily() /*&& UnsafeVirtualMachine.getJavaVersion() < 9*/)
            return;

        /*try {
            MethodHandle privateMethod = MethodHandles.lookup().findStatic(SampleClass.class, "privateMethod", MethodType.methodType(void.class, String.class));
            privateMethod.invoke("argument it should have not received");
            fail("Should have failed");
        } catch (Throwable e) {
            assertNotNull(e);
        }

        StaticObjectFieldRef<MethodHandles.Lookup> implLookupField = new StaticObjectFieldRef<>(MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP"));
        MethodHandles.Lookup implLookup = implLookupField.get();

        MethodHandle privateMethod = implLookup.findStatic(SampleClass.class, "privateMethod", MethodType.methodType(void.class, String.class));

        privateMethod.invoke("argument");*/

        /*MethodHandle privateMethod = MethodHandles.privateLookupIn(
                SampleClass.class,
                MethodHandles.lookup()
        ).findStatic(
                SampleClass.class, "privateMethod", MethodType.methodType(void.class, String.class)
        );*/

        MethodHandle privateMethod = Methods.privateLookupIn(
                SampleClass.class
        ).findStatic(
                SampleClass.class, "privateMethod", MethodType.methodType(void.class, String.class)
        );

        privateMethod.invoke("argument");

        SampleClassTest.<MethodHandles.Lookup>privateLookup(SampleClass.class).findStatic(
                SampleClass.class, "privateMethod", MethodType.methodType(void.class, String.class)
        ).invoke("argument");


    }

    private final static Field fooField;
    private final static long fooOffset;

    static {
        Field f;
        try {
            f = SampleClass.class.getDeclaredField("foo");
        } catch (Exception e) {
            f = null;
        }
        fooField = f;
        fooOffset = tools.unsafe.Unsafe.getSunMiscUnsafe().staticFieldOffset(fooField);
    }

    @Test
    public void testNG() throws Throwable {
        StaticMethodRef.VoidOneParam<String> privateMethod =
                Methods.staticMethodRef(SampleClass.class.getDeclaredMethod("privateMethod", String.class)).
                        asVoidMethod(String.class);
        privateMethod.invoke("abracadabra");
    }

    private final static StaticMethodRef.VoidOneParam<String> privateMethodField =
            Methods.staticMethodRef(() -> SampleClass.class.getDeclaredMethod("privateMethod", String.class)).
                    asVoidMethod(String.class);
    private static final StaticObjectFieldRef<Object> fooFieldRef = new StaticObjectFieldRef<Object>(
            () -> SampleClass.class.getDeclaredField("foo"),
            (unsafe, value) -> unsafe.putObject(SampleClass.class, unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo")), value)
    );

    @Test
    public void testMethosLookupViaImpl() throws Throwable {

        if (VirtualMachineFamily.GRAALVM_NATIVE == UnsafeVirtualMachine.getFamily() /*&& UnsafeVirtualMachine.getJavaVersion() < 9*/)
            return;

        try {
            MethodHandle privateMethod = MethodHandles.lookup().findStatic(SampleClass.class, "privateMethod", MethodType.methodType(void.class, String.class));
            privateMethod.invoke("argument it should have not received");
            fail("Should have failed");
        } catch (Throwable e) {
            assertNotNull(e);
        }

        StaticObjectFieldRef<MethodHandles.Lookup> implLookupField = new StaticObjectFieldRef<MethodHandles.Lookup>(MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP"));
        MethodHandles.Lookup implLookup = implLookupField.get();

        MethodHandle privateMethod = implLookup.findStatic(SampleClass.class, "privateMethod", MethodType.methodType(void.class, String.class));

        privateMethod.invoke("argument");


    }

    @Test
    public void testNGViaField() throws Throwable {
        privateMethodField.invoke("abracadabra");
    }

    @Test
    public void testFooFieldRef() {
        Object object = SampleClass.getFoo();
        Object value = new Object();
        fooFieldRef.set(value);
        assertEquals(value, SampleClass.getFoo());
    }

    @Test
    public void testUnsafeObjectSetter() throws Exception {

        Object object = SampleClass.getFoo();

        StaticObjectFieldRef<Object> foo = new StaticObjectFieldRef<Object>(SampleClass.class.getDeclaredField("foo"), new UnsafeObjectSetter<Object>() {
            @Override
            public void set(Unsafe unsafe, Object value) throws NoSuchFieldException {
                unsafe.putObject(SampleClass.class, unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo")), value);
            }
        });

        Object value = new Object();
        foo.set(value);

        assertEquals(value, SampleClass.getFoo());

    }

    @Test
    public void testUnsafeObjectSetterUsingLambda() throws Exception {

        Object object = SampleClass.getFoo();

        StaticObjectFieldRef<Object> foo = new StaticObjectFieldRef<Object>(fooField, new UnsafeObjectSetter<Object>() {
            @Override
            public void set(Unsafe unsafe, Object value) throws NoSuchFieldException {
                unsafe.putObject(SampleClass.class, tools.unsafe.Unsafe.getSunMiscUnsafe().staticFieldOffset(SampleClass.class.getDeclaredField("foo")), value);
            }
        });

        Object value = new Object();
        foo.set(value);

        assertEquals(value, SampleClass.getFoo());

    }

    @Test
    public void testStaticObjectFieldRef() throws Exception {

        Object object = SampleClass.getFoo();

        StaticObjectFieldRef<Object> foo = new StaticObjectFieldRef<>(SampleClass.class.getDeclaredField("foo"));

        assertEquals(object, foo.get());

        if (UnsafeVirtualMachine.getFamily() == VirtualMachineFamily.GRAALVM_NATIVE) {
            if (UnsafeVirtualMachine.getJavaVersion() < 16) {
                Object newObject = new Object();
                foo.set(newObject);
                assertEquals(newObject, foo.get());
            } else {
                try {
                    foo.set(new Object());
                    fail("Should have failed");
                } catch (Throwable e) {
                    assertNotNull(e);


                    Field declaredField = SampleClass.class.getDeclaredField("foo");
                    long fieldOffset = tools.unsafe.Unsafe.getSunMiscUnsafe().staticFieldOffset(declaredField);

                    StaticFinalObjectFieldRef<Object> finalFoo = new StaticFinalObjectFieldRef<Object>(declaredField, SampleClass.class, fieldOffset) {

                        @Override
                        public void set(Unsafe unsafe, Object value) throws NoSuchFieldException {
                            //unsafe.putObject(clazzField, offsetField, value);
                            unsafe.putObject(SampleClass.class, fieldOffset, value);
                        }

                    };

                    Object newObject = new Object();
                    finalFoo.set(newObject);
                    assertEquals(newObject, foo.get());

                }
            }
        } else {
            Object newObject = new Object();
            foo.set(newObject);
            assertEquals(newObject, foo.get());
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

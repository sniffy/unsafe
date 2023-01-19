package tools.unsafe;

import org.junit.Ignore;
import org.junit.Test;
import sun.misc.Unsafe;
import tools.unsafe.reflection.FieldOffsetSupplier;
import tools.unsafe.reflection.StaticObjectField;
import tools.unsafe.reflection.ng.Methods;
import tools.unsafe.reflection.ng.StaticMethodRef;
import tools.unsafe.reflection.x.StaticReferenceField;
import tools.unsafe.reflection.x.StaticReferenceFieldV2;
import tools.unsafe.reflection.x.StaticReferenceFieldV3;

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

    private final static StaticReferenceField REF = new StaticReferenceField(
            () -> SampleClass.class.getDeclaredField("foo"),
            (unsafe) -> unsafe.staticFieldBase(SampleClass.class.getDeclaredField("foo")),
            (unsafe) -> unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo"))
    );
    /*private static final StaticObjectFieldRef<Object> fooFieldRef = new StaticObjectFieldRef<Object>(
            () -> SampleClass.class.getDeclaredField("foo"),
            (unsafe, value) -> unsafe.putObject(SampleClass.class, unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo")), value)
    );*/
    private static final StaticObjectField<Object> fooFieldRef = new StaticObjectField<Object>(
            () -> SampleClass.class.getDeclaredField("foo"),
            (unsafe) -> unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo"))
    );

    static {
        Field f;
        try {
            f = SampleClass.class.getDeclaredField("foo");
        } catch (Exception e) {
            e.printStackTrace();
            f = null;
        }
        fooField = f;
        fooOffset = tools.unsafe.Unsafe.getSunMiscUnsafe().staticFieldOffset(fooField);
    }

    private static <T> T privateLookup(Class clazz) throws IllegalAccessException, NoSuchMethodException {
        return (T) Lookups.privateLookupIn(SampleClass.class);
        // code below works on JAva 9+ only
        /*return (T) MethodHandles.privateLookupIn(
                SampleClass.class,
                MethodHandles.lookup()
        );*/
    }

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

    @Test
    public void testPrivateMethod() throws Exception {

        Method privateMethod = SampleClass.class.getDeclaredMethod("privateMethod", String.class);

        Reflections.setAccessible(privateMethod);

        privateMethod.invoke(null, "argument");

    }

    private final static Field fooField;
    private final static long fooOffset;

    @Test
    @Ignore
    public void testMethodHandle() throws Throwable {

        Method privateMethod = SampleClass.class.getDeclaredMethod("privateMethod", String.class);
        Reflections.setAccessible(privateMethod);

        MethodHandle unreflect = MethodHandles.lookup().unreflect(privateMethod);
        unreflect.invoke("argument");

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


    // TODO: implement code below
    /*private static final StaticObjectFieldRef<Object> fooFieldRef = new StaticObjectFieldRef<Object>(
            (unsafe) -> unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo"))
    );*/

    @Test
    @Ignore("use modules logic upfront")
    public void testMethosLookup() throws Throwable {

        if (VirtualMachineFamily.GRAALVM_NATIVE == Java.virtualMachineFamily() /*&& UnsafeVirtualMachine.getJavaVersion() < 9*/)
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

        MethodHandle privateMethod = Lookups.privateLookupIn(
                SampleClass.class
        ).findStatic(
                SampleClass.class, "privateMethod", MethodType.methodType(void.class, String.class)
        );

        privateMethod.invoke("argument");

        SampleClassTest.<MethodHandles.Lookup>privateLookup(SampleClass.class).findStatic(
                SampleClass.class, "privateMethod", MethodType.methodType(void.class, String.class)
        ).invoke("argument");


    }

    /*@Test
    @Ignore
    public void testVarHandle() throws Exception {

        Modules modules = new Modules(MethodHandles.Lookup.class.getModule());
        modules.addOpens("java.lang.invoke");

        Field declaredField = SampleClass.class.getDeclaredField("foo");
        Reflections.setAccessible(declaredField);
        Reflections.removeFinalModifier(declaredField);

        VarHandle varHandle = Lookups.trustedLookup().findStaticVarHandle(SampleClass.class, "foo", Object.class);

        Object newObject = new Object();
        varHandle.set(newObject);
        assertEquals(newObject, SampleClass.getFoo());
    }*/

    @Test
    @Ignore
    public void testMethosLookupViaImpl() throws Throwable {

        if (VirtualMachineFamily.GRAALVM_NATIVE == Java.virtualMachineFamily() /*&& UnsafeVirtualMachine.getJavaVersion() < 9*/)
            return;

        try {
            MethodHandle privateMethod = MethodHandles.lookup().findStatic(SampleClass.class, "privateMethod", MethodType.methodType(void.class, String.class));
            privateMethod.invoke("argument it should have not received");
            fail("Should have failed");
        } catch (Throwable e) {
            assertNotNull(e);
        }

        StaticObjectField<MethodHandles.Lookup> implLookupField = new StaticObjectField<MethodHandles.Lookup>(() -> MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP"));
        MethodHandles.Lookup implLookup = implLookupField.get();

        MethodHandle privateMethod = implLookup.findStatic(SampleClass.class, "privateMethod", MethodType.methodType(void.class, String.class));

        privateMethod.invoke("argument");


    }

    @Test
    @Ignore
    public void testNGViaField() throws Throwable {
        privateMethodField.invoke("abracadabra");
    }

    @Test
    @Ignore
    public void testFooFieldRef() {
        Object object = SampleClass.getFoo();
        Object value = new Object();
        fooFieldRef.set(value);
        assertEquals(value, SampleClass.getFoo());
    }

    @Test
    @Ignore
    public void testUnsafeObjectSetter() throws Exception {

        Object object = SampleClass.getFoo();

        StaticObjectField<Object> foo = new StaticObjectField<Object>(() -> SampleClass.class.getDeclaredField("foo"), new FieldOffsetSupplier() {
            @Override
            public long offset(Unsafe unsafe) throws Throwable {
                return unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo"));
            }
        });
        /*, new UnsafeObjectSetter<Object>() {
            @Override
            public void set(Unsafe unsafe, Object value) throws NoSuchFieldException {
                unsafe.putObject(SampleClass.class, unsafe.staticFieldOffset(SampleClass.class.getDeclaredField("foo")), value);
            }
        });*/

        Object value = new Object();
        foo.set(value);

        assertEquals(value, SampleClass.getFoo());

    }

    /*@Test
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

    }*/

    @Test
    @Ignore

    public void testStaticObjectFieldRef() throws Exception {

        Object object = SampleClass.getFoo();

        StaticObjectField<Object> foo = new StaticObjectField<>(() -> SampleClass.class.getDeclaredField("foo"));

        assertEquals(object, foo.get());

        if (Java.virtualMachineFamily() == VirtualMachineFamily.GRAALVM_NATIVE) {
            if (Java.version() < 16) {
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

                    /*StaticFinalObjectFieldRef<Object> finalFoo = new StaticFinalObjectFieldRef<Object>(declaredField, SampleClass.class, fieldOffset) {

                        @Override
                        public void set(Unsafe unsafe, Object value) throws NoSuchFieldException {
                            //unsafe.putObject(clazzField, offsetField, value);
                            unsafe.putObject(SampleClass.class, fieldOffset, value);
                        }

                    };

                    Object newObject = new Object();
                    finalFoo.set(newObject);
                    assertEquals(newObject, foo.get());*/

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

package tools.unsafe;

import tools.unsafe.reflection.clazz.ClassRef;

public class SampleClass {

    private static final Object foo = new Object();

    private static final Object bar = new Object();

    private static void privateMethod(String argument) {
        System.out.println("I'm called via reflection with argument " + argument);
    }

    protected static Object getFoo() {
        return foo;
    }

    protected static Object getBar() {
        return bar;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperties());

        ClassRef.of(SampleClass.class).staticField("foo").set("Updated!");

        System.out.println(foo);

    }

}

package tools.unsafe;

public class SampleClass {

    @SuppressWarnings("unused")
    private static final Object unusedObject1 = new Object();

    private static final Object foo = new Object();

    @SuppressWarnings("unused")
    private static final Object unusedObject2 = new Object();

    private static final Object bar = new Object();

    private static final void privateMethod(String argument) {
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

        //ClassRef.of(SampleClass.class).staticField("foo").set("Updated!");

        System.out.println(foo);

    }

}

package io.sniffy.unsafe.agent;

import java.lang.instrument.Instrumentation;

public class UnsafeAgent {

    public static Instrumentation instrumentation;

    public static void agentmain(String args, Instrumentation inst) {
        instrumentation = inst;
        System.out.println("Attached agent;");

        /*try {
            instrumentation.retransformClasses(Class.forName("sun.security.jca.Providers"));
            System.out.println("Retransformed Providers.class");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        System.out.println("Not retransforming anything");
    }

}
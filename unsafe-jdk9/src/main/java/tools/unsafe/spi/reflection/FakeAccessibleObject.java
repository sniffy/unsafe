package tools.unsafe.spi.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.ReflectPermission;

/**
 * FakeAccessibleObject class has similar layout as {@link AccessibleObject} and can be used for calculating offsets
 */
@SuppressWarnings({"unused", "NullableProblems"})
public class FakeAccessibleObject implements AnnotatedElement {

    // Reflection factory used by subclasses for creating field,
    // method, and constructor accessors. Note that this is called
    // very early in the bootstrapping process.
    static final Object reflectionFactory = new Object();
    /**
     * The Permission object that is used to check whether a client
     * has sufficient privilege to defeat Java language access
     * control checks.
     */
    static final private java.security.Permission ACCESS_PERMISSION =
            new ReflectPermission("suppressAccessChecks");
    // Indicates whether language-level access checks are overridden
    // by this object. Initializes to "false". This field is used by
    // Field, Method, and Constructor.
    //
    // NOTE: for security purposes, this field must not be visible
    // outside this package.
    boolean override;
    volatile Object securityCheckCache;

    //@Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return false;
    }

    //@Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return null;
    }

    //@Override
    public Annotation[] getAnnotations() {
        return new Annotation[0];
    }

    //@Override
    public Annotation[] getDeclaredAnnotations() {
        return new Annotation[0];
    }

}

package tools.unsafe;

import com.oracle.svm.core.annotate.AutomaticFeature;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeClassInitialization;
import tools.unsafe.reflection.FieldSupplier;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AutomaticFeature
public class RuntimeClassInitializationFeature implements Feature {

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        //RuntimeClassInitialization.initializeAtRunTime(SampleClass.class);
        try {
            access.registerMethodOverrideReachabilityHandler(
                    (duringAnalysisAccess, executable) -> {
                        try {
                            if (executable instanceof Method) {
                                Method method = (Method) executable;
                                Field field = (Field) method.invoke(null);
                                Class<?> declaringClass = field.getDeclaringClass();
                                RuntimeClassInitialization.initializeAtRunTime(declaringClass);
                            }
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    FieldSupplier.class.getMethod("field")
            );
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}

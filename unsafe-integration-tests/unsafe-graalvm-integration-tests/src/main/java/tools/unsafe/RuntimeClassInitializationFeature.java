package tools.unsafe;

import com.oracle.svm.core.annotate.AutomaticFeature;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeClassInitialization;

@AutomaticFeature
public class RuntimeClassInitializationFeature implements Feature {

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        RuntimeClassInitialization.initializeAtRunTime(SampleClass.class);
    }

}

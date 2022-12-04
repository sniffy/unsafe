package tools.unsafe.vm;

import org.junit.jupiter.api.Test;
import tools.unsafe.Unsafe;

import static com.github.stefanbirkner.systemlambda.SystemLambda.assertNothingWrittenToSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static org.junit.jupiter.api.Assertions.*;

class UnsafeVirtualMachineTest {

    @Test
    void attachToSelf() throws Exception {
        assertNothingWrittenToSystemErr(() -> {
            UnsafeVirtualMachine unsafeVirtualMachine = UnsafeVirtualMachine.attachToSelf();
            assertNotNull(unsafeVirtualMachine);
        });
    }
}
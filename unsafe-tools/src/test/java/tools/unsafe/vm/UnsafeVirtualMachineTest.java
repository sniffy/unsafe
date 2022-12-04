package tools.unsafe.vm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UnsafeVirtualMachineTest {

    @Test
    void attachToSelf() throws Exception {
        //assertNothingWrittenToSystemErr(() -> {
            UnsafeVirtualMachine unsafeVirtualMachine = UnsafeVirtualMachine.attachToSelf();
            assertNotNull(unsafeVirtualMachine);
        //});
    }

}
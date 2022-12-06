package tools.unsafe.vm;


import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UnsafeVirtualMachineTest {

    @Test
    public void attachToSelf() throws Exception {
        //assertNothingWrittenToSystemErr(() -> {
        UnsafeVirtualMachine unsafeVirtualMachine = UnsafeVirtualMachine.attachToSelf();
        assertNotNull(unsafeVirtualMachine);
        //});
    }

}
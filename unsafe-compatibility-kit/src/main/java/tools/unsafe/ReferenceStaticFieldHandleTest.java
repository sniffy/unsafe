package tools.unsafe;

import org.junit.Test;

public class ReferenceStaticFieldHandleTest {

    @Test
    public void testShouldFail() {
        throw new RuntimeException("Fail on purpose");
    }

}

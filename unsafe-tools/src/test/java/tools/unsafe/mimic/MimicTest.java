package tools.unsafe.mimic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MimicTest {

    @Test
    public void testMimic() {
        String str = "foo";
        Measurable measurable = Mimic.as(
                Measurable.class,
                str
        );

        assertEquals(str.length(), measurable.length());

    }

    public interface Measurable {

        int length();

    }


}
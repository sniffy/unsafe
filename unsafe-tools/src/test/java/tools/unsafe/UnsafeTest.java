package tools.unsafe;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static tools.unsafe.Unsafe.$;

class UnsafeTest {

    @Test
    public void testObjectRef() throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        Object object = new Object();
        Assertions.assertEquals(object.hashCode(), Unsafe.$(object).invoke(Integer.TYPE, "hashCode", new Class<?>[0], new Object[0]));
    }

}
package io.sniffy.unsafe;

import io.sniffy.unsafe.reflection.UnresolvedRefException;
import io.sniffy.unsafe.reflection.UnsafeInvocationException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static io.sniffy.unsafe.Unsafe.$;
import static org.junit.jupiter.api.Assertions.*;

class UnsafeTest {

    @Test
    public void testObjectRef() throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        Object object = new Object();
        assertEquals(object.hashCode(), $(object).invoke(Integer.TYPE, "hashCode", new Class<?>[0], new Object[0]));
    }

}
package tools.unsafe;

import java.lang.reflect.Field;

public class SampleClassUpdater {

    private final Object object;
    private final long offset;

    public SampleClassUpdater(Object object, long offset) {
        this.object = object;
        this.offset = offset;
    }

    public SampleClassUpdater(Object object, Field field) {
        this.object = object;
        sun.misc.Unsafe unsafe = Unsafe.getSunMiscUnsafe();

        this.offset = unsafe.staticFieldOffset(field);
    }

    public void update() {
        Unsafe.getSunMiscUnsafe().putObjectVolatile(object, offset, new Object());
    }

}

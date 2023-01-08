package tools.unsafe;

import java.lang.reflect.Field;

public class FrameworkSampleClassUpdater {

    private final Object object;
    private final long offset;

    public FrameworkSampleClassUpdater(Object object, long offset) {
        this.object = object;
        this.offset = offset;
    }

    public FrameworkSampleClassUpdater(Object object, Field field) {
        this.object = object;
        sun.misc.Unsafe unsafe = Unsafe.getSunMiscUnsafe();

        this.offset = unsafe.staticFieldOffset(field);
    }

    public void update() {
        Unsafe.getSunMiscUnsafe().putObjectVolatile(object, offset, new Object());
    }

}

package tools.unsafe.reflection.x;

import tools.unsafe.Unsafe;

public class StaticReferenceFieldV2 {

    private final Object base;
    private final long offset;

    public StaticReferenceFieldV2(Object base, long offset) {
        this.base = base;
        this.offset = offset;
    }

    public void set(Object reference) throws Throwable {
        Unsafe.getSunMiscUnsafe().putObjectVolatile(base, offset, reference);
    }

}

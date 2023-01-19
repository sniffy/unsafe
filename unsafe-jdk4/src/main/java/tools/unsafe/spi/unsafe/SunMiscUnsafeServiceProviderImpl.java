package tools.unsafe.spi.unsafe;

import tools.unsafe.internal.UnsafeToolsLogging;

import java.lang.reflect.Field;

public class SunMiscUnsafeServiceProviderImpl implements VintageSunMiscUnsafeServiceProvider {

    private final static sun.misc.Unsafe UNSAFE;

    static {
        sun.misc.Unsafe unsafe = null;
        try {
            Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe"); // TODO: check THE_ONE for Android as well
            f.setAccessible(true);
            unsafe = (sun.misc.Unsafe) f.get(null);
        } catch (Throwable e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
            assert false : e;
        }
        UNSAFE = unsafe;
    }

    public Object getSunMiscUnsafe() {
        //return UNSAFE;
        try {
            Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe"); // TODO: check THE_ONE for Android as well
            f.setAccessible(true);
            return f.get(null);
        } catch (Throwable e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
            assert false : e;
        }
        return null;
    }

}

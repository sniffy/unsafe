package tools.unsafe.spi.unsafe;

// TODO: Consider also jdk.internal.misc.Unsafe and jdk.internal.reflect.Unsafe
public interface SunMiscUnsafeServiceProvider {

    <T> T getSunMiscUnsafe();

}

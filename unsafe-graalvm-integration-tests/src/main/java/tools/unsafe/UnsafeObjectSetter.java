package tools.unsafe;

public interface UnsafeObjectSetter<T> {

    void set(sun.misc.Unsafe unsafe, T value) throws NoSuchFieldException;

}

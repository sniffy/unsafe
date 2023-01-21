package tools.unsafe.reflection;

import sun.misc.Unsafe;

public interface FieldBaseSupplier {

    Object base(Unsafe field) throws Throwable;

}

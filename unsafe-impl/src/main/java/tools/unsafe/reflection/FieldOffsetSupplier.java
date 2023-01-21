package tools.unsafe.reflection;

import sun.misc.Unsafe;

public interface FieldOffsetSupplier {

    long offset(Unsafe field) throws Throwable;

}

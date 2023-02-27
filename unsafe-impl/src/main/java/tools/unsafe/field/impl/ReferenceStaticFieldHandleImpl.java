package tools.unsafe.field.impl;

public interface ReferenceStaticFieldHandleImpl<T> extends AbstractFieldHandleImpl {

    T get();

    void set(T value);

}

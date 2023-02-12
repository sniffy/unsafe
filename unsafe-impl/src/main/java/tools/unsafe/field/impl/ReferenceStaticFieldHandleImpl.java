package tools.unsafe.field.impl;

public interface ReferenceStaticFieldHandleImpl<T> extends AbstractFieldHandleImpl {

    public abstract T get();

    public abstract void set(T value);

}

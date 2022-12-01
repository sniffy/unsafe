package io.sniffy.unsafe.reflection.object;

import io.sniffy.unsafe.reflection.UnresolvedRefException;
import io.sniffy.unsafe.reflection.UnsafeInvocationException;
import io.sniffy.unsafe.reflection.clazz.ClassRef;
import io.sniffy.unsafe.reflection.field.ObjectFieldRef;
import io.sniffy.unsafe.reflection.field.UnresolvedNonStaticFieldRef;
import io.sniffy.unsafe.reflection.method.ObjectNonVoidMethodRef;

import java.lang.reflect.InvocationTargetException;

public class ObjectRef<C> {

    private final ClassRef<C> classRef;

    private final C object;

    public ObjectRef(ClassRef<C> classRef, C object) {
        this.classRef = classRef;
        this.object = object;
    }

    public <T> T getField(String fieldName) throws UnresolvedRefException, UnsafeInvocationException {
        return this.<T>field(fieldName).get();
    }

    public <T> UnresolvedObjectRef<T> $(String fieldName) {
        UnresolvedNonStaticFieldRef<C, T> nonStaticField = classRef.<T>getNonStaticField(fieldName);
        T fieldValue = null;
        Exception exception = null;
        Class<T> fieldType = null;
        try {
            fieldValue = nonStaticField.get(object);
            //noinspection unchecked
            fieldType = (Class<T>) nonStaticField.getField().getType();
        } catch (Exception e) {
            exception = e;
        }
        return new UnresolvedObjectRef<T>(
                new ObjectRef<T>(new ClassRef<T>(fieldType), fieldValue),
                exception
        );
    }

    public <T> void setField(String fieldName, T value) throws UnresolvedRefException, UnsafeInvocationException {
        field(fieldName).set(value);
    }

    public <T> ObjectFieldRef<C,T> field(String fieldName) throws UnresolvedRefException {
        return new ObjectFieldRef<C,T>(classRef.<T>getNonStaticField(fieldName).resolve(), object);
    }

    public <T> T invoke(Class<T> returnType, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        ObjectNonVoidMethodRef<C,T> method = new ObjectNonVoidMethodRef<C,T>(classRef.getNonStaticMethod(returnType, methodName, parameterTypes).resolve(), object);
        return method.invoke(object, parameters);
    }

}

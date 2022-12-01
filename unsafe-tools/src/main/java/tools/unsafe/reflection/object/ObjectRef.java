package tools.unsafe.reflection.object;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.field.ResolvedInstanceObjectFieldRef;
import tools.unsafe.reflection.field.UnresolvedNonStaticObjectFieldRef;
import tools.unsafe.reflection.method.ObjectNonVoidMethodRef;

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
        UnresolvedNonStaticObjectFieldRef<C, T> nonStaticField = classRef.<T>getNonStaticField(fieldName);
        T fieldValue = null;
        Exception exception = null;
        Class<T> fieldType = null;
        try {
            fieldValue = nonStaticField.get(object);
            //noinspection unchecked
            fieldType = (Class<T>) fieldValue.getClass();
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

    public <T> ResolvedInstanceObjectFieldRef<C,T> field(String fieldName) throws UnresolvedRefException {
        return new ResolvedInstanceObjectFieldRef<C,T>(classRef, classRef.<T>getNonStaticField(fieldName).resolve(), object);
    }

    public <T> T invoke(Class<T> returnType, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        ObjectNonVoidMethodRef<C,T> method = new ObjectNonVoidMethodRef<C,T>(classRef.getNonStaticMethod(returnType, methodName, parameterTypes).resolve(), object);
        return method.invoke(object, parameters);
    }

}

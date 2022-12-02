package tools.unsafe.reflection.object;

import tools.unsafe.reflection.UnresolvedRefException;
import tools.unsafe.reflection.UnsafeInvocationException;
import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.field.booleans.resolved.ResolvedInstanceBooleanFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedInstanceObjectFieldRef;
import tools.unsafe.reflection.field.objects.unresolved.UnresolvedDynamicObjectFieldRef;
import tools.unsafe.reflection.method.resolved.ObjectNonVoidMethodRef;

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
        UnresolvedDynamicObjectFieldRef<C, T> nonStaticField = classRef.<T>field(fieldName);
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
        return new ResolvedInstanceObjectFieldRef<C,T>(classRef, classRef.<T>field(fieldName).resolve(), object);
    }

    public <T> ResolvedInstanceBooleanFieldRef<C> booleanField(String fieldName) throws UnresolvedRefException {
        return new ResolvedInstanceBooleanFieldRef<C>(classRef, classRef.booleanField(fieldName).resolve(), object);
    }

    public <T> T invoke(Class<T> returnType, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws UnresolvedRefException, UnsafeInvocationException, InvocationTargetException {
        ObjectNonVoidMethodRef<C,T> method = new ObjectNonVoidMethodRef<C,T>(classRef.getNonStaticMethod(returnType, methodName, parameterTypes).resolve(), object);
        return method.invoke(object, parameters);
    }

}

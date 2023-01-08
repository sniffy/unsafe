package tools.unsafe.reflection.field;

import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.field.booleans.resolved.ResolvedDynamicBooleanFieldRef;
import tools.unsafe.reflection.field.booleans.resolved.ResolvedStaticBooleanFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedDynamicObjectFieldRef;
import tools.unsafe.reflection.field.objects.resolved.ResolvedStaticObjectFieldRef;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldRefs {

    public static <C, T, R> R of(Field declaredField) {
        boolean isStatic = Modifier.isStatic(declaredField.getModifiers());
        Class<?> type = declaredField.getType();
        if (boolean.class == type || Boolean.class == type) {
            return (isStatic) ?
                    (R) new ResolvedStaticBooleanFieldRef<C>(ClassRef.<C>of((Class<C>) declaredField.getDeclaringClass()), declaredField) :
                    (R) new ResolvedDynamicBooleanFieldRef<C>(ClassRef.<C>of((Class<C>) declaredField.getDeclaringClass()), declaredField);
        } else {
            return (isStatic) ?
                    (R) new ResolvedStaticObjectFieldRef<C, T>(ClassRef.<C>of((Class<C>) declaredField.getDeclaringClass()), declaredField) :
                    (R) new ResolvedDynamicObjectFieldRef<C, T>(ClassRef.<C>of((Class<C>) declaredField.getDeclaringClass()), declaredField);
        }
    }

}

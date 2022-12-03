package tools.unsafe.fluent;

import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.field.objects.unresolved.UnresolvedStaticObjectFieldRef;
import tools.unsafe.reflection.method.voidresult.unresolved.UnresolvedStaticVoidMethodRef;
import tools.unsafe.reflection.object.ObjectRef;

import javax.annotation.Nonnull;

public class FluentClassRef<C> extends ClassRef<C> {

    public FluentClassRef(@Nonnull Class<C> clazz) {
        super(clazz);
    }

    public <T> UnresolvedStaticObjectFieldRef<C, T> $(@Nonnull String fieldName) {
        return super.staticField(fieldName);
        // TODO: return union of UnresolvedStaticObjectFieldRef<C, T> and UnresolvedStaticVoidMethodRef<C>
        /*
        $(Foo.class).$("bar").$("baz") - return field value of result of method invocation
         */
    }

    public ObjectRef<C> $(C object) {
        return super.objectRef(object);
    }
}

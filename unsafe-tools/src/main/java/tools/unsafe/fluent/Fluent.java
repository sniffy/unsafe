package tools.unsafe.fluent;

import tools.unsafe.reflection.clazz.ClassRef;
import tools.unsafe.reflection.clazz.UnresolvedClassRef;
import tools.unsafe.reflection.object.ObjectRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Fluent {

    // UnresolvedClassRef factory methods

    public static @Nonnull <C> UnresolvedClassRef<C> $(@Nonnull String className) {
        return UnresolvedClassRef.<C>of(className);
    }

    @Nonnull
    public static <C> UnresolvedClassRef<C> $(@Nonnull String className, @SuppressWarnings("unused") @Nullable Class<C> cast) {
        return UnresolvedClassRef.<C>of(className, cast);
    }

    // ClassRef factory methods

    // TODO: introduce caching
    @Nonnull
    public static <C> ClassRef<C> $(@Nonnull Class<C> clazz) {
        return ClassRef.<C>of(clazz);
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    public static <C, C1 extends C> ClassRef<C> $(@Nonnull Class<C1> clazz, @SuppressWarnings("unused") @Nullable Class<C> cast) {
        return ClassRef.<C, C1>of(clazz, cast);
    }

    // ObjectRef factory methods

    public static <C> ObjectRef<C> $(@Nonnull C object) {
        return ObjectRef.<C>of(object);
    }

}

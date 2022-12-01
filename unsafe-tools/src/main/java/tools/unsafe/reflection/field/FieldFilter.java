package tools.unsafe.reflection.field;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public interface FieldFilter {

    boolean include(@Nonnull String name, @Nonnull Field field);

}

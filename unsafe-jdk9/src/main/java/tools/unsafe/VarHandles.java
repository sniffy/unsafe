package tools.unsafe;

import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;

public final class VarHandles {

    private VarHandles() {
    }

    private static VarHandle enableFinalAccess(Field field) throws Exception {
        Reflections.setAccessible(field);
        Reflections.removeFinalModifier(field);
        return Lookups.trustedLookup().unreflectVarHandle(field);
        // TODO: fallback to privateLookupIn (?)
    }

}

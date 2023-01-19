package tools.unsafe.spi.reflection;

import tools.unsafe.internal.UnsafeToolsLogging;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class SimpleReflectionServiceProvider implements ReflectionServiceProvider {

    private final Field modifiersField;

    public SimpleReflectionServiceProvider() {
        Field modifiersField = null;
        try {
            modifiersField = Field.class.getDeclaredField("modifiers");
            makeAccessible(modifiersField);
        } catch (NoSuchFieldException e) {
            // TODO: this is Android case; use if VirtualMachine.isAndroid() instead
            try {
                //noinspection JavaReflectionMemberAccess
                modifiersField = Field.class.getDeclaredField("accessFlags");
                makeAccessible(modifiersField);
            } catch (Exception e1) {
                if (UnsafeToolsLogging.stdErrEnabled()) {
                    e.printStackTrace();
                }
                if (UnsafeToolsLogging.stdErrEnabled()) {
                    e1.printStackTrace();
                }
            }
        } catch (Exception e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
        }
        this.modifiersField = modifiersField;
    }

    @Override
    public boolean makeAccessible(AccessibleObject ao) {

        //noinspection RedundantSuppression
        {
            //noinspection deprecation
            if (ao.isAccessible()) {
                return true;
            }
        }

        try {
            ao.setAccessible(true);
        } catch (Throwable e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
        }

        //noinspection RedundantSuppression
        {
            //noinspection deprecation
            return ao.isAccessible();
        }

    }

    @Override
    public boolean removeFinalModifier(Field field) {
        if (Modifier.isFinal(field.getModifiers()) /*&& Java.versionWithFallback(8) < 16*/) {
            try {
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            } catch (Throwable e) {
                if (UnsafeToolsLogging.stdErrEnabled()) {
                    e.printStackTrace();
                }
            }
        }

        // TODO: might require flushing cache

        return !Modifier.isFinal(field.getModifiers());
    }
}

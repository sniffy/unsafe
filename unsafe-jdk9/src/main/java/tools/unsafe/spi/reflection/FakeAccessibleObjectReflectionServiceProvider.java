package tools.unsafe.spi.reflection;

import tools.unsafe.Exceptions;
import tools.unsafe.Java;
import tools.unsafe.Unsafe;
import tools.unsafe.internal.UnsafeToolsLogging;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FakeAccessibleObjectReflectionServiceProvider implements ReflectionServiceProvider {

    private final Field modifiersField;

    public FakeAccessibleObjectReflectionServiceProvider() {
        Field modifiersField = null;
        try {
            //modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField = getModifiersField();
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

        if (Java.versionWithFallback(8) >= 16) {

            try {
                long overrideOffset = Unsafe.getSunMiscUnsafe().objectFieldOffset(FakeAccessibleObject.class.getDeclaredField("override"));
                Unsafe.getSunMiscUnsafe().putBoolean(ao, overrideOffset, true);
                System.out.println("Making " + ao + " accessible via Unsafe and FakeAccessibleObject");
            } catch (NoSuchFieldException e) {
                throw Exceptions.throwException(e);
            }

            //noinspection RedundantSuppression
            {
                //noinspection deprecation
                return ao.isAccessible();
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


    private Field getModifiersField() throws NoSuchFieldException {
        try {
            return Field.class.getDeclaredField("modifiers");
        } catch (NoSuchFieldException e) {
            try {
                Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
                makeAccessible(getDeclaredFields0);
                Field[] fields = (Field[]) getDeclaredFields0.invoke(Field.class, false);
                for (Field field : fields) {
                    if ("modifiers".equals(field.getName())) {
                        return field;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw Exceptions.throwException(e);
            }
            throw e;
        }
    }

}


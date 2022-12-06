package tools.unsafe;

import sun.misc.Unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * <a href="https://stackoverflow.com/questions/48616630/is-it-possible-to-call-constructor-on-existing-instance">See stackoverflow discussion</a>
 */
public class MethodHandleConstructorInvokerFactoryImpl implements ConstructorInvokerFactory {


    @Override
    public <C> ConstructorInvoker<C> createConstructorInvoker(Class<C> clazz, Class<?>... parameterTypes) throws InternalUnsafeException {
        try {
            /*
             * Step 0: invoke MethodHandles.publicLookup() and ignore result
             * If not done, on certain JVMs the IMPL_LOOKUP field below might be null
             */
            //noinspection ResultOfMethodCallIgnored
            MethodHandles.publicLookup();

            /*
             * Step 1: Obtaining a trusted MethodHandles.Lookup
             * Next, a java.lang.invoke.MethodHandles$Lookup is needed to get the actual method handle for the constructor.
             * This class has a permission system which works through the allowedModes property in Lookup, which is set to a bunch of Flags. There is a special TRUSTED flag that circumvents all permission checks.
             * Unfortunately, the allowedModes field is filtered from reflection, so we cannot simply bypass the permissions by setting that value through reflection.
             * Even though reflection filters can be circumvented as well, there is a simpler way: Lookup contains a static field IMPL_LOOKUP, which holds a Lookup with those TRUSTED permissions. We can get this instance by using reflection and Unsafe:
             */
            Unsafe unsafe = InternalUnsafe.getSunMiscUnsafe();

            Field field = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            long fieldOffset = unsafe.staticFieldOffset(field);
            MethodHandles.Lookup implLookup = (MethodHandles.Lookup) unsafe.getObject(MethodHandles.Lookup.class, fieldOffset);


            MethodType constructorMethodType = MethodType.methodType(Void.TYPE, parameterTypes);
            MethodHandle constructor = implLookup.findConstructor(clazz, constructorMethodType);

            Class<?> constructorClass = Class.forName("java.lang.invoke.DirectMethodHandle$Constructor");
            Field initMethodField = constructorClass.getDeclaredField("initMethod");
            long initMethodFieldOffset = unsafe.objectFieldOffset(initMethodField);
            Object initMemberName = unsafe.getObject(constructor, initMethodFieldOffset);

            Class<?> memberNameClass = Class.forName("java.lang.invoke.MemberName");
            Field flagsField = memberNameClass.getDeclaredField("flags");
            long flagsFieldOffset = unsafe.objectFieldOffset(flagsField);
            int flags = unsafe.getInt(initMemberName, flagsFieldOffset);

            flags &= ~0x00020000; // remove "is constructor"
            flags |= 0x00010000; // add "is (non-constructor) method"

            unsafe.putInt(initMemberName, flagsFieldOffset, flags);

            MethodHandle handle;

            if (InternalUnsafe.tryGetJavaVersion() > 8) {
                //noinspection JavaLangInvokeHandleSignature
                MethodHandle getDirectMethodHandle = implLookup.findVirtual(
                        MethodHandles.Lookup.class,
                        "getDirectMethod",
                        MethodType.methodType(
                                MethodHandle.class,
                                byte.class,
                                Class.class,
                                Class.forName("java.lang.invoke.MemberName"),
                                MethodHandles.Lookup.class
                        )
                );

                handle = (MethodHandle) getDirectMethodHandle.invoke(implLookup, (byte) 5, clazz, initMemberName, implLookup);

            } else //noinspection RedundantSuppression
            {
                //noinspection JavaLangInvokeHandleSignature
                MethodHandle getDirectMethodHandle = implLookup.findVirtual(
                        MethodHandles.Lookup.class,
                        "getDirectMethod",
                        MethodType.methodType(
                                MethodHandle.class,
                                byte.class,
                                Class.class,
                                Class.forName("java.lang.invoke.MemberName"),
                                Class.class
                        )
                );

                handle = (MethodHandle) getDirectMethodHandle.invoke(implLookup, (byte) 5, clazz, initMemberName, MethodHandles.class);

            }

            return new MethodHandleConstructorInvokerImpl<>(handle);
        } catch (NoSuchFieldException e) {
            throw new InternalUnsafeException(e);
        } catch (ClassNotFoundException e) {
            throw new InternalUnsafeException(e);
        } catch (NoSuchMethodException e) {
            throw new InternalUnsafeException(e);
        } catch (IllegalAccessException e) {
            throw new InternalUnsafeException(e);
        } catch (Throwable e) {
            throw new InternalUnsafeException(e);
        }

    }

}

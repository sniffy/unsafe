package tools.unsafe.reflection.ng;

public abstract class ExecutableRef {

    public abstract Class<?>[] getParameterTypes();

    public void validateParameterTypes(Class<?>... actualParameterTypes) {
        // TODO
    }

}

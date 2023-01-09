package tools.unsafe.ng;

public abstract class ExecutableRef {

    public abstract Class<?>[] getParameterTypes();

    public void validateParameterTypes(Class<?>... actualParameterTypes) {
        // TODO
    }

}

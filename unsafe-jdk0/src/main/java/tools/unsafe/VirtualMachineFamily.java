package tools.unsafe;

public class VirtualMachineFamily {

    public final static VirtualMachineFamily GRAALVM_NATIVE = new VirtualMachineFamily();
    public final static VirtualMachineFamily ANDROID = new VirtualMachineFamily();
    public final static VirtualMachineFamily J9 = new VirtualMachineFamily();
    public final static VirtualMachineFamily HOTSPOT = new VirtualMachineFamily();

    private VirtualMachineFamily() {

    }

}
package tools.unsafe.internal;

public class UnsafeToolsLogging {

    public final static String UNSAFE_TOOLS_SYSTEM_PROPERTY_STDERR_LOGGING_ENABLED = "tools.unsafe.stderr";

    public static boolean stdErrEnabled() {
        try {
            String stderr = System.getProperty(UNSAFE_TOOLS_SYSTEM_PROPERTY_STDERR_LOGGING_ENABLED);
            if (null == stderr) {
                return true; // stderr is enabled by default - ideally we shouldn't generate any output
            } else {
                return stderr.equalsIgnoreCase("true");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return true;
        }
    }


}

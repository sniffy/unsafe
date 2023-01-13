package tools.unsafe;

import tools.unsafe.spi.VintageServiceProviders;
import tools.unsafe.spi.assertion.AssertionServiceProvider;

public final class Java {

    private Java() {

    }

    // TODO: can we return something like enum here with Java Virtual Machine Family ?

    public static int version() {
        String version = System.getProperty("java.version");

        if (null == version) {
            return -1;
        }

        if (version.startsWith("1.")) {
            if (version.length() >= 3) {
                version = version.substring(2, 3);
            } else {
                return -1;
            }
        } else {
            int dot = version.indexOf('.');
            if (dot != -1) {
                version = version.substring(0, dot);
            }
        }

        int dash = version.indexOf('-');
        if (dash != -1) {
            version = version.substring(0, dash);
        }

        try {
            return Integer.parseInt(version);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static int versionWithFallback(int fallbackJavaVersion) {
        int version = version();
        if (version == -1) {
            return fallbackJavaVersion;
        } else {
            return version;
        }
    }

    public static boolean assertionsEnabled() {
        VintageServiceProviders serviceProviders = VintageServiceProviders.getInstance();
        if (null == serviceProviders) {
            return false;
        } else {
            AssertionServiceProvider assertionServiceProvider = serviceProviders.getAssertionServiceProvider();
            if (null == assertionServiceProvider) {
                return false;
            } else {
                return assertionServiceProvider.assertionsEnabled();
            }
        }
    }

}

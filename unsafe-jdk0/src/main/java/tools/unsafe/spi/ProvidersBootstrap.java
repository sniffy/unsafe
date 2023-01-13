package tools.unsafe.spi;

import tools.unsafe.Java;
import tools.unsafe.internal.UnsafeToolsLogging;

public class ProvidersBootstrap {

    static {
        registerProvidersImpl();
    }

    private ProvidersBootstrap() {

    }

    public static void registerProviders() {

    }

    private static void registerProvidersImpl() {
        try {

            Java0Providers.registerProviders();

            int version = Java.versionWithFallback(0);

            for (int i = 1; i <= version; i++) {
                switch (i) {
                    case 9:
                        try {
                            Java9Providers.registerProviders();
                        } catch (Throwable e) {
                            if (UnsafeToolsLogging.stdErrEnabled()) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 8:
                        try {
                            Java8Providers.registerProviders();
                        } catch (Throwable e) {
                            if (UnsafeToolsLogging.stdErrEnabled()) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 7:
                        try {
                            Java7Providers.registerProviders();
                        } catch (Throwable e) {
                            if (UnsafeToolsLogging.stdErrEnabled()) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 6:
                        try {
                            Java6Providers.registerProviders();
                        } catch (Throwable e) {
                            if (UnsafeToolsLogging.stdErrEnabled()) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 5:
                        try {
                            Java5Providers.registerProviders();
                        } catch (Throwable e) {
                            if (UnsafeToolsLogging.stdErrEnabled()) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 4:
                        try {
                            Java4Providers.registerProviders();
                        } catch (Throwable e) {
                            if (UnsafeToolsLogging.stdErrEnabled()) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 3:
                        try {
                            Java3Providers.registerProviders();
                        } catch (Throwable e) {
                            if (UnsafeToolsLogging.stdErrEnabled()) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 2:
                        try {
                            Java2Providers.registerProviders();
                        } catch (Throwable e) {
                            if (UnsafeToolsLogging.stdErrEnabled()) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 1:
                        try {
                            Java1Providers.registerProviders();
                        } catch (Throwable e) {
                            if (UnsafeToolsLogging.stdErrEnabled()) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }

        } catch (Throwable e) {
            if (UnsafeToolsLogging.stdErrEnabled()) {
                e.printStackTrace();
            }
        }

    }

}

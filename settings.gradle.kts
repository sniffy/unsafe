pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "unsafe-parent"

include("unsafe-impl")
include("unsafe-compatibility-kit")
include("unsafe-graalvm-integration-tests")
include("unsafe-android-integration-tests")
include("unsafe-ikvm-integration-tests")
// TODO: add IKVM.NET


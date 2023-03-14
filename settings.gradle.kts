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

val ci = System.getenv().containsKey("CI")
val graalVmTest = startParameter.projectProperties.containsKey("graalVmTest")
val androidTest = startParameter.projectProperties.containsKey("androidTest")
val dotNetTest = startParameter.projectProperties.containsKey("dotNetTest")

if (ci) {
    if (graalVmTest) {
        include("unsafe-graalvm-integration-tests")
    }
    if (androidTest) {
        include("unsafe-android-integration-tests")
    }
    if (dotNetTest) {
        include("unsafe-ikvm-integration-tests")
    }
} else {
    include("unsafe-graalvm-integration-tests")
    include("unsafe-android-integration-tests")
    include("unsafe-ikvm-integration-tests")
}
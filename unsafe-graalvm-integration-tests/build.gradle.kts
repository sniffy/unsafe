plugins {
    `java-library`
    //application

    id("org.graalvm.buildtools.native") version "0.9.18"
}

graalvmNative {
    binaries.all {
        resources.autodetect()
    }
    toolchainDetection.set(false)
}

tasks.test {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}


dependencies {
    implementation(group = "com.google.code.findbugs", name = "jsr305", version = "3.0.2")
    implementation(group = "org.ow2.asm", name = "asm-util", version = "9.4")
    implementation(group = "org.ow2.asm", name = "asm", version = "9.4")
    compileOnly(group = "org.graalvm.nativeimage", name = "svm", version = "20.2.0")

    testImplementation(group = "junit", name = "junit", version = "4.13.2")

    implementation(project(":unsafe-compatibility-kit"))
    implementation(project(":unsafe-impl"))
    //runtime()
}

/*application {
    // Define the main class for the application.
    mainClass.set("tools.unsafe.MainClass")
}*/


/*
plugins {
    id("java")
}

group = "io.sniffy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}*/

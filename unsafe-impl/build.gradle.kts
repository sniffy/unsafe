plugins {
    `java-library`
}

dependencies {
    implementation(group = "com.google.code.findbugs", name = "jsr305", version = "3.0.2")
}
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

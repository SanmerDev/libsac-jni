plugins {
    kotlin("jvm") version "1.9.10"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.1")
    testImplementation(kotlin("test", version = "1.9.10"))
    testImplementation(project(":jni", configuration = "darwin-x86_64-debug"))
}

tasks.test {
    useJUnitPlatform()
}

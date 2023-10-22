@file:Suppress("UnstableApiUsage")

rootProject.name = "libsac-jni"

include("kotlin")
include("java")
include("jni")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

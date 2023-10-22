@file:Suppress("UnstableApiUsage")

rootProject.name = "libsac-jni"

include("base")
include("jni")
include("java")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

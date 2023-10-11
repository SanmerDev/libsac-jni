@file:Suppress("UnstableApiUsage")

rootProject.name = "libsac-jni"

include("base")
include("jni")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

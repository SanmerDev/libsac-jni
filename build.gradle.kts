plugins {
    kotlin("multiplatform") version "1.9.10"
}

val customTarget = Attribute.of("customTarget", String::class.java)

kotlin {
    jvmToolchain(17)

    jvm("android") {
        attributes {
            attribute(customTarget, "android")
        }
    }

    jvm("darwin") {
        attributes {
            attribute(customTarget, "darwin")
        }
    }

    jvm("linux") {
        attributes {
            attribute(customTarget, "linux")
        }
    }

    jvm("mingw") {
        attributes {
            attribute(customTarget, "mingw")
        }
    }

    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test", version = "1.9.10"))
            }
        }

        named("darwinTest") {
            dependsOn(commonTest.get())
            dependencies {
                implementation(project(":runtime", configuration = "darwin"))
            }
        }
    }
}
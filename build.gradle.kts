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
        commonMain {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

        named("darwinTest") {
            dependencies {
                implementation(project(":runtime", configuration = "darwin"))
            }
        }

        named("linuxTest") {
            dependencies {
                implementation(project(":runtime", configuration = "linux"))
            }
        }

        named("mingwTest") {
            dependencies {
                implementation(project(":runtime", configuration = "mingw"))
            }
        }
    }
}
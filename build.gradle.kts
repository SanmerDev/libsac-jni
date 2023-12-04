plugins {
    kotlin("multiplatform") version "1.9.20"
}

val customTarget = Attribute.of("customTarget", String::class.java)

kotlin {
    jvmToolchain(17)

    jvm("android") {
        attributes {
            attribute(customTarget, name)
        }
    }

    jvm("darwin") {
        attributes {
            attribute(customTarget, name)
        }
    }

    jvm("linux") {
        attributes {
            attribute(customTarget, name)
        }
    }

    jvm("mingw") {
        attributes {
            attribute(customTarget, name)
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
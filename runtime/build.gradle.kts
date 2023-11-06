fun configureTarget(id: String, fileName: String, bundleName: String) {
    val name = id[0].uppercase() + id.substring(1)

    val compileTask = tasks.register<Exec>("compileRust$name") {
        group = "build"

        inputs.dir(file("native/src"))
        inputs.files(file("native/Cargo.toml"), file("native/Cargo.lock"))
        outputs.file(file("native/target/release/$fileName"))

        commandLine(listOf("cargo", "build", "--lib", "--release"))
        workingDir(file("native"))
    }

    val jar = tasks.register<Jar>("bundleJar$name") {
        val buildDir = file(layout.buildDirectory)
        destinationDirectory.set(buildDir.resolve("jars"))
        archiveBaseName.set("${rootProject.name}-runtime-${id}")
        entryCompression = ZipEntryCompression.DEFLATED
        isPreserveFileTimestamps = false

        from(compileTask) {
            into("/")
            rename { bundleName }
        }
    }

    configurations.register(id) {
        isCanBeConsumed = true
        isCanBeResolved = false
    }

    artifacts.add(id, jar)
}

configureTarget("darwin", "libjni.dylib", "libsac-jni.dylib")
configureTarget("linux", "libjni.so", "libsac-jni.so")
configureTarget("mingw", "libjni.dll", "libsac-jni.dll")

task<Delete>("clean") {
    group = "build"

    delete(layout.buildDirectory)
    delete("native/target")
}
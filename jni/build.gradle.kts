fun configureTarget(id: String, debug: Boolean, fileName: String, bundleName: String) {
    val compileTask = tasks.register("compileRust[$id]", type = Exec::class) {
        group = "build"

        inputs.dir(file("crate/src"))
        inputs.files(file("crate/Cargo.toml"), file("crate/Cargo.lock"), file("crate/Cross.toml"))
        outputs.file(file("crate/target/${if (debug) "debug" else "release"}/$fileName"))

        commandLine(listOf("cargo", "build", "--lib") + if (!debug) listOf("--release") else emptyList())
        workingDir(file("crate"))
    }

    val jar = tasks.register("bundleJar[$id]", type = Jar::class) {
        val buildDir = file(layout.buildDirectory)
        destinationDirectory.set(buildDir.resolve("jars"))
        archiveBaseName.set(id)
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

configureTarget("darwin-x86_64", false, "libjni.dylib", "libsac-jni.dylib")
configureTarget("darwin-x86_64-debug", true, "libjni.dylib", "libsac-jni.dylib")

task("clean", type = Delete::class) {
    group = "build"

    delete(layout.buildDirectory)
    delete("crate/target")
}
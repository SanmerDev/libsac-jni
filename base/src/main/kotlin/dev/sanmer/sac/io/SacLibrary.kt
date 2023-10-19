package dev.sanmer.sac.io

import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files


object SacLibrary {
    private const val LIBRARY_NAME = "libsac-jni.dylib"

    fun load() {
        val libraryFile = getLibraryFile()
        System.load(libraryFile)
    }

    private fun getLibraryFile(): String {
        if (System.getenv("SAC_JNI_LIBRARY_PATH") != null) {
            return System.getenv("SAC_JNI_LIBRARY_PATH")
        }

        val libraryURL = checkNotNull(javaClass.getResource("/${LIBRARY_NAME}"))
        if (libraryURL.protocol != "jar") {
            throw LinkageError("Load $LIBRARY_NAME")
        }

        val libsDir = Files.createTempDirectory("sac-jni").toFile()
        libsDir.deleteOnExit()

        val libraryFile = File(libsDir, LIBRARY_NAME)
        libraryFile.deleteOnExit()

        val inputStream = libraryURL.openStream()
        val outputStream = FileOutputStream(libraryFile)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return libraryFile.absolutePath
    }
}

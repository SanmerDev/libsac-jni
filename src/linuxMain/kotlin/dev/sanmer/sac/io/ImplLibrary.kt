package dev.sanmer.sac.io

import dev.sanmer.sac.io.Library.Companion.getLibrary

internal actual object ImplLibrary: Library {
    actual override val name = "libsac-jni.so"

    actual override fun load() {
        val library = getLibrary()
        System.load(library)
    }
}
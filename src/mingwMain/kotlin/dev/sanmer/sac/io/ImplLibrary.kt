package dev.sanmer.sac.io

import dev.sanmer.sac.io.Library.Companion.getLibrary

internal actual object ImplLibrary: Library {
    actual override val name = "sac-jni.dll"

    actual override fun load() {
        val library = getLibrary()
        System.load(library)
    }
}
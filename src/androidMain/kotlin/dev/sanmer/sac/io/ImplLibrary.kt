package dev.sanmer.sac.io

internal actual object ImplLibrary: Library {
    actual override val name = "sac-jni"

    actual override fun load() {
        System.loadLibrary(name)
    }
}
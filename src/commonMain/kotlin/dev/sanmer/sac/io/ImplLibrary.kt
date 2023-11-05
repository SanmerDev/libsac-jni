package dev.sanmer.sac.io


internal expect object ImplLibrary: Library {
    override val name: String

    override fun load()
}

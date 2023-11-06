# libsac-jni
Kotlin bindings for Rust library [libsac](https://github.com/SanmerDev/libsac)

## demo
```kotlin
import dev.sanmer.sac.io.Endian
import dev.sanmer.sac.io.Sac
import java.io.File

fun main() {
    val file = File("test.sac")

    Sac.readHeader(file, Endian.Little).use {
        val h = it.header
        h.t[0] = 10.0f
        h.kt[0] = "P"
        h.kstnm = "VDC"

        it.header = h
        it.writeHeader()
    }

    val sac = Sac.read(file, Endian.Little)
    val h = sac.header
    val y = sac.first

    println("t0 = ${h.t[0]}")
    println("kt0 = ${h.kt[0]}")
    println("kstnm = ${h.kstnm}")

    println("y.first = ${y.first()}")
    println("y.last = ${y.last()}")
    println("y.size = ${y.size}")

    sac.close()
}
```
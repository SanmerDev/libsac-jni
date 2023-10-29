# libsac-jni
Java bindings for Rust library [libsac](https://github.com/SanmerDev/libsac)

## java
```java
import dev.sanmer.sac.io.Endian;
import dev.sanmer.sac.io.Sac;
import dev.sanmer.sac.io.SacHeader;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("src/test/resources/test.sac");

        try (Sac sac = Sac.readHeader(file, Endian.Little)) {
            SacHeader h = sac.getHeader();
            h.getT()[0] = 10.0f;
            h.getKt()[0] = "P";
            h.setKstnm("VDC");

            sac.setHeader(h);
            sac.writeHeader();
        }

        Sac sac = Sac.read(file, Endian.Little);
        SacHeader h = sac.getHeader();
        float[] y = sac.getFirst();

        System.out.println("t0 = " + h.getT()[0]);
        System.out.println("kt0 = " + h.getKt()[0]);
        System.out.println("kstnm = " + h.getKstnm());

        System.out.println("y.first = " + y[0]);
        System.out.println("y.last = " + y[y.length - 1]);
        System.out.println("y.size = " + y.length);

        sac.close();
    }
}
```
## kotlin
```kotlin
import dev.sanmer.sac.io.Endian
import dev.sanmer.sac.io.Sac
import java.io.File

fun main() {
    val file = File("src/test/resources/test.sac")

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
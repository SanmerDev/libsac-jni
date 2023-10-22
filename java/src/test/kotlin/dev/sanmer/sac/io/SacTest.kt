package dev.sanmer.sac.io

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class SacTest {
    private val file = File("src/test/resources/test.sac")

    @Test
    fun test_read() {
        val sac = Sac.read(file, Endian.Little)
        val h = sac.h
        val y = sac.y

        assertEquals(h.delta, 0.01f)
        assertEquals(h.npts, 1000)
        assertEquals(h.kstnm, "CDV")

        assertEquals(y.first(), -0.09728001f)
        assertEquals(y.last(), -0.07680000f)
        assertEquals(y.size, h.npts)

        sac.close()
    }

    @Test
    fun test_readHeader() {
        val sac = Sac.readHeader(file, Endian.Little)
        val h1 = sac.h
        val y = sac.y

        assertEquals(h1.delta, 0.01f)
        assertEquals(h1.npts, 1000)
        assertEquals(h1.kstnm, "CDV")

        assertEquals(y.firstOrNull(), null)
        assertEquals(y.lastOrNull(), null)
        assertEquals(y.size, 0)

        sac.close()
    }

    @Test
    fun test_write() {
        val fileT = File("src/test/resources/test_t.sac")

        Sac.read(file, Endian.Little).use {
            it.setEndian(Endian.Big)
            it.writeTo(fileT)
        }

        val sac = Sac.read(fileT, Endian.Big)
        val h = sac.h
        val y = sac.y

        assertEquals(h.delta, 0.01f)
        assertEquals(h.npts, 1000)
        assertEquals(h.kstnm, "CDV")

        assertEquals(y.first(), -0.09728001f)
        assertEquals(y.last(), -0.07680000f)
        assertEquals(y.size, h.npts)

        sac.close()
        fileT.delete()
    }

    @Test
    fun test_writeHeader() {
        val fileH = File("src/test/resources/test_h.sac")
        file.copyTo(fileH, overwrite = true)

        Sac.readHeader(fileH, Endian.Little).use {
            val h = it.h
            h.t[0] = 10.0f
            h.kt[0] = "P"
            h.kstnm = "VDC"

            it.h = h
            it.writeHeader()
        }

        val sac = Sac.read(fileH, Endian.Little)
        val h = sac.h
        val y = sac.y

        assertEquals(h.t[0], 10.0f)
        assertEquals(h.kt[0], "P")
        assertEquals(h.kstnm, "VDC")

        assertEquals(y.first(), -0.09728001f)
        assertEquals(y.last(), -0.07680000f)
        assertEquals(y.size, h.npts)

        sac.close()
        fileH.delete()
    }
}
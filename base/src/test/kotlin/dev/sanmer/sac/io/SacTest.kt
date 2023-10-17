package dev.sanmer.sac.io

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class SacTest {
    private val file = File("src/test/resources/test.sac")

    @Test
    fun test_read() {
        val (h, _, y) = Sac.read(
            file = file,
            endian = Endian.Little
        ).values

        assertEquals(h.delta, 0.01f)
        assertEquals(h.npts, 1000)
        assertEquals(h.kstnm, "CDV")

        assertEquals(y.first(), -0.09728001f)
        assertEquals(y.last(), -0.07680000f)
        assertEquals(y.size, h.npts)
    }

    @Test
    fun test_readHeader() {
        val h0 = SacHeader.read(
            file = file,
            endian = Endian.Little
        )

        assertEquals(h0.delta, 0.01f)
        assertEquals(h0.t[0], -12345f)
        assertEquals(h0.npts, 1000)
        assertEquals(h0.leven, true)
        assertEquals(h0.kt[0], "-12345")
        assertEquals(h0.kstnm, "CDV")

        val (h1, _, y) = Sac.readHeader(
            file = file,
            endian = Endian.Little
        ).values

        assertEquals(h1.delta, 0.01f)
        assertEquals(h1.npts, 1000)
        assertEquals(h1.kstnm, "CDV")

        assertEquals(y.firstOrNull(), null)
        assertEquals(y.lastOrNull(), null)
        assertEquals(y.size, 0)
    }

    @Test
    fun test_write() {
        val fileT = File("src/test/resources/test_t.sac")

        Sac.read(
            file = file,
            endian = Endian.Little
        ).use {
            it.setEndian(Endian.Big)
            it.writeTo(fileT)
        }

        val (h, _, y) = Sac.read(
            file = fileT,
            endian = Endian.Big
        ).values

        assertEquals(h.delta, 0.01f)
        assertEquals(h.npts, 1000)
        assertEquals(h.kstnm, "CDV")

        assertEquals(y.first(), -0.09728001f)
        assertEquals(y.last(), -0.07680000f)
        assertEquals(y.size, h.npts)

        fileT.delete()
    }

    @Test
    fun test_writeHeader() {
        val fileH = File("src/test/resources/test_h.sac")
        file.copyTo(fileH, overwrite = true)

        SacHeader.update(
            file = fileH,
            endian = Endian.Little
        ) {
            t[0] = 10.0f
            kt[0] = "P"
            kstnm = "VDC"
        }

        val (h, _, y) = Sac.read(
            file = fileH,
            endian = Endian.Little
        ).values

        assertEquals(h.t[0], 10.0f)
        assertEquals(h.kt[0], "P")
        assertEquals(h.kstnm, "VDC")

        assertEquals(y.first(), -0.09728001f)
        assertEquals(y.last(), -0.07680000f)
        assertEquals(y.size, h.npts)

        fileH.delete()
    }
}
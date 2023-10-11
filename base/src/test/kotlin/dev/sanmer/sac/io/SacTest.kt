package dev.sanmer.sac.io

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class SacTest {
    private val file = File("src/test/resources/test.sac")

    @Test
    fun test_read() {
        val sac = Sac.read(
            file = file,
            endian = Endian.Little
        )

        sac.use {
            val h = it.h
            val y = it.y

            assertEquals(h.delta, 0.01f)
            assertEquals(h.npts, 1000)
            assertEquals(h.kstnm, "CDV")

            assertEquals(y.first(), -0.09728001f)
            assertEquals(y.last(), -0.07680000f)
            assertEquals(y.size, h.npts)
        }
    }

    @Test
    fun test_readHeader() {
        val hdr = SacHeader.read(
            file = file,
            endian = Endian.Little
        )

        assertEquals(hdr.delta, 0.01f)
        assertEquals(hdr.t[0], -12345f)
        assertEquals(hdr.npts, 1000)
        assertEquals(hdr.leven, true)
        assertEquals(hdr.kt[0], "-12345")
        assertEquals(hdr.kstnm, "CDV")

        val sac = Sac.readHeader(
            file = file,
            endian = Endian.Little
        )

        sac.use {
            val h = it.h
            val y = it.y

            assertEquals(h.delta, 0.01f)
            assertEquals(h.npts, 1000)
            assertEquals(h.kstnm, "CDV")

            assertEquals(y.firstOrNull(), null)
            assertEquals(y.lastOrNull(), null)
            assertEquals(y.size, 0)
        }
    }

    @Test
    fun test_write() {
        val fileT = File("src/test/resources/test_t.sac")

        val sac = Sac.read(
            file = file,
            endian = Endian.Little
        )

        sac.use {
            it.setEndian(Endian.Big)
            it.writeTo(fileT)
        }

        val sacT = Sac.read(
            file = fileT,
            endian = Endian.Big
        )

        sacT.use {
            val h = it.h
            val y = it.y

            assertEquals(h.delta, 0.01f)
            assertEquals(h.npts, 1000)
            assertEquals(h.kstnm, "CDV")

            assertEquals(y.first(), -0.09728001f)
            assertEquals(y.last(), -0.07680000f)
            assertEquals(y.size, h.npts)
        }

        fileT.delete()
    }

    @Test
    fun test_writeHeader() {
        val fileH = File("src/test/resources/test_h.sac")
        file.copyTo(fileH, overwrite = true)

        val sac = Sac.readHeader(
            file = fileH,
            endian = Endian.Little
        )

        sac.use {
            val newH = it.h

            newH.t[0] = 10.0f
            newH.kt[0] = "P"
            newH.kstnm = "VDC"

            it.h = newH
            it.writeHeader()
        }

        val sacN = Sac.read(
            file = fileH,
            endian = Endian.Little
        )

        sacN.use {
            val h = it.h
            val y = it.y

            assertEquals(h.t[0], 10.0f)
            assertEquals(h.kt[0], "P")
            assertEquals(h.kstnm, "VDC")

            assertEquals(y.first(), -0.09728001f)
            assertEquals(y.last(), -0.07680000f)
            assertEquals(y.size, h.npts)
        }

        fileH.delete()
    }
}
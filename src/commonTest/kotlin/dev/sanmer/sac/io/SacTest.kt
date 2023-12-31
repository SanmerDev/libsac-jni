package dev.sanmer.sac.io

import org.junit.Assert.assertEquals
import java.io.File
import kotlin.test.Test

class SacTest {
    private val file = File("src/commonTest/resources/test.sac")

    @Test
    fun read() {
        val sac = Sac.read(file, Endian.Little)
        val h = sac.header
        val y = sac.first
        val fileType = SacFileType.valueBy(h.iftype)

        assertEquals(h.delta, 0.01f)
        assertEquals(h.npts, 1000)
        assertEquals(h.kstnm, "CDV")
        assertEquals(fileType, SacFileType.Time)

        assertEquals(y.first(), -0.09728001f)
        assertEquals(y.last(), -0.07680000f)
        assertEquals(y.size, h.npts)

        sac.close()
    }

    @Test
    fun readHeader() {
        val h0 = SacHeader.read(file, Endian.Little)

        assertEquals(h0.delta, 0.01f)
        assertEquals(h0.t[0], -12345f)
        assertEquals(h0.npts, 1000)
        assertEquals(h0.leven, true)
        assertEquals(h0.kt[0], "-12345")
        assertEquals(h0.kstnm, "CDV")

        val sac = Sac.readHeader(file, Endian.Little)
        val h1 = sac.header
        val y = sac.first

        assertEquals(h1.delta, 0.01f)
        assertEquals(h1.npts, 1000)
        assertEquals(h1.kstnm, "CDV")

        assertEquals(y.firstOrNull(), null)
        assertEquals(y.lastOrNull(), null)
        assertEquals(y.size, 0)

        sac.close()
    }

    @Test
    fun write() {
        val fileT = File("src/commonTest/resources/test_t.sac")

        Sac.read(file, Endian.Little).use {
            it.setEndian(Endian.Big)
            it.writeTo(fileT)
        }

        val sac = Sac.read(fileT, Endian.Big)
        val h = sac.header
        val y = sac.first

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
    fun writeHeader() {
        val fileH = File("src/commonTest/resources/test_h.sac")
        file.copyTo(fileH)

        Sac.readHeader(fileH, Endian.Little).use {
            val h = it.header
            h.t[0] = 10.0f
            h.kt[0] = "P"
            h.kstnm = "VDC"

            it.header = h
            it.writeHeader()
        }

        val sac = Sac.read(fileH, Endian.Little)
        val h = sac.header
        val y = sac.first

        assertEquals(h.t[0], 10.0f)
        assertEquals(h.kt[0], "P")
        assertEquals(h.kstnm, "VDC")

        assertEquals(y.first(), -0.09728001f)
        assertEquals(y.last(), -0.07680000f)
        assertEquals(y.size, h.npts)

        sac.close()
        fileH.delete()
    }

    @Test
    fun empty() {
        val fileN = File("src/commonTest/resources/test_new.sac")
        Sac.empty(fileN, Endian.Little).use {
            val h = it.header
            h.iftype = SacFileType.Time.iftype

            it.header = h
            it.write()
        }

        val sac = Sac.read(fileN, Endian.Little)
        val h1 = sac.header
        val y = sac.first

        assertEquals(h1.delta, -12345f)
        assertEquals(h1.npts, 0)
        assertEquals(h1.kstnm, "-12345")

        assertEquals(y.firstOrNull(), null)
        assertEquals(y.lastOrNull(), null)
        assertEquals(y.size, 0)

        sac.close()
        fileN.delete()
    }
}
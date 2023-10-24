package dev.sanmer.sac.io;

import java.io.Closeable;
import java.io.File;

public class Sac implements Closeable {
    private final long ptr;

    public Sac(long ptr) {
        this.ptr = ptr;
    }

    public SacHeader getH() {
        return getHeader(ptr);
    }

    public void setH(SacHeader value) {
        setHeader(ptr, value);
    }

    public float[] getX() {
        return getX(ptr);
    }

    public void setX(float[] value) {
        setX(ptr, value);
    }

    public float[] getY() {
        return getY(ptr);
    }

    public void setY(float[] value) {
        setY(ptr, value);
    }

    public void writeHeader() {
        writeHeader(ptr);
    }

    public void setEndian(Endian endian) {
        setEndian(ptr, endian.ordinal());
    }

    public void write() {
        write(ptr);
    }

    public void writeTo(File file) {
        writeTo(ptr, file.getAbsolutePath());
    }

    @Override
    public void close() {
        drop(ptr);
    }

    static {
        SacLibrary.load();
    }

    private static native long readHeader(String path, int endian);

    private static native long read(String path, int endian);

    private static native void writeHeader(long ptr);

    private static native void write(long ptr);

    private static native void writeTo(long ptr, String path);

    private static native SacHeader getHeader(long ptr);

    private static native void setHeader(long ptr, SacHeader h);

    private static native float[] getX(long ptr);

    private static native void setX(long ptr, float[] x);

    private static native float[] getY(long ptr);

    private static native void setY(long ptr, float[] y);

    private static native void setEndian(long ptr, int endian);

    private static native void drop(long ptr);

    public static Sac readHeader(File file, Endian endian) {
        long ptr = readHeader(file.getAbsolutePath(), endian.ordinal());
        return new Sac(ptr);
    }

    public static Sac read(File file, Endian endian) {
        long ptr = read(file.getAbsolutePath(), endian.ordinal());
        return new Sac(ptr);
    }
}

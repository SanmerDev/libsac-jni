package dev.sanmer.sac.io;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Library {
    private static final String LIBRARY_NAME = "libsac-jni.dylib";

    public static void load() {
        String libraryFile = getLibraryFile();
        System.load(libraryFile);
    }

    private static String getLibraryFile() {
        String sacJniLibraryPath = System.getenv("SAC_JNI_LIBRARY_PATH");
        if (sacJniLibraryPath != null) {
            return sacJniLibraryPath;
        }

        URL libraryURL = Library.class.getResource("/" + LIBRARY_NAME);
        if (libraryURL == null || !libraryURL.getProtocol().equals("jar")) {
            throw new LinkageError("Load " + LIBRARY_NAME);
        }

        File libsDir;
        try {
            Path tempDir = Files.createTempDirectory("sac-jni");
            tempDir.toFile().deleteOnExit();
            libsDir = tempDir.toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File libraryFile = getFile(libsDir, libraryURL);

        return libraryFile.getAbsolutePath();
    }

    @SuppressWarnings("resource")
    @NotNull
    private static File getFile(File libsDir, URL libraryURL) {
        File libraryFile = new File(libsDir, LIBRARY_NAME);
        libraryFile.deleteOnExit();

        try {
            InputStream inputStream = libraryURL.openStream();
            FileOutputStream outputStream = new FileOutputStream(libraryFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return libraryFile;
    }
}

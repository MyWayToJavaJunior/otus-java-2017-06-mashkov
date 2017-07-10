package ru.otus.testFramework;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class PackageScanner {
    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    public static List<Class<?>> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        List<Class<?>> classes = new ArrayList<>();

        try {
            Path scanned = Paths.get(scannedUrl.toURI());
            Files.walkFileTree(scanned,  new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String resource = scannedPackage + PKG_SEPARATOR + file.getFileName();
                    if (!Files.isDirectory(file) && resource.endsWith(CLASS_FILE_SUFFIX)){
                        int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
                        String className = resource.substring(0, endIndex);
                        try {
                            classes.add(Class.forName(className));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return classes;
    }

}

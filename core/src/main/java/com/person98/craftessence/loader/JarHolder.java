package com.person98.craftessence.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JarHolder {

    /**
     * A list of URLs representing the JAR files.
     *
     * <p>
     * This list stores the URLs of the JAR files added to a {@link JarHolder} instance.
     * The URLs can be used to access the contents of the JAR files.
     * </p>
     *
     * <p>
     * Example usage:
     * <pre>{@code
     * JarHolder jarHolder = new JarHolder();
     * jarHolder.addJarFile(new File("path/to/file.jar"));
     * jarHolder.addJarFile(new File("path/to/another/file.jar"));
     * URL[] jarUrls = jarHolder.getJarUrls();
     * }</pre>
     * </p>
     *
     * @see JarHolder
     * @see JarHolder#addJarFile(File)
     * @see JarHolder#getJarUrls()
     * @since 1.0.0
     */
    private final List<URL> jarUrls = new ArrayList<>();

    /**
     * Adds a JAR file to the list of JAR URLs.
     *
     * @param jarFile the JAR file to be added
     * @throws IOException if an I/O error occurs while converting the JAR file to a URL
     */
    public void addJarFile(File jarFile) throws IOException {
        URL jarUrl = jarFile.toURI().toURL();
        jarUrls.add(jarUrl);
    }

    /**
     * Returns an array of URLs representing the JAR file URLs stored in the JarHolder.
     *
     * @return an array of URLs representing the JAR file URLs
     */
    public URL[] getJarUrls() {
        return jarUrls.toArray(new URL[0]);
    }
}

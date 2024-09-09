package com.person98.craftessence.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JarHolder {

    private final List<URL> jarUrls = new ArrayList<>();

    // Add a JAR file URL to the list
    public void addJarFile(File jarFile) throws IOException {
        URL jarUrl = jarFile.toURI().toURL();
        jarUrls.add(jarUrl);
    }

    // Get all JAR URLs
    public URL[] getJarUrls() {
        return jarUrls.toArray(new URL[0]);
    }
}

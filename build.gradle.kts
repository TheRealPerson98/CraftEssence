group = "com.person98"
version = "1.0"

subprojects {
    apply(plugin = "java")  // Ensure the Java plugin is applied in all subprojects

    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    // Common Java configuration for all subprojects
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    // Java toolchain configuration should be placed in the java extension block
    configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }
}

// Ensure the root project build depends on subprojects' build tasks
tasks.register("buildAll") {
    dependsOn(subprojects.map { it.tasks["build"] })
}

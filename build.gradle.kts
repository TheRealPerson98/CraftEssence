group = "com.person98"
version = "1.0"

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven {
            url = uri("https://nexus.person98.com/repository/public/")
            credentials {
                username = System.getenv("NEXUS_USERNAME")
                password = System.getenv("NEXUS_PASSWORD")
            }
        }
    }

    dependencies {
        "compileOnly"("org.projectlombok:lombok:1.18.34") // Add Lombok as compile-only dependency
        "annotationProcessor"("org.projectlombok:lombok:1.18.34") // Ensure annotation processing works
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

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

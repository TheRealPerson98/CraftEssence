plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.person98"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    shadowJar {
        archiveFileName.set("CraftEssence.jar") // Set the name of the output jar
        archiveClassifier.set("") // This ensures no additional classifier is added to the jar name
        mergeServiceFiles()
    }

    build {
        dependsOn(shadowJar)
    }
}

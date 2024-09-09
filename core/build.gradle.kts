plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
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
        archiveFileName.set("CraftEssence.jar")
        archiveClassifier.set("")
        destinationDirectory.set(file("$buildDir/../../releases/Core/"))
        mergeServiceFiles()
    }
    build {
        dependsOn(shadowJar)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.shadowJar.get()) {
                artifactId = "CraftEssence" // Set the artifact ID to "CraftEssence"
            }
        }
    }
    repositories {
        maven {
            name = "Public"
            url = uri("https://nexus.person98.com/repository/public")
            credentials {
                username = System.getenv("NEXUS_USERNAME")
                password = System.getenv("NEXUS_PASSWORD")
            }
        }
    }
}

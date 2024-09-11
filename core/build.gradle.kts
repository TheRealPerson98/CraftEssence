plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.1"
    id("maven-publish")
}

group = "com.person98"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2")

    implementation("de.tr7zw:item-nbt-api-plugin:2.13.2")
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

        // Relocate NBT and Jackson to com.person98.libs
        relocate("de.tr7zw.nbtapi", "com.person98.libs.nbtapi")
        relocate("com.fasterxml.jackson", "com.person98.libs.jackson")

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
                artifactId = "CraftEssence"
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

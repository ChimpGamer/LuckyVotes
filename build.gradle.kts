plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "nl.chimpgamer"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

    compileOnly("dev.dejvokep:boosted-yaml:1.3.1")
    compileOnly("cloud.commandframework:cloud-paper:1.8.3")
    compileOnly("cloud.commandframework:cloud-minecraft-extras:1.8.3")

    compileOnly(fileTree("libs"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvmToolchain(17)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    processResources {
        filesMatching("**/*.yml") {
            expand("version" to project.version)
        }
    }
}
plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.iridium"
version = "1.8.9"
description = "IridiumTeams"

repositories {
    maven("https://repo.mvdw-software.com/content/groups/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://ci.ender.zone/plugin/repository/everything/")
    maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://hub.jeff-media.com/nexus/repository/jeff-media-public/")
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    // Dependencies that we want to shade in
    implementation("org.jetbrains:annotations:24.0.1")
    implementation("com.j256.ormlite:ormlite-core:6.1")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")
    implementation("com.iridium:IridiumCore:1.7.1")

    // Other dependencies that are not required or already available at runtime
    compileOnly("org.projectlombok:lombok:1.18.26")
    compileOnly("org.spigotmc:spigot-api:1.19.1-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("me.clip:placeholderapi:2.9.2")
    compileOnly("be.maximvdw:MVdWPlaceholderAPI:2.1.1-SNAPSHOT") {
        exclude("org.spigotmc")
    }

    implementation("de.jeff_media:SpigotUpdateChecker:1.3.2")
    implementation("org.bstats:bstats-bukkit:3.0.1")

    // Enable lombok annotation processing
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    // Test dependencies
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.18:2.26.0")
    testImplementation("com.github.MilkBowl:VaultAPI:1.7")
}

tasks {
    // "Replace" the build task with the shadowJar task (probably bad but who cares)
    jar {
        dependsOn("shadowJar")
        enabled = false
    }

    shadowJar {

        // Remove the archive classifier suffix
        archiveClassifier.set("")

        // Remove unnecessary files from the jar
        minimize()
    }

    // Set UTF-8 as the encoding
    compileJava {
        options.encoding = "UTF-8"
    }

    // Process Placeholders for the plugin.yml
    processResources {
        filesMatching("**/plugin.yml") {
            expand(rootProject.project.properties)
        }

        // Always re-run this task
        outputs.upToDateWhen { false }
    }

    test {
        useJUnitPlatform()
    }

    compileJava {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
    }

    compileTestJava {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }
}

// Set the Java version and vendor
java {
    toolchain {
        vendor.set(JvmVendorSpec.ADOPTOPENJDK)
    }
}

// Maven publishing
publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

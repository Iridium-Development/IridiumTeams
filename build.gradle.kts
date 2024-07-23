plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.iridium"
version = "2.5.7"
description = "IridiumTeams"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://ci.ender.zone/plugin/repository/everything/")
    maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://hub.jeff-media.com/nexus/repository/jeff-media-public/")
    maven("https://jitpack.io")
    maven("https://repo.rosewooddev.io/repository/public/")
    maven("https://repo.bg-software.com/repository/api/")
    maven("https://repo.essentialsx.net/releases/")
    maven("https://moyskleytech.com/debian/m2")
}

dependencies {
    // Dependencies that we want to shade in
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("com.j256.ormlite:ormlite-core:6.1")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")
    implementation("com.iridium:IridiumCore:2.0.5")

    // Other dependencies that are not required or already available at runtime
    compileOnly("org.projectlombok:lombok:1.18.34")
    compileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("dev.rosewood:rosestacker:1.5.23")
    compileOnly("com.bgsoftware:WildStackerAPI:2024.1")
    compileOnly("com.moyskleytech:ObsidianStackerAPI:1.0.0")
    compileOnly("net.ess3:EssentialsXSpawn:2.16.1")

    implementation("de.jeff_media:SpigotUpdateChecker:1.3.2")
    implementation("org.bstats:bstats-bukkit:3.0.2")

    // Enable lombok annotation processing
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    // Test dependencies
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.18:2.85.2")
    testImplementation("com.github.MilkBowl:VaultAPI:1.7")
}

tasks {
    // Add the shadowJar task to the build task
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        fun relocate(origin: String) = relocate(origin, "com.iridium.iridiumteams.dependencies${origin.substring(origin.lastIndexOf('.'))}")

        relocate("org.bstats")
        relocate("org.intellij")
        relocate("org.jetbrains")
        relocate("de.jeff_media.updatechecker")
        relocate("com.j256.ormlite")

        // Remove the archive classifier suffix
        archiveClassifier.set("")
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

// Maven publishing
publishing {
    publications.create<MavenPublication>("maven") {
        setGroupId("com.iridium")
        setArtifactId("IridiumTeams")
        setVersion(version)
        artifact(tasks["shadowJar"])
    }
}

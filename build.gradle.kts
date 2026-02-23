plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "9.3.1"
}

group = "com.iridium"
version = "2.6.13"
description = "IridiumTeams"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://ci.ender.zone/plugin/repository/everything/")
    maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.jeff-media.com/public/")
    maven("https://jitpack.io")
    maven("https://repo.rosewooddev.io/repository/public/")
    maven("https://repo.bg-software.com/repository/api/")
    maven("https://repo.essentialsx.net/releases/")
    maven("https://moyskleytech.com/debian/m2")
}

dependencies {
    // Dependencies that we want to shade in
    implementation("org.jetbrains:annotations:26.1.0")
    implementation("com.j256.ormlite:ormlite-core:6.1")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")
    implementation("com.iridium:IridiumCore:2.0.14")

    // Other dependencies that are not required or already available at runtime
    compileOnly("org.projectlombok:lombok:1.18.42")
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("me.clip:placeholderapi:2.12.2")
    compileOnly("dev.rosewood:rosestacker:1.5.39")
    compileOnly("com.bgsoftware:WildStackerAPI:2025.2")
    compileOnly("com.moyskleytech:ObsidianStackerAPI:1.0.0")
    compileOnly("net.ess3:EssentialsXSpawn:2.16.1")

    implementation("com.jeff_media:SpigotUpdateChecker:3.0.4")
    implementation("org.bstats:bstats-bukkit:3.1.0")

    // Enable lombok annotation processing
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    // Test dependencies
    testImplementation(platform("org.junit:junit-bom:6.0.3"))
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.3")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.18:2.85.2")
    testImplementation("com.github.MilkBowl:VaultAPI:1.7")
}

tasks {
    // Add the shadowJar task to the build task
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        fun relocate(origin: String) =
            relocate(origin, "com.iridium.iridiumteams.dependencies${origin.substring(origin.lastIndexOf('.'))}")

        // Remove the archive classifier suffix
        archiveClassifier.set("")

        // Relocate dependencies
        relocate("com.jeff_media.updatechecker")
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
        // TODO: fix unit tests & re-enable this
        // useJUnitPlatform()
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
        groupId = "com.iridium"
        artifactId = "IridiumTeams"
        version = version
        artifact(tasks["shadowJar"])
    }
}

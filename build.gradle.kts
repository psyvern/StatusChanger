import java.net.URI

plugins {

    id("fabric-loom") version "+"
}

version = project.properties["mod_version"] ?: "SNAPSHOT"
group = project.properties["maven_group"] ?: "UNKNOWN"

java {

    withSourcesJar()
}

base {

    archivesName.set((project.properties["archives_base_name"] ?: "UNKNOWN").toString())
}

repositories {

    maven {
        url = URI("https://jitpack.io")
    }
    maven {
        url = URI("https://maven.terraformersmc.com/")
    }
    maven {
        url = URI("https://maven.shedaniel.me/")
    }

}

dependencies {

    minecraft("com.mojang", "minecraft", project.properties["minecraft_version"]?.toString())
    mappings("net.fabricmc", "yarn", project.properties["yarn_mappings"]?.toString(), null, "v2")

    modImplementation("net.fabricmc", "fabric-loader", project.properties["loader_version"]?.toString())
    modImplementation("net.fabricmc.fabric-api", "fabric-api", project.properties["fabric_version"]?.toString())
}

tasks.processResources {

    inputs.property("version", version)

    filesMatching("fabric.mod.json") {

        expand("version" to version)
    }
}

tasks.jar {

    from("LICENSE") {

        rename {
            it + "_" + project.properties["archivesBaseName"]
        }
    }
}

plugins {
    `java-library`
    eclipse
    idea
}

repositories {
    mavenCentral()
}

ext.set("appName", "Black Magic")
project.version = "0.0.1"

idea {
    module {
        outputDir = file("build/classes/java/main")
        testOutputDir = file("build/classes/java/test")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.named<JavaCompile>("compileJava") {
    options.isIncremental = true
}

tasks.named<ProcessResources>("processResources") {
    this.dependsOn("generateAssetList")
}

// From https://lyze.dev/2021/04/29/libGDX-Internal-Assets-List/
tasks.register("generateAssetList").configure {
    inputs.dir("${project.rootDir}/assets/")
    val assetsFolder = file("${project.rootDir}/assets/")
    val assetsFile = file("$assetsFolder/assets.txt")
    assetsFile.delete()

    fileTree(assetsFolder).forEach {
        assetsFile.appendText("${it.path}\n")
    }
}

eclipse.project.name = "Black Magic" + "-parent"

//  compileKotlin.compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)





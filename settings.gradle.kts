rootProject.name = "blackmagic"

// A list of which subprojects to load as part of the same larger project.
// You can remove Strings from the list and reload the Gradle project
// if you want to temporarily disable a subproject.
//include("lwjgl3")
include("core")
//include("ios")
//include("android")

pluginManagement {
    plugins {
        `kotlin-dsl`
        eclipse
        idea
        id("com.android.application") version("8.1.4")
        id("org.jetbrains.kotlin.android") version("2.0.20")
    }
    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        google()
        maven { url = uri("https://s01.oss.sonatype.org") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
    }
}

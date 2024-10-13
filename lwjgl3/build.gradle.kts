import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.github.fourlastor.construo.Target
import org.jetbrains.kotlin.utils.addIfNotNull

plugins {
    id("blackmagic.java-conventions")
    application
    id("io.github.fourlastor.construo")
    kotlin("jvm")
}

sourceSets {
    main {
        resources {
            srcDirs(rootProject.file("assets").path)
        }
    }
}

application {
    mainClass.set("dev.souzou.blackmagic.lwjgl3.Lwjgl3Launcher")
}

val appName: String by project
val projectVersion: String by project

eclipse.project.name = "$appName-lwjgl3"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile>().configureEach {
    if (JavaVersion.current().isJava9Compatible) {
        options.release.set(8)
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_1_8)
    compilerOptions.freeCompilerArgs.add("-Xallow-unstable-dependencies")
    compilerOptions.freeCompilerArgs.add("-Xskip-prerelease-check")
}

dependencies {
    val gdxVersion: String by project

    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
    implementation(project(":core"))

    val enableGraalNative: String by project
    if(enableGraalNative == "true") {
        val graalHelperVersion: String by project
        implementation("io.github.berstanio:gdx-svmhelper-backend-lwjgl3:$graalHelperVersion")
    }
}

tasks.named<JavaExec>("run") {
    workingDir = file(rootProject.file("assets").path)
    setIgnoreExitValue(true)

    val os = System.getProperty("os.name").lowercase()
    if (os.contains("mac")) {
        jvmArgs?.plus("-XstartOnFirstThread")
    }
}

tasks.named<Jar>("jar") {
    // sets the name of the .jar file this produces to the name of the game or app, with the version after.
    archiveFileName.set("$appName-$projectVersion.jar")
    // the duplicatesStrategy matters starting in Gradle 7.0; this setting works.
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(configurations["runtimeClasspath"])

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    // these "exclude" lines remove some unnecessary duplicate files in the output JAR.
    exclude("META-INF/INDEX.LIST", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
    dependencies {
        exclude("META-INF/INDEX.LIST", "META-INF/maven/**")
    }
    // setting the manifest makes the JAR runnable.
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
    // this last step may help on some OSes that need extra instruction to make runnable JARs.
    doLast {
        file(archiveFile).setExecutable(true, false)
    }
}

construo {
    // name of the executable
    name.set(appName)
    // human-readable name, used for example in the `.app` name for macOS
    humanName.set(appName)
    // Optional, defaults to project version property
    version.set(projectVersion)

    targets {
        create<Target.Linux>("linuxX64") {
            architecture.set(Target.Architecture.X86_64)
            jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.11%2B9/OpenJDK17U-jdk_x64_linux_hotspot_17.0.11_9.tar.gz")
        }
        create<Target.MacOs>("macM1") {
            architecture.set(Target.Architecture.AARCH64)
            jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_aarch64_mac_hotspot_17.0.12_7.tar.gz")
            // macOS needs an identifier
            identifier.set("dev.souzou.blackmagic.$appName")
            // Optional: icon for macOS
            macIcon.set(project.file("icons/logo.icns"))
        }
        create<Target.MacOs>("macX64") {
            architecture.set(Target.Architecture.X86_64)
            jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_x64_mac_hotspot_17.0.12_7.tar.gz")
            // macOS needs an identifier
            identifier.set("dev.souzou.blackmagic." + appName)
            // Optional: icon for macOS
            macIcon.set(project.file("icons/logo.icns"))
        }
        create<Target.Windows>("winX64") {
            architecture.set(Target.Architecture.X86_64)
            jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_x64_windows_hotspot_17.0.12_7.zip")
        }
    }
}

// Equivalent to the jar task; here for compatibility with gdx-setup.
tasks.register("dist") {
    dependsOn("jar")
}

distributions {
  main {
    contents {
      into("libs") {
        project.configurations.runtimeClasspath.get().files
            .filter { file ->
                file.getName() != project.tasks.jar.get().outputs.files.singleFile.name
            }
            .forEach { file ->
                exclude(file.name)
            }
      }
    }
  }
}

tasks.named<CreateStartScripts>("startScripts") {
    dependsOn(":lwjgl3:jar")
    classpath = project.tasks.jar.get().outputs.files
}

//
//buildscript {
//  repositories {
//    gradlePluginPortal()
//  }
//  dependencies {
//    if(enableGraalNative == 'true') {
//      classpath "org.graalvm.buildtools.native:org.graalvm.buildtools.native.gradle.plugin:0.9.28"
//    }
//  }
//}
//plugins {
//  id "io.github.fourlastor.construo" version "1.4.1"
//  id "application"
//}
//apply plugin: 'org.jetbrains.kotlin.jvm'
//
//
//import io.github.fourlastor.construo.Target
//
//sourceSets.main.resources.srcDirs += [ rootProject.file('assets').path ]
//mainClassName = 'dev.souzou.blackmagic.lwjgl3.Lwjgl3Launcher'
//application.setMainClass(mainClassName)
//eclipse.project.name = appName + '-lwjgl3'
//java.sourceCompatibility = 8
//java.targetCompatibility = 8
//if (JavaVersion.current().isJava9Compatible()) {
//        compileJava.options.release.set(8)
//}
//kotlin.compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
//

//
//if(enableGraalNative == 'true') {
//  apply from: file("nativeimage.gradle")
//}

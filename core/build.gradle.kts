import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    `kotlin-dsl`
    id("blackmagic.java-conventions")
}

eclipse.project.name = ext.get("appName").toString() + "-core"

tasks.withType(JavaCompile::class.java).configureEach {
    options.encoding = "UTF-8"
}

tasks.named<KotlinJvmCompile>("compileKotlin") {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
//        jvmTargetValidationMode = JvmTargetValidationMode.WARNING
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
    }
}

dependencies {
    val box2dlightsVersion: String by project
    val aiVersion: String by project
    val gdxVersion: String by project
    val visUiVersion: String by project
    val ktxVersion: String by project
    val kotlinVersion: String by project
    val kotlinxCoroutinesVersion: String by project
    val fleksVersion: String by project

    api("com.badlogicgames.box2dlights:box2dlights:$box2dlightsVersion")
    api("com.badlogicgames.gdx:gdx-ai:$aiVersion")
    api("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")
    api("com.badlogicgames.gdx:gdx-freetype:$gdxVersion")
    api("com.badlogicgames.gdx:gdx:$gdxVersion")
    api("com.kotcrab.vis:vis-ui:$visUiVersion")
    api("io.github.libktx:ktx-actors:$ktxVersion")
    api("io.github.libktx:ktx-ai:$ktxVersion")
    api("io.github.libktx:ktx-app:$ktxVersion")
    api("io.github.libktx:ktx-assets-async:$ktxVersion")
    api("io.github.libktx:ktx-assets:$ktxVersion")
    api("io.github.libktx:ktx-async:$ktxVersion")
    api("io.github.libktx:ktx-box2d:$ktxVersion")
    api("io.github.libktx:ktx-collections:$ktxVersion")
    api("io.github.libktx:ktx-freetype-async:$ktxVersion")
    api("io.github.libktx:ktx-freetype:$ktxVersion")
    api("io.github.libktx:ktx-graphics:$ktxVersion")
    api("io.github.libktx:ktx-i18n:$ktxVersion")
    api("io.github.libktx:ktx-inject:$ktxVersion")
    api("io.github.libktx:ktx-json:$ktxVersion")
    api("io.github.libktx:ktx-log:$ktxVersion")
    api("io.github.libktx:ktx-math:$ktxVersion")
    api("io.github.libktx:ktx-preferences:$ktxVersion")
    api("io.github.libktx:ktx-reflect:$ktxVersion")
    api("io.github.libktx:ktx-scene2d:$ktxVersion")
    api("io.github.libktx:ktx-style:$ktxVersion")
    api("io.github.libktx:ktx-tiled:$ktxVersion")
    api("io.github.libktx:ktx-vis-style:$ktxVersion")
    api("io.github.libktx:ktx-vis:$ktxVersion")
    api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
    api("io.github.quillraven.fleks:Fleks:$fleksVersion")

    val enableGraalNative: String by project
    if (enableGraalNative == "true") {
        val graalHelperVersion: String by project
        implementation("io.github.berstanio:gdx-svmhelper-annotations:$graalHelperVersion")
    }
}

//compileKotlin {
//  kotlinOptions {
//    jvmTarget = JavaVersion.VERSION_11.toString()
//  }
//}
//
//tasks.withType(KotlinJvmCompile.class).configureEach {
//  jvmTargetValidationMode = JvmTargetValidationMode.WARNING
//}

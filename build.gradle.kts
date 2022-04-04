import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.20"
    id("com.github.johnrengelman.shadow") version "7.1.1"
    id("com.github.gmazzo.buildconfig") version "3.0.3"
}

group = "ren.natsuyuk1.hk416api"
version = "0.0.1" + getGitInfo()

repositories {
    mavenCentral()
    maven(url = "https://mirrors.huaweicloud.com/repository/maven")
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    gradlePluginPortal()
    maven(url = "https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
}

buildConfig {
    buildConfigField("String", "version", "\"${version}\"")
    buildConfigField("String", "commitHash", "\"${group}\"")
}

fun getGitInfo(): String {
    val commitHashCommand = "git rev-parse --short HEAD"
    val commitHash = Runtime.getRuntime().exec(commitHashCommand).inputStream.bufferedReader().readLine()

    val branchCommand = "git rev-parse --abbrev-ref HEAD"
    val branch = Runtime.getRuntime().exec(branchCommand).inputStream.bufferedReader().readLine()

    return "$branch-$commitHash"
}

tasks.shadowJar {
    isZip64 = true
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
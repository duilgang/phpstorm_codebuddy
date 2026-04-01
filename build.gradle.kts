plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.16.0"
}

group = "com.tencent.codebuddy"
version = "1.0.4"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

intellij {
    version.set("2021.3.3")
    type.set("IU")
    plugins.set(listOf("com.intellij.java"))
}

tasks {
    patchPluginXml {
        sinceBuild.set("213")
        untilBuild.set("213.*")
    }
}
plugins {
    java
    idea
    kotlin("jvm") version Versions.KOTLIN
}

group = "com.coinkeeper"
version = "1.0-SNAPSHOT"

allprojects {
    apply {
        plugin("java")
    }

    java.targetCompatibility = JavaVersion.VERSION_17
    java.sourceCompatibility = JavaVersion.VERSION_17

    repositories {
        mavenLocal()
        mavenCentral()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}

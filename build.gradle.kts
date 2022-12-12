plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "me.pluie"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.ginsberg:cirkle:1.0.1")
    implementation("org.jgrapht:jgrapht-core:1.5.1")
}
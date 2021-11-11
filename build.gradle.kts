import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.5.31"
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.noarg") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

group = "com.yapp"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val asciidoctorExtensions: Configuration by configurations.creating

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.1.RELEASE")
    implementation("org.springframework.security:spring-security-jwt:1.1.1.RELEASE")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.json:json:20210307")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")
    implementation("org.springframework.boot:spring-boot-starter-test"){
        exclude (group = "com.vaadin.external.google", module = "android-json")
    }
    implementation("org.slf4j:slf4j-api:1.7.32")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctorExtensions("org.springframework.restdocs:spring-restdocs-asciidoctor")

    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("com.github.consoleau:kassava:2.1.0")

    implementation ("org.mapstruct:mapstruct:1.4.2.Final")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.4.2.Final")

    implementation("org.mapstruct:mapstruct:1.5.0.Beta1")
    kapt("org.mapstruct:mapstruct-processor:1.5.0.Beta1")

    testImplementation("com.nhaarman:mockito-kotlin:1.5.0")
}

kapt {
    arguments {
        // Set Mapstruct Configuration options here
        // https://kotlinlang.org/docs/reference/kapt.html#annotation-processor-arguments
        // https://mapstruct.org/documentation/stable/reference/html/#configuration-options
        // arg("mapstruct.defaultComponentModel", "spring")
    }
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    test {
        useJUnitPlatform()
    }

    asciidoctor {
        dependsOn(test)
        configurations(asciidoctorExtensions.name)
        baseDirFollowsSourceDir()
        doLast {
            copy {
                from(outputDir)
                into("src/main/resources/static/docs")
            }
        }
    }

    build {
        dependsOn(asciidoctor)
    }
}
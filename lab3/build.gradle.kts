plugins {
    java
}

group = "testing.lab3"
version = "1.0.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    systemProperty("browser", System.getProperty("browser", "chrome"))
    systemProperty("headless", System.getProperty("headless", "false"))
    systemProperty("chrome.binary", System.getProperty("chrome.binary", "/usr/bin/google-chrome"))
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.31.0")
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.31.0")
    implementation("org.seleniumhq.selenium:selenium-firefox-driver:4.31.0")

    implementation("io.github.bonigarcia:webdrivermanager:5.8.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
    runtimeOnly("org.aspectj:aspectjweaver:1.9.24")

    testImplementation("org.hamcrest:hamcrest:2.2")

    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.slf4j:slf4j-simple:2.0.16")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.2")
}

import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test

allprojects {
    group = "testing.lab1"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    dependencies {
        add("testImplementation", "org.junit.jupiter:junit-jupiter:5.10.2")
        add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher")
    }
}

tasks.register("test") {
    group = "verification"
    description = "Runs tests in all subprojects (task1, task2, task3)"
    dependsOn(subprojects.map { it.tasks.named("test") })
}

tasks.register("clean") {
    group = "build"
    description = "Cleans build outputs in all subprojects"
    dependsOn(subprojects.map { it.tasks.named("clean") })
}

tasks.register("build") {
    group = "build"
    description = "Builds all subprojects (compile + test)"
    dependsOn(subprojects.map { it.tasks.named("build") })
}

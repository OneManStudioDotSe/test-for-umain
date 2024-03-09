import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

apply {
    plugin("com.github.ben-manes.versions")
}
//Check versions of dependencies: ./gradlew dependencyUpdates -Drevision=milestone -DoutputFormatter=json
//Force-update dependencies:      ./gradlew clean build --refresh-dependencies

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.versionsCheck) apply false
    alias(libs.plugins.hilt) apply false
    //alias(libs.plugins.serialization) apply false
    kotlin("plugin.serialization") version "1.9.22" apply false
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

// Reject all non-stable versions
tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}
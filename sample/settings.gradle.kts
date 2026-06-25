pluginManagement {
    // Use the Potassium Packager plugin straight from this repo's source (composite build),
    // so the sample always tracks the local plugin — no publish-to-mavenLocal needed.
    includeBuild("..")
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "potassium-sample"

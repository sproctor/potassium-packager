# Potassium Packager — sample app

A minimal **Compose Multiplatform** desktop application (`kotlin("multiplatform")` with a `jvm()`
target) packaged by the Potassium Packager plugin.

It's a standalone Gradle build that pulls the plugin straight from this repo's source via a
[composite build](https://docs.gradle.org/current/userguide/composite_builds.html) —
`pluginManagement { includeBuild("..") }` in [`settings.gradle.kts`](settings.gradle.kts) — so it
always builds against the local plugin. No `publishToMavenLocal` needed.

## Prerequisites

- **JDK 17+**
- **Node.js 18+** and **npm** — required only for building installers (electron-builder runs under
  the hood). Running the app (`./gradlew run`) does not need Node.

## Run it

```bash
cd sample
./gradlew run
```

## Package installers

One invocation builds every configured format for the current OS:

```bash
./gradlew packageDistributionForCurrentOS

# or just the current platform's non-store formats:
./gradlew packageMacOS      # DMG          (on macOS)
./gradlew packageWindows    # NSIS + MSI   (on Windows)
./gradlew packageLinux      # DEB + RPM + AppImage  (on Linux)
```

Output lands under `build/potassium/`. Formats not compatible with the build OS are skipped, and
Snap/Flatpak are skipped automatically when their tools aren't installed.

See the [main docs](../docs/getting-started.md) for the full DSL.

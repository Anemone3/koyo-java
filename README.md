# Koyo Java

A Java playground focused on exploring backend technologies,
frameworks, design patterns, and software engineering concepts
through independent learning modules.

The repository uses Gradle Multi-Project Build with centralized
dependency management and shared build configurations.

## Technologies

- Java 21
- Gradle
- Kotlin DSL
- Spring Boot
- Spring WebFlux
- Apache Kafka

## Project Structure

```text
koyo-java/
├── gradle/
│   ├── wrapper/
│   └── libs.versions.toml
├── learning-01/          # Spring WebFlux
├── learning-02/          # Apache Kafka
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── README.md
```

## Learning Modules

| Module | Technology | Description |
|--------|------------|-------------|
| learning-01 | Spring WebFlux | Reactive programming and non-blocking APIs |
| learning-02 | Apache Kafka | Event-driven architecture and messaging |

## Prerequisites

- JDK 21
- Git
- Internet connection for the initial dependency download
- Docker (optional, for external services such as Kafka)

A global Gradle installation is not required.
The repository includes the Gradle Wrapper.

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd koyo-java
```

### 2. Verify Gradle

Windows:

```powershell
.\gradlew --version
```

Linux / macOS:

```bash
chmod +x gradlew
./gradlew --version
```

### 3. List available modules

```bash
./gradlew projects
```

### 4. Build the project

```bash
./gradlew build
```

## Running Modules

Each module can be executed independently from the root directory.

### Learning 01 - Spring WebFlux

```bash
./gradlew :learning-01:bootRun
```

Test endpoint:

```text
http://localhost:8080/hello
```

### Learning 02 - Apache Kafka

```bash
./gradlew :learning-02:bootRun
```

A running Kafka broker is required for producing and consuming
messages. Broker configuration is not included in this setup yet.

> On Windows PowerShell, replace `./gradlew` with `.\gradlew`.

## Gradle Commands

| Command | Description |
|---------|-------------|
| `./gradlew projects` | List all modules |
| `./gradlew tasks` | List available tasks |
| `./gradlew build` | Build all modules |
| `./gradlew clean` | Remove build outputs |
| `./gradlew :learning-01:build` | Build a specific module |
| `./gradlew :learning-01:bootRun` | Run WebFlux |
| `./gradlew :learning-02:bootRun` | Run Kafka application |
| `./gradlew :learning-01:test` | Run module tests |
| `./gradlew :learning-01:dependencies` | List module dependencies |

## Dependency Management

Dependencies and versions are centralized in:

```text
gradle/libs.versions.toml
```

Each module declares only the dependencies it requires.

Example:

```kotlin
dependencies {
    implementation(platform(libs.spring.bom))
    implementation(libs.spring.webflux)
}
```

Shared configurations, including the Java toolchain and
repositories, are managed by the root `build.gradle.kts`.

Gradle stores downloaded dependencies in a shared global cache,
avoiding unnecessary duplication across modules.

## Adding a New Module

1. Create a new module directory.
2. Create its `build.gradle.kts` file.
3. Register the module in `settings.gradle.kts`.
4. Declare the required dependencies in the version catalog.
5. Reference the dependencies from the module.
6. Create the Java source directories.

## Purpose

This repository is intended for continuous learning,
experimentation, and practical exploration of Java technologies.
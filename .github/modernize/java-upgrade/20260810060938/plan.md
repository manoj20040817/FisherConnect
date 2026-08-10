# Upgrade Plan: FisherConnect (20260810060938)

- **Generated**: 2026-08-10 06:09:38
- **HEAD Branch**: N/A
- **HEAD Commit ID**: N/A

## Available Tools

**JDKs**
- JDK 18.0.1.1: C:\Program Files\Java\jdk-18.0.1.1\bin (available, not matching project target)
- JDK 26.0.1: C:\Program Files\Java\jdk-26.0.1\bin (available, newer than target, not LTS-targeted)
- JDK 25: **<TO_BE_INSTALLED>** (required by step 3 and final validation)

**Build Tools**
- Maven 3.9.16: D:\tools\apache-maven-3.9.16\bin (installed and compatible)
- Maven Wrapper: not present in project root

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

- Keep the application on the latest supported Java LTS release while preserving current Spring Boot behavior.
- Prefer minimal project changes; do not upgrade unrelated framework dependencies unless required for Java 25 compatibility.
- If the target JDK is not available, install it before compilation and testing.

## Options

- Working branch: appmod/java-upgrade-20260810060938
- Run tests before and after the upgrade: true

## Upgrade Goals

- Java 25

## Technology Stack

| Technology/Dependency | Current | Min Compatible Version | Why Incompatible |
| ---------------------- | ------- | ---------------------- | ---------------- |
| Java | 17 | 25 | User requested |
| Spring Boot | 3.2.0 | 3.5.0 | Java 25 runtime compatibility is best supported on the current Spring Boot LTS line |
| Maven | 3.9.16 | 3.9.0+ | Compatible with Java 25 and current project toolchain |
| Spring Boot JPA starter | 3.2.0 | 3.5.0 | Align with the Java 25 runtime target and current Spring Boot compatibility |

## Derived Upgrades

- Java 25 requires the project build to target the 25 runtime and compile with a supported JDK.
- Spring Boot 3.2.0 is a valid baseline but is not the recommended Java 25-compatible baseline; upgrading to the 3.5.x line keeps the project aligned with the target runtime while preserving the current Spring Boot configuration.
- No wrapper upgrade is required because the project does not contain Maven Wrapper files.

## Impact Analysis

### Subsection: Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
|------|-----------|---------|--------|--------|--------|
| backend/pom.xml | java.version | 17 | upgrade | 25 | User requested latest LTS Java |
| backend/pom.xml | spring-boot-starter-parent | 3.2.0 | upgrade | 3.5.5 | Align Boot with Java 25 target and current supported LTS line |

### Subsection: Source Code Changes

| File | Location | Current | Required Change | Reason |
|------|----------|---------|----------------|--------|
| backend/src/main/java/com/fisherconnect/FisherConnectApplication.java | application entry point | standard Spring Boot app | No source rewrite expected | The app is already compatible with Java 17+/Spring Boot 3.x and should compile unchanged under Java 25 |

### Subsection: Configuration Changes

| File | Property/Setting | Current | Required Change | Reason |
|------|------------------|---------|-----------------|--------|
| backend/src/main/resources/application.properties | spring.autoconfigure.exclude | DataSource + Hibernate autoconfig disabled | No change | Current application config remains valid under Java 25 |

### Subsection: CI/CD Changes

| File | Location | Current | Required Change |
|------|----------|---------|----------------|
| None | N/A | N/A | No CI/CD files found in the project workspace |

### Subsection: Risks & Warnings

- **Java 25 compatibility of Boot 3.2.0**: The project is currently on Spring Boot 3.2.0, which may work with Java 25 but is not the current LTS-aligned support line. **Mitigation**: upgrade to Spring Boot 3.5.x before final validation and run the full Maven test suite on JDK 25.
- **No baseline JDK 17 available**: The current project JDK is not installed in the environment, so the optional baseline compile/test run cannot be executed. **Mitigation**: proceed directly to the target JDK install and run the upgrade validation using JDK 25.

## Upgrade Steps

- Step 1: Install the required Java 25 toolchain
  - **Rationale**: The project is configured for Java 17 but no matching JDK is available; Java 25 must be installed before compilation and validation.
  - **Changes to Make**: Install JDK 25 and verify availability via the environment list and PATH.
  - **Verification**: Use `#appmod-list-jdks` and confirm the JDK 25 installation path is present; expected result: Java 25 is available to Maven.

- Step 2: Setup Baseline
  - **Rationale**: The project base JDK is not available, so the optional baseline is skipped rather than guessed.
  - **Changes to Make**: Skip baseline compile/test because the current JDK is not installed.
  - **Verification**: Record baseline as `skipped` with reason: current JDK 17 unavailable in this environment.

- Step 3: Upgrade the project to Java 25 and the compatible Spring Boot line
  - **Rationale**: This is the primary code change required to meet the target runtime while preserving existing application behavior.
  - **Changes to Make**: Apply the dependency changes from Impact Analysis: update `java.version` to 25 and upgrade `spring-boot-starter-parent` to 3.5.5 in `backend/pom.xml`.
  - **Verification**: `D:\tools\apache-maven-3.9.16\bin\mvn clean test-compile -q` using JDK 25; expected result: compilation success for main and test code.

- Step 4: CVE Validation & Fix
  - **Rationale**: Security scan is required for direct dependencies after the version upgrade.
  - **Changes to Make**: Extract direct dependencies, scan for CVEs, apply any required patch-level upgrades, and re-scan.
  - **Verification**: `D:\tools\apache-maven-3.9.16\bin\mvn dependency:list -DexcludeTransitive=true` plus `#appmod-validate-cves-for-java`; expected result: no unresolved direct dependency CVEs remain.

- Step 5: Final Validation
  - **Rationale**: Confirm the project compiles and all tests pass under Java 25.
  - **Changes to Make**: Fix any remaining compile or test issues discovered during the final run.
  - **Verification**: `D:\tools\apache-maven-3.9.16\bin\mvn clean test -q` using JDK 25; expected result: 100% pass rate, or equivalent baseline if tests are absent.

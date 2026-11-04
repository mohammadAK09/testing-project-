# Build and Test Guide

Run commands from the repository root.

## Compile and execute tests

```bash
mvn clean test
```

A successful command ends with `BUILD SUCCESS`. Test reports are written under `target/surefire-reports/`.

## Optional coverage report

```bash
mvn clean test jacoco:report
```

Open `target/site/jacoco/index.html`. Coverage is supporting information and does not replace meaningful test design or assertions.

## Common checks

- Confirm that `java -version` reports Java 17 or later.
- Confirm that tests are under `src/test/java`.
- Confirm that test class names end with `Test`.
- Do not rely only on the IDE's test button. The command-line build is the submission check.

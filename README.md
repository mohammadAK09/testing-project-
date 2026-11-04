# Software Testing Semester Project Starter

This repository contains the setup and templates for the semester Ticket Booking System project. It provides configuration and guidance only. It does **not** provide solutions to the assessed requirements.

## Quick start

### Requirements

- Java 17 or later
- Maven 3.9 or later
- Git

Check your installation:

```bash
java -version
mvn -version
git --version
```

Run all tests:

```bash
mvn clean test
```

Generate the optional JaCoCo report:

```bash
mvn clean test jacoco:report
```

The HTML report will be available at `target/site/jacoco/index.html`.

## Where to work

- Production code: `src/main/java/edu/course/booking/`
- Test code: `src/test/java/edu/course/booking/`
- Report and submission templates: `docs/templates/`
- Student guides: `docs/guides/`

You may add small supporting classes and packages when needed. Do not add a GUI, database, REST API, or real external integrations.

## Suggested workflow

1. Read `PROJECT_SPECIFICATION.md`.
2. Add all team members to the repository.
3. Complete `docs/templates/TEAM_CONTRIBUTION_MATRIX.md` as work progresses.
4. For TDD work, commit the failing test, the minimum implementation, and any meaningful refactoring.
5. Run `mvn clean test` before pushing.
6. Complete the report, manifest, AI declaration/log, and checklist.
7. Create the Moodle submission using the script in `scripts/` or the equivalent commands in the submission guide.

## Important

- Do not commit passwords, tokens, private data, or build output.
- All group members must make meaningful commits from their own accounts.
- AI-assisted work must be reviewed, verified, and disclosed as required by the specification.
- The submitted commit hash must match the assessed repository state.

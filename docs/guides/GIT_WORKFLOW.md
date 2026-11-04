# Git Collaboration Guide

## Before starting work

```bash
git pull
```

Create a short-lived branch when appropriate:

```bash
git switch -c test/booking-validation
```

## Commit meaningful changes

```bash
git add src/test/java/...
git commit -m "test: add failing booking validation test"
```

Example prefixes:

- `test:` tests or test data
- `feat:` production behavior
- `fix:` defect correction
- `refactor:` structure without behavior change
- `docs:` report or documentation
- `build:` Maven configuration

## Share work

```bash
git push -u origin test/booking-validation
```

Merge only after tests pass and conflicts are resolved. Avoid sharing one GitHub account among team members.

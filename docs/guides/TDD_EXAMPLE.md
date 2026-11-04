# Short TDD Example: Leap-Year Rule

This example is unrelated to the assessed Ticket Booking System. Its purpose is to demonstrate the expected evidence, not to prescribe the team’s exact commit count.

## Red

Add a focused failing test for a year divisible by 4, then commit it:

```text
test: add failing test for ordinary leap year
```

The test should fail for the intended reason, not because the project does not compile.

## Green

Add the minimum implementation needed to satisfy that test, run all tests, and commit:

```text
feat: implement ordinary leap year rule
```

## Refactor

Improve names or structure without changing behavior, run all tests again, and commit if the refactoring is meaningful:

```text
refactor: clarify leap year condition
```

## What to record in the report

Record the relevant commit hashes and explain briefly:

- what behavior was introduced;
- why the Red test failed;
- what minimum change made it pass; and
- what was improved during refactoring.

Do not create artificial commits after completing the feature. The repository should reflect the actual development process.

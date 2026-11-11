# Software Testing Semester Project Report — Milestone 1

## Cover Information

- **Course:** Software Testing
- **Milestone:** Milestone 1
- **Group number:** [ENTER GROUP NUMBER]
- **Student names and IDs:** moath abd salam bani fadel 2334657, mohammad omar abu khorj 2332768
- **Repository URL:** https://github.com/mohammadAK09/testing-project-
- **Final commit hash:** [ENTER FINAL SUBMISSION COMMIT HASH AFTER FINAL MERGE]

## 1. System and Test Scope

Our project is a Ticket Booking System implemented using Java 17 and Maven. Milestone 1 focuses on the `BookingManager` class and its unit tests using JUnit 5 and Mockito.

The implemented and tested behaviors are successful booking, rejection of invalid booking requests, rejection of sold-out events, and handling of payment failure. A successful booking checks event availability, calculates the total in cents, processes payment, saves the booking, and sends a confirmation. Invalid requests are rejected before any external dependency is called.

The system intentionally excludes a GUI, real database, real payment service, and real notification integration. The dependencies are represented by interfaces and mocked during unit testing. Milestone 2 functionality, including discount rules and pair-wise test design, is outside the current milestone scope.

### Test Execution Summary

- **Command:** `mvn clean test`
- **Last recorded execution:** November 11, 2026
- **Result:** `BUILD SUCCESS`
- **Total tests:** 18; **failures:** 0; **errors:** 0; **skipped:** 0
- **Booking tests:** 14 (1 successful booking, 10 parameterized invalid requests, 1 null-request test, 1 sold-out event, 1 payment failure)
- **Starter example tests:** 4 (`EvenNumberCheckerTest`)
- **JaCoCo coverage:** 100% instruction coverage and 100% branch coverage in the local report generated on November 11, 2026 after adding the null-request test (measured code only; coverage does not prove correctness).

These figures describe the recorded local run on the team's feature branch; rerun the suite and update this section if the submitted code changes.

## 2. Milestone 1: Design and Testability

`BookingManager` coordinates the booking workflow and receives its dependencies through its constructor (**constructor dependency injection**):

- `IPaymentGateway`: processes payment and returns a transaction ID.
- `INotificationService`: sends a booking confirmation.
- `IEventRepository`: checks whether an event is sold out and saves a booking.

`BookingRequest` stores customer ID, event ID, ticket quantity, and price per ticket in cents. `Booking` represents a saved booking, and `BookingResult` represents the result returned to the caller. The total amount is computed using `long` arithmetic to avoid overflow from multiplying two `int` values.

Constructor injection makes the class testable in isolation: Mockito mocks replace all three external services. Tests can control dependency responses and verify both expected calls and prohibited calls, without using a real database, payment gateway, or notification provider.

### Requirements and Test Mapping

| Behavior | Test class | Verified outcome |
|---|---|---|
| Successful booking | `BookingManagerTest` | Success result and transaction ID; availability, payment, saving, and confirmation called |
| Invalid request | `BookingManagerValidationTest` | 10 parameterized cases rejected with `IllegalArgumentException`; no dependency interactions |
| Null booking request | `BookingManagerNullRequestTest` | A null request is rejected with `IllegalArgumentException`; no dependency interactions |
| Sold-out event | `BookingManagerSoldOutTest` | Unsuccessful result; availability checked once; no payment, saving, or confirmation |
| Payment failure | `BookingManagerPaymentFailureTest` | Payment exception propagates; no booking saved or confirmation sent |

## 3. Milestone 1: TDD Evidence

We used the **Red–Green–Refactor** cycle for two distinct behaviors. The commit references below represent real commits in the repository history; the recorded test failures and subsequent successful executions were observed during development.

### Behavior 1: Successful Booking

- **Red commits:** `036cd3d`, `b3315ff`
- **Green commit:** `b7ff9e2`
- **Refactor commit:** `0d3a1f4`

**RED:** We first committed a successful-booking test before the implementation existed. After adding an initial class structure, the test executed but failed because the successful-booking behavior had not been implemented.

**GREEN:** We implemented the workflow: checking availability, calculating the total price, processing payment, saving the booking, sending a confirmation, and returning a successful result with a transaction ID. The test passed.

**REFACTOR:** We extracted the total-price calculation into a private method, `calculateTotalAmount()`, and reran the tests successfully without changing observable behavior.

### Behavior 2: Invalid Booking Validation

- **Red commit:** `b2c2a54`
- **Green commit:** `c04f75a`
- **Refactor commit:** `db672d7`

**RED:** We committed 10 JUnit 5 parameterized tests for invalid booking inputs before validation was implemented. Initially, the test branch lacked production classes, causing compilation errors. After integrating those classes, all 10 validation cases executed and failed because invalid requests were not rejected.

**GREEN:** We added checks for null or blank customer and event IDs, ticket quantity outside 1–5, and non-positive ticket prices. An invalid request throws `IllegalArgumentException` before any external service interaction. The full suite passed.

**REFACTOR:** We extracted the validation conditions into a private `validateRequest()` method. The tests continued to pass.

## 4. Limitation of Mocking

Mock-based tests verify how `BookingManager` interacts with controlled fake dependencies, but they do not prove that a real payment provider, persistent repository, or notification service will behave correctly. Real integrations may introduce network failures, timeouts, transaction problems, or configuration errors that Mockito unit tests do not expose. Integration tests would be needed to assess those risks.

## 5–8. Milestone 2 Sections

The Input Space Model, Constraints and Infeasible Combinations, Pair-Wise Test Set, and Limitation of Pair-Wise Testing are **reserved for Milestone 2** and have not been completed or claimed as Milestone 1 results.

## 9. Team Contribution Matrix

| Student name and ID | Main responsibilities | Key files or report sections | Representative commits | Contribution confirmed by team |
|---|---|---|---|---|
| moath abd salam bani fadel / 2334657 | Successful booking implementation, shared domain classes and interfaces, successful-booking unit test, first TDD cycle, null-request test and coverage review | `src/main/java/edu/course/booking/`, `BookingManagerTest.java`, `BookingManagerNullRequestTest.java`, TDD Behavior 1 | `036cd3d`, `b3315ff`, `b7ff9e2`, `0d3a1f4`, `6ab354b` | [TEAM TO CONFIRM] |
| mohammad omar abu khorj / 2332768 | Invalid-input parameterized tests and validation implementation, second TDD cycle, sold-out and payment-failure tests, documentation | `BookingManagerValidationTest.java`, `BookingManagerSoldOutTest.java`, `BookingManagerPaymentFailureTest.java`, `docs/M1_VALIDATION_TEST_CASES.md`, report draft | `b2c2a54`, `c04f75a`, `db672d7`, `1d0f9bc`, `76ac2bf`, `7db9908`, `0becc87` | [TEAM TO CONFIRM] |

Responsibilities and authorship must be confirmed against the final Git history and by both team members. AI-assisted material is disclosed below and must be reviewed by the team.

## Appendix A: AI Use Log

Material assistance from ChatGPT was used in developing code, tests, and report wording. The team is responsible for checking all retained suggestions against the assignment requirements and for being able to explain them.

| Date | Tool | Purpose and prompt summary | Files or sections affected | Verification / review | Decision |
|---|---|---|---|---|---|
| Nov 4, 2026 | ChatGPT | Guidance on successful-booking test, implementation, and Red–Green–Refactor workflow | `BookingManagerTest.java`, booking production classes | Failing and passing Maven test runs recorded; team must confirm independent code review | Accepted/modified during development |
| Nov 4, 2026 | ChatGPT | Guidance on input validation, fixing misplaced file contents, and validation refactor | `BookingManager.java`, validation TDD workflow | Recorded 10 validation failures followed by passing tests; manual review to confirm | Accepted/modified during development |
| Nov 11, 2026 | ChatGPT | Suggested unit tests for sold-out events and payment failure | `BookingManagerSoldOutTest.java`, `BookingManagerPaymentFailureTest.java` | Both tests passed; full suite later passed with 18 tests, zero failures | Accepted during development |
| Nov 11, 2026 | ChatGPT | Suggested a null-request test after examining JaCoCo coverage gaps | `BookingManagerNullRequestTest.java`, coverage review | Test passed; full suite passed with 18 tests and JaCoCo reported 100% instruction and branch coverage | Accepted during development |
| Nov 11, 2026 | ChatGPT | Drafted technical descriptions, TDD evidence, and report structure | `docs/M1_REPORT_DRAFT.md` | **Pending team review and verification of final text and commit references** | Draft — pending review |

**Before submission:** Replace every square-bracket placeholder, confirm the contribution matrix, independently inspect the requirements and test assertions, confirm the report accurately describes the final version, rerun `mvn clean test`, record the final assessed commit hash, and prepare the required PDF, submission manifest, bundle, and ZIP according to the submission checklist.

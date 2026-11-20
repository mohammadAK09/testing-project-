# Software Testing Semester Project Report — Milestone 1

## Cover Information

- **Course:** Software Testing (2010031410)
- **Milestone:** 1
- **Group number:** Pending confirmation from the instructor
- **Student names and IDs:** moath abd salam bani fadel (2334657); mohammad omar abu khorj (2332768)
- **GitHub usernames:** `Moath-c`; `mohammadAK09`
- **Repository URL:** https://github.com/mohammadAK09/testing-project-
- **Final submission commit hash:** Pending final review and merge into `main`

## 1. System and Test Scope

This project implements a Ticket Booking System using Java 17 and Maven. Milestone 1 focuses on the testability and behavior of the `BookingManager` class, using JUnit 5 and Mockito for automated unit testing.

The implemented behaviors are successful ticket booking, invalid-request rejection, sold-out-event handling, and payment-failure handling. For a valid booking, the system checks availability, calculates the total price in cents, processes payment, saves the booking, sends a confirmation, and returns a successful booking result. Invalid requests are rejected before any external service is called.

The scope intentionally excludes a user interface, real database, live payment provider, and live notification provider. Those dependencies are represented by interfaces and replaced with Mockito mocks in unit tests. Milestone 2 topics, including an Input Space Model and pair-wise test design, are outside the scope of this Milestone 1 report.

### Test Execution Summary — November 20, 2026

The latest version of the feature branch was tested successfully on both team members' computers on **November 20, 2026**.

| Item | Verified result |
|---|---|
| Command | `mvn clean test` (with Java 17 JDK correctly configured) |
| Maven result | `BUILD SUCCESS` |
| Tests executed | **18** |
| Failures / errors / skipped | **0 / 0 / 0** |
| Booking tests | **14**: 1 successful booking, 10 parameterized invalid-request cases, 1 null-request test, 1 sold-out test, 1 payment-failure test |
| Starter example tests | **4** (`EvenNumberCheckerTest`) |
| JaCoCo instruction coverage | **100%** — 160 of 160 instructions covered |
| JaCoCo branch coverage | **100%** — 20 of 20 branches covered |
| JaCoCo lines / methods / classes | 36/36 lines, 9/9 methods, 5/5 classes covered |

The JaCoCo values were checked in the local HTML report at `target/site/jacoco/index.html` on November 20, 2026. They apply to the **measured code** and are not a guarantee of defect-free software or correct behavior in real integrations. If the final production code changes, the tests and coverage should be rerun.

## 2. Milestone 1: Design and Testability

`BookingManager` receives its three external collaborators through its constructor (**constructor dependency injection**):

- `IPaymentGateway` processes payments and returns a transaction ID.
- `INotificationService` sends a booking confirmation.
- `IEventRepository` checks event availability and stores bookings.

`BookingRequest` carries the customer ID, event ID, ticket quantity, and ticket price in cents. `Booking` stores the completed booking details. `BookingResult` carries the success flag and transaction ID. Total cost is calculated using `long` arithmetic to avoid overflowing an `int` during multiplication.

The three interfaces separate business logic from external services. During unit testing, Mockito supplies controlled mock implementations. This allows the tests to simulate sold-out events and payment errors, verify the expected sequence of operations, and confirm that no inappropriate external interactions occur.

### Requirements and Test Mapping

| Behavior | Test class | What the assertions verify |
|---|---|---|
| Successful booking | `BookingManagerTest` | Successful result and correct transaction ID; expected availability, payment, save, and notification interactions occur in order; the saved `Booking` has the correct customer ID, event ID, quantity, total amount, and transaction ID. `ArgumentCaptor<Booking>` captures the saved booking and `InOrder` verifies interaction order. |
| Invalid request | `BookingManagerValidationTest` | Ten parameterized cases throw `IllegalArgumentException`; the three mocks have no interactions. |
| Null request | `BookingManagerNullRequestTest` | A null booking request throws `IllegalArgumentException` without contacting any dependency. |
| Sold-out event | `BookingManagerSoldOutTest` | An unsuccessful result with a null transaction ID; no payment, save, or confirmation. |
| Payment failure | `BookingManagerPaymentFailureTest` | A simulated payment exception propagates; the repository does not save a booking and no notification is sent. |

## 3. Milestone 1: TDD Evidence

Two behaviors were developed using a **Red–Green–Refactor** workflow. The commit identifiers below refer to commits preserved in the repository history. The observed failing and passing tests are described separately from the latest passing run.

### Behavior 1 — Successful Booking

- **RED:** `036cd3d`, `b3315ff`
- **GREEN:** `b7ff9e2`
- **REFACTOR:** `0d3a1f4`
- **Later test strengthening:** `c737a0d` (November 20, 2026; not represented as a separate TDD cycle)

**RED:** A successful-booking unit test was committed before the required implementation existed. An initial scaffold then allowed the test to run, but the expected successful-booking behavior was still missing, so the test failed.

**GREEN:** The booking workflow was implemented: check availability, calculate total cost, process payment, save booking, send confirmation, and return the successful result with its transaction ID. The test passed.

**REFACTOR:** Total-price calculation was extracted into `calculateTotalAmount()` without intentionally changing externally visible behavior, and the tests continued to pass.

**Additional verification on November 20:** `BookingManagerTest` was strengthened with `ArgumentCaptor` assertions for all saved-booking fields and `InOrder` assertions for availability, payment, storage, and notification. The complete suite still passed (18 tests, zero failures).

### Behavior 2 — Invalid Booking Validation

- **RED:** `b2c2a54`
- **GREEN:** `c04f75a`
- **REFACTOR:** `db672d7`

**RED:** Ten parameterized tests for invalid inputs were committed before validation was implemented. The separate test branch initially lacked production classes, so compilation failed. After the production classes were integrated, the ten tests executed and failed because invalid inputs were not rejected.

**GREEN:** Validation was implemented for null, empty, and blank customer and event IDs; ticket quantity outside the inclusive range 1–5; and non-positive price per ticket. Invalid requests throw `IllegalArgumentException` without contacting external dependencies.

**REFACTOR:** Validation conditions were extracted into the private `validateRequest()` method. The validation tests continued to pass.

## 4. Limitation of Mocking

Mockito-based unit tests verify interactions with controlled mock dependencies, but they do not verify that a real payment gateway, database, or notification provider behaves as expected. Production integrations may encounter network problems, timeouts, persistence errors, transaction inconsistencies, and configuration failures. Additional integration and end-to-end tests would be required to assess those risks. High code coverage alone does not overcome this limitation.

## 5–8. Milestone 2 Sections

The **Input Space Model**, **Constraints and Infeasible Combinations**, **Pair-Wise Test Set**, and **Limitation of Pair-Wise Testing** are reserved for Milestone 2. They are not claimed as Milestone 1 deliverables in this report.

## 9. Team Contribution Matrix

| Student name and ID | Main responsibilities | Key files and report sections | Representative commits | Team confirmation |
|---|---|---|---|---|
| moath abd salam bani fadel / 2334657 (`Moath-c`) | Successful-booking workflow and domain abstractions; first TDD cycle; null-request test; stronger successful-booking assertions; running Maven tests and reviewing coverage | `src/main/java/edu/course/booking/`, `BookingManagerTest.java`, `BookingManagerNullRequestTest.java`, Section 3 Behavior 1 | `036cd3d`, `b3315ff`, `b7ff9e2`, `0d3a1f4`, `6ab354b`, `c737a0d` | Confirmed by both team members (November 20, 2026) |
| mohammad omar abu khorj / 2332768 (`mohammadAK09`) | Parameterized validation tests; validation implementation and refactor; second TDD cycle; sold-out and payment-failure tests; test-case documentation, report and README maintenance; running Maven tests | `BookingManagerValidationTest.java`, `BookingManagerSoldOutTest.java`, `BookingManagerPaymentFailureTest.java`, `docs/M1_VALIDATION_TEST_CASES.md`, `docs/M1_REPORT_DRAFT.md`, `README.md`, Section 3 Behavior 2 | `b2c2a54`, `c04f75a`, `db672d7`, `1d0f9bc`, `76ac2bf`, `7db9908`, `0becc87`, `ed4b780`, `be0d852`, `766c653` | Confirmed by both team members (November 20, 2026) |

**Git attribution note:** Some older commits have Mohammad's Git author name but are not automatically linked to his GitHub profile because of an earlier local email configuration error. The original commit history was retained to preserve the TDD evidence. Newer commits `be0d852` and `766c653` are attributed to `mohammadAK09` on GitHub. Actual contributions are listed by the work performed and documented commits, not by the account-link display alone.

Both team members confirmed the responsibilities and contribution matrix on November 20, 2026. Commit references were checked against the repository history.

## Appendix A — AI Use Log

ChatGPT materially assisted with code, test suggestions, debugging, TDD workflow, and documentation. The retained outputs were tested or reviewed as indicated below. The team remains responsible for understanding the implementation, checking correctness against the instructor's requirements, and approving the final submission.

| Date | Tool | Purpose / prompt summary | Files or sections affected | Evidence of checking / review status | Decision |
|---|---|---|---|---|---|
| November 4, 2026 | ChatGPT | Guidance for the successful-booking test, implementation, and Red–Green–Refactor workflow | `BookingManagerTest.java`, `BookingManager.java`, domain classes; TDD Behavior 1 | Recorded failing and passing test steps; final team review still required | Suggestions accepted and adapted |
| November 4, 2026 | ChatGPT | Guidance on validation test cases, implementation and refactoring | `BookingManagerValidationTest.java`, `BookingManager.java`; TDD Behavior 2 | Ten failing validation cases followed by passing tests were recorded; final team review still required | Suggestions accepted and adapted |
| November 11, 2026 | ChatGPT | Suggestions for sold-out and payment-failure test cases | `BookingManagerSoldOutTest.java`, `BookingManagerPaymentFailureTest.java` | Both tests included in passing Maven suite | Accepted during development |
| November 11, 2026 | ChatGPT | Suggestion for a null-request test to address a coverage gap | `BookingManagerNullRequestTest.java`; coverage analysis | Full suite passed; local JaCoCo coverage was checked | Accepted during development |
| November 11, 2026 | ChatGPT | Drafting and structuring technical report sections and TDD evidence | `docs/M1_REPORT_DRAFT.md` | Commit references and main technical descriptions checked against repository; final student review pending | Drafted with AI assistance |
| November 20, 2026 | ChatGPT | Guidance to strengthen the successful-booking test with `ArgumentCaptor` and `InOrder`; help with Java environment troubleshooting and test verification | `BookingManagerTest.java`; test execution workflow | 18 tests passed with zero failures and errors; latest test commit `c737a0d`; user inspected local JaCoCo report showing 100% instruction and branch coverage | Suggested changes applied and tested |
| November 20, 2026 | ChatGPT | Guidance for updating README, test-execution documentation, and Milestone 1 report with current results and contribution history | `README.md`, `docs/M1_VALIDATION_TEST_CASES.md`, `docs/M1_REPORT_DRAFT.md` | Newer documentation commits verified on GitHub; report prepared for review; final student approval pending | Documentation assistance accepted / draft pending approval |

**AI use declaration:** Generative AI materially influenced assessed project work. This log is provided as disclosure; the students should confirm that they have reviewed, understood, and can explain the retained work before submitting it.

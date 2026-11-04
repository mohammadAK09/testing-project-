Milestone 1 - Invalid Booking Test Cases

1-Objective

The purpose of these tests is to verify that BookingManager rejects invalid booking requests before interacting with any external services.

These tests cover requirement M1-US2: Invalid Request.

2-Input Validation Rules

A booking request is considered valid only when:

Customer ID is not null, empty, or blank.

Event ID is not null, empty, or blank.

Ticket quantity is between 1 and 5, inclusive.

Price per ticket is greater than zero.

3-Invalid Test Cases
| Test ID | Invalid Input | Reason |
|---|---|---|
| TC01 | Customer ID = null | Customer identification is missing. |
| TC02 | Customer ID = "" | Customer ID is empty. |
| TC03 | Customer ID = "   " | Customer ID contains only spaces. |
| TC04 | Event ID = null | Event identification is missing. |
| TC05 | Event ID = "" | Event ID is empty. |
| TC06 | Event ID = "   " | Event ID contains only spaces. |
| TC07 | Ticket quantity = 0 | Quantity is below the allowed minimum. |
| TC08 | Ticket quantity = 6 | Quantity exceeds the allowed maximum. |
| TC09 | Ticket price = 0 | Ticket price must be positive. |
| TC10 | Ticket price = -100 | Negative ticket prices are invalid. |

All other request fields contain valid values in each test case.

4-Expected Results
For every invalid booking request:

The request must be rejected.
The payment gateway must not be called.
The notification service must not be called.
The event repository must not be called.

Our current design uses IllegalArgumentException to indicate invalid booking requests.

5-Testing Approach
JUnit 5 parameterized testing is used to execute multiple invalid input cases through a single test method.

Mockito is used to create mock dependencies and verify that no external interactions occur when invalid input is provided.

6-Execution Status
The validation test cases have been written, but their successful execution has not yet been verified.

The initial compilation failure occurred because the required production classes were not yet available on this branch.

The tests must be executed again after integrating the BookingManager implementation.

7-Limitations
The selected test cases cover representative invalid inputs but do not exhaust every possible invalid value or combination of invalid fields.

# Ticket Booking System

## Software Testing - Milestone 1

A Java 17 Ticket Booking System developed using Test-Driven Development (TDD).

## Technologies
- Java 17
- Maven
- JUnit 5
- Mockito
- JaCoCo

## Features
- Successful ticket booking
- Invalid request validation
- Sold-out event handling
- Payment failure handling

## Architecture
BookingManager uses constructor dependency injection with three interfaces:
- IPaymentGateway
- INotificationService
- IEventRepository

## Testing
18 automated tests passed with 0 failures.

JaCoCo reported 100% instruction and branch coverage for the measured code.

Run tests:
mvn clean test

## TDD
Two Red-Green-Refactor cycles are documented in the Milestone 1 report.

## Documentation
- docs/M1_REPORT_DRAFT.md
- docs/M1_VALIDATION_TEST_CASES.md

## Team
Moath and Mohammad

## Status
Milestone 1 - Final review and submission preparation.
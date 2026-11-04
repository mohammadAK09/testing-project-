package edu.course.booking;

public record BookingResult(
    boolean success,
    String transactionId
) {}
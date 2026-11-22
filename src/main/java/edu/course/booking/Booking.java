
package edu.course.booking;

public record Booking(
    String customerId,
    String eventId,
    int ticketQuantity,
    long totalAmountInCents,
    String transactionId
) {}


package edu.course.booking;

public record BookingRequest(
    String customerId,
    String eventId,
    int ticketQuantity,
    int pricePerTicketInCents
) {}

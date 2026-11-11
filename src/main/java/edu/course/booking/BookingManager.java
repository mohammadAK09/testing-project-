package edu.course.booking;

public class BookingManager {
    private final IPaymentGateway payment;
    private final INotificationService notification;
    private final IEventRepository repository;

    public BookingManager(
            IPaymentGateway payment,
            INotificationService notification,
            IEventRepository repository) {
        this.payment = payment;
        this.notification = notification;
        this.repository = repository;
    }

    public BookingResult book(BookingRequest request) {
        validateRequest(request);

        if (repository.isSoldOut(request.eventId())) {
            return new BookingResult(false, null);
        }

        long total = calculateTotalAmount(request);
        String tx = payment.processPayment(total);

        Booking booking = new Booking(
            request.customerId(),
            request.eventId(),
            request.ticketQuantity(),
            total,
            tx
        );

        repository.saveBooking(booking);
        notification.sendConfirmation(
            request.customerId(),
            request.eventId(),
            tx
        );

        return new BookingResult(true, tx);
    }

    private void validateRequest(BookingRequest request) {
        if (request == null ||
            request.customerId() == null ||
            request.customerId().isBlank() ||
            request.eventId() == null ||
            request.eventId().isBlank() ||
            request.ticketQuantity() < 1 ||
            request.ticketQuantity() > 5 ||
            request.pricePerTicketInCents() <= 0) {
            throw new IllegalArgumentException(
                "Invalid booking request");
        }
    }

    private long calculateTotalAmount(BookingRequest request) {
        return (long) request.ticketQuantity()
                * request.pricePerTicketInCents();
    }
}
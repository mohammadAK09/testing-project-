
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

        // 1. Check event availability
        if (repository.isSoldOut(request.eventId())) {
            return new BookingResult(false, null);
        }

        // 2. Calculate total amount
        long totalAmount = calculateTotalAmount(request);

        // 3. Process payment
        String transactionId = payment.processPayment(totalAmount);

        // 4. Create and save booking
        Booking booking = new Booking(
                request.customerId(),
                request.eventId(),
                request.ticketQuantity(),
                totalAmount,
                transactionId
        );

        repository.saveBooking(booking);

        // 5. Send confirmation
        notification.sendConfirmation(
                request.customerId(),
                request.eventId(),
                transactionId
        );

        // 6. Return success
        return new BookingResult(true, transactionId);
    }

    private long calculateTotalAmount(BookingRequest request) {
        return (long) request.ticketQuantity()
                * request.pricePerTicketInCents();
    }
}

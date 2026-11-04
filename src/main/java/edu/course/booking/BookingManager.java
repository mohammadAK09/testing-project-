
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

        // RED stage: booking logic not implemented yet
        return new BookingResult(false, null);
    }
}

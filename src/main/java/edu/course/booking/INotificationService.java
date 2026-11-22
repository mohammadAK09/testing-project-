
package edu.course.booking;

public interface INotificationService {

    void sendConfirmation(
        String customerId,
        String eventId,
        String transactionId
    );

}

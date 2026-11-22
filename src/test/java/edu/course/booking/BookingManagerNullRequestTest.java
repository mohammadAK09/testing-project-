
package edu.course.booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingManagerNullRequestTest {

    @Test
    void shouldRejectNullBookingRequest() {
        IPaymentGateway payment =
            mock(IPaymentGateway.class);
        INotificationService notification =
            mock(INotificationService.class);
        IEventRepository repository =
            mock(IEventRepository.class);

        BookingManager manager =
            new BookingManager(payment, notification, repository);

        assertThrows(IllegalArgumentException.class,
            () -> manager.book(null));

        verifyNoInteractions(
            payment, notification, repository);
    }
}

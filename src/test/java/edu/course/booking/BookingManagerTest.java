
package edu.course.booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingManagerTest {

    @Test
    void shouldBookTicketsSuccessfully() {

        // Arrange
        BookingRequest request =
            new BookingRequest("C101", "E101", 2, 2500);

        IPaymentGateway payment = mock(IPaymentGateway.class);
        INotificationService notification =
            mock(INotificationService.class);
        IEventRepository repository =
            mock(IEventRepository.class);

        when(repository.isSoldOut("E101")).thenReturn(false);
        when(payment.processPayment(5000L)).thenReturn("TX123");

        BookingManager manager =
            new BookingManager(payment, notification, repository);

        // Act
        BookingResult result = manager.book(request);

        // Assert
        assertTrue(result.success());
        assertEquals("TX123", result.transactionId());

        verify(repository, times(1)).isSoldOut("E101");
        verify(payment, times(1)).processPayment(5000L);
        verify(repository, times(1))
            .saveBooking(any(Booking.class));
        verify(notification, times(1))
            .sendConfirmation("C101", "E101", "TX123");
    }
}

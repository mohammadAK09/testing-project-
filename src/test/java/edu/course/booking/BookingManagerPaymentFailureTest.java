package edu.course.booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingManagerPaymentFailureTest {

    @Test
    void shouldNotSaveBookingWhenPaymentFails() {
        BookingRequest request =
            new BookingRequest("C101", "E101", 2, 2500);

        IPaymentGateway payment =
            mock(IPaymentGateway.class);
        INotificationService notification =
            mock(INotificationService.class);
        IEventRepository repository =
            mock(IEventRepository.class);

        when(repository.isSoldOut("E101"))
            .thenReturn(false);

        when(payment.processPayment(5000L))
            .thenThrow(new RuntimeException("Payment failed"));

        BookingManager manager =
            new BookingManager(payment, notification, repository);

        assertThrows(RuntimeException.class,
            () -> manager.book(request));

        verify(repository, times(1)).isSoldOut("E101");
        verify(payment, times(1)).processPayment(5000L);

        verifyNoMoreInteractions(repository, payment);
        verifyNoInteractions(notification);
    }
}
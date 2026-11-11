package edu.course.booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingManagerSoldOutTest {

    @Test
    void shouldRejectSoldOutEvent() {
        BookingRequest request =
            new BookingRequest("C101", "E101", 2, 2500);

        IPaymentGateway payment =
            mock(IPaymentGateway.class);
        INotificationService notification =
            mock(INotificationService.class);
        IEventRepository repository =
            mock(IEventRepository.class);

        when(repository.isSoldOut("E101"))
            .thenReturn(true);

        BookingManager manager =
            new BookingManager(
                payment, notification, repository);

        BookingResult result = manager.book(request);

        assertFalse(result.success());
        assertNull(result.transactionId());

        verify(repository, times(1))
            .isSoldOut("E101");

        verifyNoMoreInteractions(repository);
        verifyNoInteractions(payment, notification);
    }
}
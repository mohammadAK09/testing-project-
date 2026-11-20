
package edu.course.booking;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingManagerTest {

    @Test
    void shouldBookTicketsSuccessfully() {

        BookingRequest request =
            new BookingRequest("C101", "E101", 2, 2500);

        IPaymentGateway payment =
            mock(IPaymentGateway.class);
        INotificationService notification =
            mock(INotificationService.class);
        IEventRepository repository =
            mock(IEventRepository.class);

        when(repository.isSoldOut("E101")).thenReturn(false);
        when(payment.processPayment(5000L))
            .thenReturn("TX123");

        BookingManager manager =
            new BookingManager(
                payment, notification, repository);

        BookingResult result = manager.book(request);

        assertTrue(result.success());
        assertEquals("TX123", result.transactionId());

        ArgumentCaptor<Booking> bookingCaptor =
            ArgumentCaptor.forClass(Booking.class);

        InOrder order =
            inOrder(repository, payment, notification);

        order.verify(repository).isSoldOut("E101");
        order.verify(payment).processPayment(5000L);
        order.verify(repository)
            .saveBooking(bookingCaptor.capture());
        order.verify(notification)
            .sendConfirmation("C101", "E101", "TX123");

        order.verifyNoMoreInteractions();

        Booking savedBooking = bookingCaptor.getValue();

        assertEquals("C101", savedBooking.customerId());
        assertEquals("E101", savedBooking.eventId());
        assertEquals(2, savedBooking.ticketQuantity());
        assertEquals(5000L, savedBooking.totalAmountInCents());
        assertEquals("TX123", savedBooking.transactionId());
    }
}

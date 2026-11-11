package edu.course.booking;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingManagerValidationTest {

    static Stream<Arguments> invalidRequests() {
        return Stream.of(
            Arguments.of(null, "E101", 2, 2500),
            Arguments.of("", "E101", 2, 2500),
            Arguments.of("   ", "E101", 2, 2500),
            Arguments.of("C101", null, 2, 2500),
            Arguments.of("C101", "", 2, 2500),
            Arguments.of("C101", "   ", 2, 2500),
            Arguments.of("C101", "E101", 0, 2500),
            Arguments.of("C101", "E101", 6, 2500),
            Arguments.of("C101", "E101", 2, 0),
            Arguments.of("C101", "E101", 2, -100)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidRequests")
    void shouldRejectInvalidBookingRequests(
            String customerId,
            String eventId,
            int quantity,
            int price) {

        IPaymentGateway payment = mock(IPaymentGateway.class);
        INotificationService notification = mock(INotificationService.class);
        IEventRepository repository = mock(IEventRepository.class);

        BookingRequest request = new BookingRequest(
                customerId, eventId, quantity, price);

        BookingManager manager = new BookingManager(
                payment, notification, repository);

        assertThrows(IllegalArgumentException.class,
                () -> manager.book(request));

        verifyNoInteractions(payment, notification, repository);
    }
}
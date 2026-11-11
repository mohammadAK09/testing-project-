
package edu.course.booking;

public interface IPaymentGateway {

    String processPayment(long amountInCents);

}

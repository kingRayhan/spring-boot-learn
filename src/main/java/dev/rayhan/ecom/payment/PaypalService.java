package dev.rayhan.ecom.payment;

import org.springframework.stereotype.Service;

//@Service("paypal")
public class PaypalService implements PaymentService {

    @Override
    public void processPayment() {
        System.out.println("Processing payment with Paypal");
    }
}

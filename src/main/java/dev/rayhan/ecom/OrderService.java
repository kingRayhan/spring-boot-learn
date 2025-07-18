package dev.rayhan.ecom;

import dev.rayhan.ecom.payment.PaymentService;
import dev.rayhan.ecom.payment.PaypalService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



public class OrderService {
    PaymentService paymentService;

    public OrderService(
            PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        System.out.println("Placing order...");
        paymentService.processPayment();
    }
}

package dev.rayhan.ecom.payment;

//@Service("ssl")
public class SSLCommerce implements PaymentService{
    @Override
    public void processPayment() {
        System.out.println( "Processing payment with SSLCommerce");
    }
}

package dev.rayhan.ecom;

import dev.rayhan.ecom.payment.PaypalService;
import dev.rayhan.ecom.payment.SSLCommerce;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${payment.gateway}")
    private String paymentGateway;

    @Bean
    public OrderService orderService(){
        if(paymentGateway.equalsIgnoreCase("paypal")){
            return new OrderService(new PaypalService());
        }

        return new OrderService(new SSLCommerce());
    }
}

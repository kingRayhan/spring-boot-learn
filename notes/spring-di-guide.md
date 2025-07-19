# Spring Dependency Injection - Complete Guide

## 1. Annotation-Based DI

### Core Annotations
- `@Component` - Generic Spring component
- `@Service` - Business logic layer
- `@Repository` - Data access layer
- `@Controller` - Web layer

```java
@Service
public class UserService {
    // Spring manages this bean
}
```

### Constructor Injection (Recommended)
```java
@Service
public class OrderService {
    private final PaymentService paymentService;
    
    // Constructor injection - no @Autowired needed in Spring 4.3+
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

### Field Injection
```java
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;
}
```

### Setter Injection
```java
@Service
public class OrderService {
    private PaymentService paymentService;
    
    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

## 2. Handling Multiple Implementations

### @Qualifier
```java
@Service("email")
public class EmailService implements NotificationService {}

@Service("sms") 
public class SmsService implements NotificationService {}

@Service
public class NotificationManager {
    public NotificationManager(@Qualifier("email") NotificationService service) {
        this.service = service;
    }
}
```

### @Primary
```java
@Service
@Primary
public class EmailService implements NotificationService {}

@Service
public class SmsService implements NotificationService {}

// EmailService will be injected by default
```

## 3. Java Configuration (@Bean)

### Basic Configuration
```java
@Configuration
public class AppConfig {
    
    @Bean
    public PaymentService paymentService() {
        return new PayPalService();
    }
    
    @Bean
    public OrderService orderService() {
        return new OrderService(paymentService());
    }
}
```

### Conditional Bean Creation
```java
@Configuration
public class AppConfig {
    
    @Value("${payment.provider}")
    private String paymentProvider;
    
    @Bean
    public PaymentService paymentService() {
        if ("paypal".equals(paymentProvider)) {
            return new PayPalService();
        }
        return new StripeService();
    }
}
```

## 4. Bean Scopes

### Singleton (Default)
```java
@Service
@Scope("singleton")  // Optional, default behavior
public class UserService {
    // One instance per Spring container
}
```

### Prototype
```java
@Service
@Scope("prototype")
public class ShoppingCart {
    // New instance every time requested
}
```

### Request (Web Applications)
```java
@Service
@Scope("request")
public class RequestContext {
    // One instance per HTTP request
}
```

### Session (Web Applications)
```java
@Service
@Scope("session")
public class UserSession {
    // One instance per HTTP session
}
```

### Custom Scope Usage
```java
@Component
public class ScopeDemo {
    
    @Autowired
    private ApplicationContext context;
    
    public void demonstrateScopes() {
        // Singleton - same instance
        UserService user1 = context.getBean(UserService.class);
        UserService user2 = context.getBean(UserService.class);
        // user1 == user2 (true)
        
        // Prototype - different instances
        ShoppingCart cart1 = context.getBean(ShoppingCart.class);
        ShoppingCart cart2 = context.getBean(ShoppingCart.class);
        // cart1 != cart2 (true)
    }
}
```

## 5. @Value Annotation

### Simple Property Injection
```java
@Service
public class EmailService {
    
    @Value("${email.host}")
    private String host;
    
    @Value("${email.port}")
    private int port;
    
    @Value("${email.username}")
    private String username;
}
```

### With Default Values
```java
@Service
public class PaymentService {
    
    @Value("${payment.timeout:30}")
    private int timeout; // Default 30 if property not found
    
    @Value("${payment.currency:USD}")
    private String currency; // Default USD
}
```

### Constructor with @Value
```java
@Service
public class DatabaseService {
    
    private final String url;
    private final String username;
    
    public DatabaseService(
            @Value("${db.url}") String url,
            @Value("${db.username}") String username) {
        this.url = url;
        this.username = username;
    }
}
```

### application.properties
```properties
email.host=smtp.gmail.com
email.port=587
email.username=noreply@company.com

payment.timeout=45
payment.currency=EUR
payment.provider=paypal

db.url=jdbc:mysql://localhost:3306/ecom
db.username=root
```

### application.yml
```yaml
email:
  host: smtp.gmail.com
  port: 587
  username: noreply@company.com

payment:
  timeout: 45
  currency: EUR
  provider: paypal

db:
  url: jdbc:mysql://localhost:3306/ecom
  username: root
```

## 6. Best Practices

### 1. Prefer Constructor Injection
```java
// Good
@Service
public class OrderService {
    private final PaymentService paymentService;
    
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}

// Avoid
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;
}
```

### 2. Use Interfaces for Loose Coupling
```java
// Interface
public interface PaymentService {
    void processPayment(double amount);
}

// Implementations
@Service
public class PayPalService implements PaymentService {
    public void processPayment(double amount) {
        // PayPal logic
    }
}

@Service
public class StripeService implements PaymentService {
    public void processPayment(double amount) {
        // Stripe logic
    }
}
```

### 3. Validate Required Properties
```java
@Service
public class EmailService {
    
    @Value("${email.host}")
    private String host;
    
    @PostConstruct
    public void validateConfig() {
        if (host == null || host.trim().isEmpty()) {
            throw new IllegalStateException("Email host is required");
        }
    }
}
```

## 7. Common Patterns

### Factory Pattern with DI
```java
@Component
public class PaymentServiceFactory {
    
    private final Map<String, PaymentService> services;
    
    public PaymentServiceFactory(List<PaymentService> services) {
        this.services = services.stream()
            .collect(Collectors.toMap(
                service -> service.getClass().getSimpleName(),
                service -> service
            ));
    }
    
    public PaymentService getPaymentService(String type) {
        return services.get(type + "Service");
    }
}
```

### Configuration Properties
```java
@ConfigurationProperties(prefix = "app")
@Component
public class AppProperties {
    private String name;
    private String version;
    private Database database = new Database();
    
    // getters and setters
    
    public static class Database {
        private String url;
        private String username;
        // getters and setters
    }
}
```

## Summary
- **Annotations**: Use `@Service`, `@Component` for auto-detection
- **Injection**: Prefer constructor injection for required dependencies
- **Multiple Beans**: Use `@Qualifier` or `@Primary` to resolve conflicts
- **Configuration**: Use `@Configuration` + `@Bean` for complex setup
- **Scopes**: Choose appropriate scope based on use case
- **Properties**: Use `@Value` for external configuration
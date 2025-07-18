# Dependency Injection in Spring - Practice Notes

## Key Concepts Learned

### 1. Constructor Injection
- Dependencies are provided through class constructors
- Example: `OrderService.java:14-17` - PaymentService injected via constructor
- Example: `NotificationManager.java:10-15` - NotificationService injected via constructor

### 2. Annotation-Based DI
- `@Service` - Marks classes as Spring-managed beans
- `@Qualifier("name")` - Specifies which bean to inject when multiple implementations exist
- `@Value("${property}")` - Injects values from configuration files
- `@Configuration` + `@Bean` - Manual bean creation and configuration

### 3. Interface-Based Design
- Services implement interfaces (NotificationService, PaymentService)
- Enables loose coupling and easy testing
- Multiple implementations can be swapped easily

### 4. Practical Examples from Code

#### Qualifier Usage
```java
// NotificationManager.java:11-12
@Qualifier("email")
NotificationService notificationService
```
- Chooses EmailNotificationService over SmsNotificationService

#### Named Services
```java
// EmailNotificationService.java:5
@Service("email")

// SmsNotificationService.java:5  
@Service("sms")
```

#### Configuration-Based Injection
```java
// AppConfig.java:15-22
@Bean
public OrderService orderService(){
    if(paymentGateway.equalsIgnoreCase("paypal")){
        return new OrderService(new PaypalService());
    }
    return new OrderService(new SSLCommerce());
}
```

## Benefits Practiced
- **Loose Coupling**: Services depend on interfaces, not concrete classes
- **Testability**: Easy to mock dependencies
- **Flexibility**: Switch implementations via configuration
- **Maintainability**: Changes isolated to specific components
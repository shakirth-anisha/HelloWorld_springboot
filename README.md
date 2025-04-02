# Spring Boot Hello World Project

## Table of Contents
1. [Introduction](#introduction)
2. [Project Structure](#project-structure)
3. [Dependencies](#dependencies)
4. [Code Breakdown](#code-breakdown)
   - [HomeController.java](#homecontrollerjava)
   - [StoreApplication.java](#storeapplicationjava)
   - [OrderService.java](#orderservicejava)
   - [PaymentService.java](#paymentservicejava)
   - [StripePaymentService.java](#stripepaymentservicejava)
   - [PayPalPaymentService.java](#paypalpaymentservicejava)
5. [How to Run](#how-to-run)
6. [Notes](#notes)

## Introduction
This is a simple Spring Boot application demonstrating core concepts like dependency injection, components, services, and bean management. You can use https://start.spring.io to initialize your project. The settings used are: 
- Project: Maven
- Language: Java
- Spring Boot: 3.4.4 (Latest Stable Version at the time)
- Project Metadata:
    - Group: com.first
    - Artifact: store
    - Name: store (default)
    - Description: Demo project for Spring Boot (default)
    - Package Name: com.first.store (default)
    - Packaging: Jar
    - Java: 24 (Latest Stable Version at the time)

## Project Structure
```
/store
├── src/main/java/com/first/store
│   ├── StoreApplication.java
│   ├── HomeController.java
│   ├── OrderService.java
│   ├── PaymentService.java
│   ├── StripePaymentService.java
│   ├── PayPalPaymentService.java
├── src/main/resources/static/index.html
├── pom.xml
```

## Dependencies
We use the following dependencies in `pom.xml`:
- `spring-boot-starter` → Core Spring Boot features.
- `spring-boot-starter-web` → For building web applications.
- `spring-boot-starter-test` → For testing support.
- `spring-boot-devtools` → For automatic application restart on changes.

All of these can be found on https://central.sonatype.com

## Code Breakdown
### HomeController.java
Handles HTTP requests and returns the home page.
```java
@Controller
public class HomeController {
    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping("/")
    public String index(){
        System.out.println("appName: " + appName);
        return "index.html";
    }
}
```
- `@Controller` → Marks the class as a Spring MVC controller.
- `@RequestMapping("/")` → Maps the root URL to the `index()` method.
- `@Value("${spring.application.name}")` → Injects the application name from properties.

### StoreApplication.java
The main entry point of the application.
```java
@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StoreApplication.class, args);
        var orderService = context.getBean(OrderService.class);
        orderService.placeOrder();
    }
}
```
- `@SpringBootApplication` → Enables component scanning, auto-configuration, and configuration properties.
- `ApplicationContext context = SpringApplication.run(...)` → Retrieves the Spring application context.
- `context.getBean(OrderService.class)` → Gets an instance of `OrderService`.

### OrderService.java
A service handling orders and processing payments.
```java
@Service
public class OrderService {
    private PaymentService paymentService;

    public OrderService(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    public void placeOrder(){
        paymentService.processPayment(10);
    }
}
```
- `@Service` → Marks it as a business service component.
- Uses constructor injection for `PaymentService`.

### PaymentService.java
An interface defining a payment processing method.
```java
public interface PaymentService {
    void processPayment(double amount);
}
```

### StripePaymentService.java
A concrete implementation of `PaymentService`.
```java
public class StripePaymentService implements PaymentService {
    @Override
    public void processPayment(double amount){
        System.out.println("STRIPE");
        System.out.println("AMOUNT: " + amount);
    }
}
```

### PayPalPaymentService.java
Another implementation using PayPal.
```java
@Service
public class PayPalPaymentService implements PaymentService{
    @Override
    public void processPayment(double amount) {
        System.out.println("PAYPAL");
        System.out.println("AMOUNT: " + amount);
    }
}
```
- `@Service` → This marks it as a Spring-managed service.

## How to Run
1. Ensure Java and Maven are installed.
2. Run:
   ```sh
   ./mvnw spring-boot:run
   ```
3. Open `http://localhost:8080/` in a browser.

## Notes
### Concepts Used
1. **Spring Boot**: A framework for building standalone applications with minimal configuration.
2. **Dependency Injection (DI)**: Injecting dependencies instead of manually creating objects.
3. **Beans & Component Scanning**:
   - `@Component`, `@Service`, `@Repository`, `@Controller` → All are Spring-managed components.
   - `@SpringBootApplication` enables scanning for these components.
4. **ApplicationContext**: A container managing Spring Beans.
5. **Constructor Injection**: Injecting dependencies via constructors.
6. **Interface-based Programming**: `PaymentService` is an interface implemented by `StripePaymentService` and `PayPalPaymentService`.
7. **Loose Coupling**: The `OrderService` depends on `PaymentService`, allowing different implementations (Stripe/PayPal) without modifying `OrderService`.
8. **Spring Boot DevTools**: Auto-restarts the app when code changes.
9. **Spring MVC**:
   - `@Controller` handles HTTP requests.
   - `@RequestMapping("/")` maps the root URL to a method.
10. **Configuration Properties**:
    - `@Value("${spring.application.name}")` reads values from properties.

This project serves as a foundation for understanding Spring Boot's core features!


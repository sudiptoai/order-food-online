# Order Food Online - Backend Service

A scalable backend service for online food ordering system built with Java Spring Boot, following SOLID principles and implementing various design patterns.

## Architecture Overview

This application is designed with a clean, layered architecture:
- **Controller Layer**: REST API endpoints
- **Service Layer**: Business logic implementation
- **Repository Layer**: Data access using Spring Data JPA
- **Model Layer**: Domain entities
- **Pattern Layer**: Design pattern implementations

## Key Players

### 1. Customer
- Register as a user
- Browse restaurants and food items
- Add items to cart
- Place orders with multiple payment options (Credit Card, UPI, Cash on Delivery)
- Track order status

### 2. Restaurant Owner
- Manage restaurant details
- Add/update/remove food items
- Set pricing and availability
- View incoming orders

### 3. Admin
- Add/remove restaurants
- Check order feasibility
- Monitor delivery persons
- System-wide oversight

### 4. Delivery Person
- Register and manage profile
- Update delivery status and location
- View assigned orders

## Core Features

### Search Functionality
1. **Restaurant Search**
   - Search by name
   - Search by location
   - Combined search (name + location)

2. **Food Item Search**
   - Search food items across all restaurants
   - Lists all restaurants that deliver that item

### Order Management
- Complete cart functionality
- Multiple payment options
- Order status tracking
- Delivery person assignment

## Design Patterns Implemented

### 1. Strategy Pattern
**Payment Processing**: Different payment strategies for various payment methods
- `CreditCardPaymentStrategy`
- `UpiPaymentStrategy`
- `CashOnDeliveryStrategy`
- `PaymentStrategyFactory` - Factory for selecting appropriate strategy

**Location**: `com.foodorder.pattern`

### 2. Observer Pattern
**Order Notifications**: Observers notified on order status changes
- `CustomerNotificationObserver`
- `RestaurantNotificationObserver`
- `OrderNotificationService` - Subject that notifies observers

**Location**: `com.foodorder.pattern`

### 3. Repository Pattern
All data access through repository interfaces extending JpaRepository
- Abstraction over data layer
- Easy to mock for testing
- Clean separation of concerns

**Location**: `com.foodorder.repository`

### 4. Factory Pattern
`PaymentStrategyFactory` creates appropriate payment strategy based on payment method

### 5. Dependency Injection
Spring's IoC container manages all dependencies, promoting loose coupling

## SOLID Principles

### Single Responsibility Principle (SRP)
Each class has one reason to change:
- Services handle specific business logic
- Controllers handle HTTP requests/responses
- Repositories handle data access
- Models represent domain entities

### Open/Closed Principle (OCP)
- Payment strategies can be extended without modifying existing code
- New observers can be added without changing notification service
- New search criteria can be added without modifying search service

### Liskov Substitution Principle (LSP)
- All payment strategies implement `PaymentStrategy` interface
- All observers implement `OrderObserver` interface
- Implementations can be substituted without breaking functionality

### Interface Segregation Principle (ISP)
- Focused interfaces (e.g., `PaymentStrategy`, `OrderObserver`)
- Clients depend only on methods they use

### Dependency Inversion Principle (DIP)
- Services depend on repository interfaces, not concrete implementations
- High-level modules (services) don't depend on low-level modules (repositories)
- Both depend on abstractions (interfaces)

## Technology Stack

- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **Database**: H2 (in-memory, easily replaceable with PostgreSQL/MySQL)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Data Validation**: Spring Validation

## Project Structure

```
src/main/java/com/foodorder/
├── OrderFoodOnlineApplication.java  # Main application class
├── controller/                       # REST API endpoints
│   ├── AdminController.java
│   ├── CartController.java
│   ├── DeliveryController.java
│   ├── FoodItemController.java
│   ├── OrderController.java
│   ├── RestaurantController.java
│   ├── SearchController.java
│   └── UserController.java
├── dto/                             # Data Transfer Objects
│   ├── AddToCartDto.java
│   ├── FoodItemDto.java
│   ├── PlaceOrderDto.java
│   ├── RestaurantDto.java
│   ├── SearchDto.java
│   └── UserRegistrationDto.java
├── enums/                           # Enumerations
│   ├── DeliveryStatus.java
│   ├── OrderStatus.java
│   ├── PaymentMethod.java
│   └── UserRole.java
├── model/                           # Domain entities
│   ├── Cart.java
│   ├── CartItem.java
│   ├── DeliveryPerson.java
│   ├── FoodItem.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Restaurant.java
│   └── User.java
├── pattern/                         # Design patterns
│   ├── CashOnDeliveryStrategy.java
│   ├── CreditCardPaymentStrategy.java
│   ├── CustomerNotificationObserver.java
│   ├── OrderNotificationService.java
│   ├── OrderObserver.java
│   ├── PaymentStrategy.java
│   ├── PaymentStrategyFactory.java
│   ├── RestaurantNotificationObserver.java
│   └── UpiPaymentStrategy.java
├── repository/                      # Data access layer
│   ├── CartItemRepository.java
│   ├── CartRepository.java
│   ├── DeliveryPersonRepository.java
│   ├── FoodItemRepository.java
│   ├── OrderItemRepository.java
│   ├── OrderRepository.java
│   ├── RestaurantRepository.java
│   └── UserRepository.java
└── service/                         # Business logic layer
    ├── AdminService.java
    ├── CartService.java
    ├── DeliveryService.java
    ├── FoodItemService.java
    ├── OrderService.java
    ├── RestaurantService.java
    ├── SearchService.java
    └── UserService.java
```

## API Endpoints

### User Management
- `POST /api/users/register` - Register new user
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `GET /api/users/role/{role}` - Get users by role
- `PUT /api/users/{id}` - Update user

### Restaurant Management
- `POST /api/restaurants` - Add restaurant
- `GET /api/restaurants` - Get all active restaurants
- `GET /api/restaurants/{id}` - Get restaurant by ID
- `GET /api/restaurants/search/name/{name}` - Search by name
- `GET /api/restaurants/search/location/{location}` - Search by location
- `PUT /api/restaurants/{id}` - Update restaurant
- `DELETE /api/restaurants/{id}` - Delete restaurant

### Food Item Management
- `POST /api/food-items` - Add food item
- `GET /api/food-items/restaurant/{restaurantId}` - Get items by restaurant
- `GET /api/food-items/{id}` - Get food item by ID
- `GET /api/food-items/search/{name}` - Search food items
- `PUT /api/food-items/{id}` - Update food item
- `PATCH /api/food-items/{id}/availability` - Update availability
- `DELETE /api/food-items/{id}` - Delete food item

### Cart Management
- `GET /api/cart/{customerId}` - Get customer's cart
- `POST /api/cart/add` - Add item to cart
- `DELETE /api/cart/{customerId}/item/{foodItemId}` - Remove item from cart
- `DELETE /api/cart/{customerId}/clear` - Clear cart

### Order Management
- `POST /api/orders` - Place order
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/customer/{customerId}` - Get customer orders
- `GET /api/orders/restaurant/{restaurantId}` - Get restaurant orders
- `GET /api/orders/status/{status}` - Get orders by status
- `PATCH /api/orders/{orderId}/status` - Update order status
- `PATCH /api/orders/{orderId}/assign-delivery/{deliveryPersonId}` - Assign delivery person

### Search
- `POST /api/search/restaurants` - Search restaurants (name/location)
- `GET /api/search/food-items/{itemName}` - Search food items across restaurants

### Admin Operations
- `DELETE /api/admin/restaurants/{restaurantId}` - Deactivate restaurant
- `DELETE /api/admin/restaurants/{restaurantId}/permanent` - Delete restaurant permanently
- `GET /api/admin/restaurants` - Get all restaurants
- `GET /api/admin/orders/{orderId}/feasibility` - Check order feasibility
- `GET /api/admin/delivery-persons` - Monitor delivery persons

### Delivery Management
- `POST /api/delivery/register` - Register delivery person
- `GET /api/delivery/available` - Get available delivery persons
- `PATCH /api/delivery/{deliveryPersonId}/status` - Update delivery status
- `PATCH /api/delivery/{deliveryPersonId}/location` - Update location
- `GET /api/delivery/{deliveryPersonId}/orders` - Get delivery person orders

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Access H2 Console
Access the H2 database console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:foodorderdb`
- Username: `sa`
- Password: (leave empty)

## Testing

Run tests with:
```bash
mvn test
```

## Scalability Considerations

1. **Database**: H2 is used for development; easily swap to PostgreSQL/MySQL for production
2. **Caching**: Can add Redis for caching frequently accessed data
3. **Message Queue**: Can integrate RabbitMQ/Kafka for async processing
4. **Microservices**: Services are loosely coupled and can be split into microservices
5. **API Gateway**: Can add Spring Cloud Gateway for routing
6. **Load Balancing**: Application is stateless and can be horizontally scaled

## Future Enhancements

- JWT-based authentication and authorization
- Real-time order tracking with WebSockets
- Rating and review system
- Promotional codes and discounts
- Advanced search with filters
- Email/SMS notifications
- Payment gateway integration
- Image upload for food items
- Analytics and reporting

## License

This project is licensed under the MIT License - see the LICENSE file for details.
# Find Your Keeb - Mechanical Keyboard E-commerce Backend

A Spring Boot application for selling mechanical keyboards with comprehensive e-commerce functionality.

## Features

### 1. Authentication and Authorization
- JWT-based authentication
- User registration and login
- Role-based access control (USER, ADMIN)
- Secure password encryption

### 2. Admin Management
- Product CRUD operations
- Order management and status updates
- Inventory management
- Sales analytics

### 3. Product Management
- Comprehensive product catalog
- Advanced filtering by:
  - Keyboard layout (Full-size, TKL, 65%, 60%, etc.)
  - Price range
  - Brand
  - Switch type
  - RGB support
  - Wireless support
- Search functionality
- Pagination support

### 4. Shopping Cart & Checkout
- Add/remove items from cart
- Update quantities
- Cart persistence
- Secure checkout process
- Order confirmation

### 5. API Documentation
- Interactive Swagger UI
- OpenAPI 3 specification
- Detailed endpoint documentation
- Request/response examples
- Authentication integration

## Technology Stack

- **Framework**: Spring Boot 3.5.3
- **Database**: MySQL 8.0
- **Security**: Spring Security with JWT
- **ORM**: Spring Data JPA with Hibernate
- **API Documentation**: Swagger/OpenAPI 3
- **Build Tool**: Maven
- **Java Version**: 17

## API Documentation

### Swagger UI
Once the application is running, you can access the interactive API documentation at:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **OpenAPI YAML**: http://localhost:8080/api-docs.yaml

### Features of the Documentation
- **Interactive Testing**: Test API endpoints directly from the browser
- **Authentication**: JWT token integration for protected endpoints
- **Request/Response Examples**: Detailed examples for all endpoints
- **Schema Documentation**: Complete model documentation
- **Error Responses**: Comprehensive error documentation

### Using Swagger UI
1. Open http://localhost:8080/swagger-ui.html in your browser
2. For protected endpoints, click the "Authorize" button
3. Enter your JWT token in the format: `Bearer <your-token>`
4. Test endpoints directly from the interface

## Database Schema

### Core Entities
- **User**: Customer and admin accounts
- **Product**: Mechanical keyboard products
- **Cart**: Shopping cart for users
- **CartItem**: Individual items in cart
- **Order**: Customer orders
- **OrderItem**: Individual items in orders

### Enums
- **Role**: USER, ADMIN
- **KeyboardLayout**: FULL_SIZE, TKL, SEVENTY_FIVE_PERCENT, SIXTY_FIVE_PERCENT, SIXTY_PERCENT, FORTY_PERCENT, SPLIT, ORTHOLINEAR, CUSTOM
- **OrderStatus**: PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, REFUNDED

## API Endpoints

### Authentication
```
POST /api/auth/register - Register new user
POST /api/auth/login - User login
```

### Products (Public)
```
GET /api/products - Get all products with filtering
GET /api/products/{id} - Get product by ID
GET /api/products/brands - Get all available brands
GET /api/products/layouts - Get all keyboard layouts
```

### Admin Operations
```
POST /api/admin/products - Create new product
PUT /api/admin/products/{id} - Update product
DELETE /api/admin/products/{id} - Delete product
GET /api/admin/orders - Get all orders
GET /api/admin/orders/{id} - Get order by ID
PUT /api/admin/orders/{id}/status - Update order status
GET /api/admin/orders/status/{status} - Get orders by status
```

### Cart (Authenticated Users)
```
GET /api/cart - Get user's cart
POST /api/cart/items - Add item to cart
PUT /api/cart/items/{productId} - Update item quantity
DELETE /api/cart/items/{productId} - Remove item from cart
DELETE /api/cart - Clear cart
```

### Orders (Authenticated Users)
```
GET /api/orders - Get user's orders
GET /api/orders/{id} - Get order by ID
POST /api/orders/checkout - Checkout cart
```

## Setup Instructions

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE find_your_keeb;
```

2. Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/find_your_keeb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application
1. Clone the repository
2. Navigate to project directory
3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Default Admin Account
- Username: `admin`
- Password: `admin123`

## Sample API Usage

### Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "password123",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "password123"
  }'
```

### Get products with filters
```bash
curl "http://localhost:8080/api/products?layout=TKL&minPrice=100&maxPrice=200&brand=Ducky"
```

### Add item to cart (requires authentication)
```bash
curl -X POST http://localhost:8080/api/cart/items \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2
  }'
```

### Checkout (requires authentication)
```bash
curl -X POST http://localhost:8080/api/orders/checkout \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "shippingAddress": "123 Main St, City, State 12345",
    "billingAddress": "123 Main St, City, State 12345",
    "paymentMethod": "Credit Card"
  }'
```

## Product Filtering Options

### Layout Filter
- `FULL_SIZE` - Full-size keyboards (104+ keys)
- `TKL` - Tenkeyless keyboards (87 keys)
- `SEVENTY_FIVE_PERCENT` - 75% keyboards
- `SIXTY_FIVE_PERCENT` - 65% keyboards
- `SIXTY_PERCENT` - 60% keyboards
- `FORTY_PERCENT` - 40% keyboards
- `SPLIT` - Split keyboards
- `ORTHOLINEAR` - Ortholinear keyboards
- `CUSTOM` - Custom layouts

### Price Range
- `minPrice` - Minimum price filter
- `maxPrice` - Maximum price filter

### Additional Filters
- `brand` - Filter by brand name
- `switchType` - Filter by switch type
- `rgbSupport` - Filter keyboards with RGB lighting
- `wirelessSupport` - Filter wireless keyboards
- `searchTerm` - Search by product name

## Security Features

- JWT token-based authentication
- Password encryption with BCrypt
- Role-based authorization
- CORS configuration
- Input validation
- SQL injection prevention

## Error Handling

The application includes comprehensive error handling for:
- Authentication failures
- Authorization violations
- Invalid input data
- Resource not found
- Business logic violations
- Database errors

## Future Enhancements

- Payment gateway integration
- Email notifications
- Product reviews and ratings
- Wishlist functionality
- Advanced analytics
- Inventory alerts
- Bulk operations
- API rate limiting 
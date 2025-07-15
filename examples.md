# API Examples - Admin JWT Token and Orders

## Method 1: Using PowerShell/curl

### Step 1: Get Admin JWT Token

```powershell
# Login as admin and get JWT token
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body '{"username": "admin", "password": "admin123"}'

# Extract the token
$token = $response.token
Write-Host "JWT Token: $token"
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1MTE2NDg0MywiZXhwIjoxNzUxMjUxMjQzfQ.5OwxfxAljJI4hbr2FHrRsqnB0uCN7QN5vu6mt1Vn8Pg",
    "type": "Bearer",
    "id": 1,
    "username": "admin",
    "email": "admin@findyourkeeb.com",
    "role": "ADMIN"
}
```

### Step 2: Get All Orders (Admin)

```powershell
# Get all orders with pagination
$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

$orders = Invoke-RestMethod -Uri "http://localhost:8080/api/admin/orders?page=0&size=10" -Method GET -Headers $headers
$orders | ConvertTo-Json -Depth 10
```

### Step 3: Get Orders by Status

```powershell
# Get pending orders
$pendingOrders = Invoke-RestMethod -Uri "http://localhost:8080/api/admin/orders/status/PENDING?page=0&size=10" -Method GET -Headers $headers
$pendingOrders | ConvertTo-Json -Depth 10

# Get confirmed orders
$confirmedOrders = Invoke-RestMethod -Uri "http://localhost:8080/api/admin/orders/status/CONFIRMED?page=0&size=10" -Method GET -Headers $headers
$confirmedOrders | ConvertTo-Json -Depth 10
```

### Step 4: Get Specific Order by ID

```powershell
# Get order by ID (replace 1 with actual order ID)
$order = Invoke-RestMethod -Uri "http://localhost:8080/api/admin/orders/1" -Method GET -Headers $headers
$order | ConvertTo-Json -Depth 10
```

### Step 5: Update Order Status

```powershell
# Update order status to CONFIRMED
$updatedOrder = Invoke-RestMethod -Uri "http://localhost:8080/api/admin/orders/1/status?status=CONFIRMED" -Method PUT -Headers $headers
$updatedOrder | ConvertTo-Json -Depth 10
```

## Method 2: Using curl (Linux/Mac/Git Bash)

### Step 1: Get Admin JWT Token

```bash
# Login as admin
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### Step 2: Get All Orders

```bash
# Store token in variable (replace with actual token from step 1)
TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1MTE2NDg0MywiZXhwIjoxNzUxMjUxMjQzfQ.5OwxfxAljJI4hbr2FHrRsqnB0uCN7QN5vu6mt1Vn8Pg"

# Get all orders
curl -X GET "http://localhost:8080/api/admin/orders?page=0&size=10" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

### Step 3: Get Orders by Status

```bash
# Get pending orders
curl -X GET "http://localhost:8080/api/admin/orders/status/PENDING?page=0&size=10" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"

# Get confirmed orders
curl -X GET "http://localhost:8080/api/admin/orders/status/CONFIRMED?page=0&size=10" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

## Method 3: Using Swagger UI

### Step 1: Access Swagger UI
1. Open your browser and go to: `http://localhost:8080/swagger-ui.html`
2. You'll see all available endpoints organized by tags

### Step 2: Get Admin Token
1. Find the **Authentication** section
2. Click on `POST /api/auth/login`
3. Click "Try it out"
4. Enter the request body:
```json
{
  "username": "admin",
  "password": "admin123"
}
```
5. Click "Execute"
6. Copy the token from the response

### Step 3: Authorize in Swagger UI
1. Click the "Authorize" button at the top of the page
2. In the "Bearer Authentication" field, enter: `Bearer <your-token>`
3. Click "Authorize"
4. Close the authorization dialog

### Step 4: Test Admin Endpoints
1. Go to the **Admin** section
2. Test the following endpoints:
   - `GET /api/admin/orders` - Get all orders
   - `GET /api/admin/orders/status/{status}` - Get orders by status
   - `GET /api/admin/orders/{id}` - Get specific order
   - `PUT /api/admin/orders/{id}/status` - Update order status

## Method 4: Using JavaScript/Fetch

### Step 1: Get Admin Token

```javascript
// Login as admin
const loginResponse = await fetch('http://localhost:8080/api/auth/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    username: 'admin',
    password: 'admin123'
  })
});

const loginData = await loginResponse.json();
const token = loginData.token;
console.log('JWT Token:', token);
```

### Step 2: Get All Orders

```javascript
// Get all orders
const ordersResponse = await fetch('http://localhost:8080/api/admin/orders?page=0&size=10', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  }
});

const orders = await ordersResponse.json();
console.log('Orders:', orders);
```

### Step 3: Get Orders by Status

```javascript
// Get pending orders
const pendingOrdersResponse = await fetch('http://localhost:8080/api/admin/orders/status/PENDING?page=0&size=10', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  }
});

const pendingOrders = await pendingOrdersResponse.json();
console.log('Pending Orders:', pendingOrders);
```

## Expected Response Format

### Orders Response
```json
{
  "content": [
    {
      "id": 1,
      "orderNumber": "ORD-ABC12345",
      "user": {
        "id": 2,
        "username": "john_doe",
        "email": "john@example.com",
        "firstName": "John",
        "lastName": "Doe"
      },
      "items": [
        {
          "id": 1,
          "product": {
            "id": 1,
            "name": "Cherry MX Board 3.0",
            "price": 89.99,
            "brand": "Cherry"
          },
          "quantity": 2,
          "unitPrice": 89.99
        }
      ],
      "totalAmount": 179.98,
      "status": "PENDING",
      "shippingAddress": "123 Main St, City, State 12345",
      "billingAddress": "123 Main St, City, State 12345",
      "paymentMethod": "Credit Card",
      "createdAt": "2024-01-15T10:30:00",
      "updatedAt": "2024-01-15T10:30:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false
    }
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "numberOfElements": 1
}
```

## Available Order Statuses

- `PENDING` - Order placed but not yet confirmed
- `CONFIRMED` - Order confirmed by admin
- `PROCESSING` - Order is being processed
- `SHIPPED` - Order has been shipped
- `DELIVERED` - Order has been delivered
- `CANCELLED` - Order has been cancelled
- `REFUNDED` - Order has been refunded

## Error Responses

### 401 Unauthorized
```json
{
  "error": "User not authenticated"
}
```

### 403 Forbidden
```json
{
  "error": "Access denied - Admin role required"
}
```

### 404 Not Found
```json
{
  "error": "Order not found"
}
```

## Notes

1. **Token Expiration**: JWT tokens expire after 24 hours (86400000 ms)
2. **Pagination**: All order endpoints support pagination with `page` and `size` parameters
3. **Admin Role**: Only users with ADMIN role can access admin endpoints
4. **CORS**: The API supports CORS for frontend integration
5. **Validation**: All endpoints include input validation and proper error handling 
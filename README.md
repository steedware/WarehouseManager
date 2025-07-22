# Warehouse Management System

## Description

A comprehensive warehouse management system written in Java using Spring Boot. The application enables full management of products, warehouse locations, tracking of inventory movements, and report generation.

## Tech Stack

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Swagger/OpenAPI**
- **Docker & Docker Compose**
- **Maven**

## Features

### Product Management
- Add, edit, delete products
- Search by name and SKU
- Inventory level control
- Low stock alerts

### Warehouse Locations
- Manage warehouse locations
- Assign products to locations
- Search by location codes

### Inventory Movement History
- Track all warehouse operations
- Movement types: Inbound, Outbound, Transfer, Adjustment, Return
- Automatic inventory updates
- Filtering by date, product, location

### Reporting System
- Export to CSV
- Report of all products
- Report of low stock products
- Report of inventory movements with filtering

### API Documentation
- Full Swagger/OpenAPI documentation
- Available at: `http://localhost:8080/swagger-ui.html`

## Getting Started

### Option 1: Docker Compose (Recommended)

```bash
# Clone the repository
git clone https://github.com/steedware/WarehouseManager.git
cd WarehouseManager

# Build the application
mvn clean package -DskipTests

# Run with Docker Compose
docker-compose up -d
```

### Option 2: Local Setup

#### Requirements
- Java 21+
- Maven 3.6+
- PostgreSQL 12+

#### Database Setup
```sql
CREATE DATABASE warehouse_db;
CREATE USER warehouse_user WITH PASSWORD 'warehouse_pass';
GRANT ALL PRIVILEGES ON DATABASE warehouse_db TO warehouse_user;
```

#### Running the Application
```bash
# Clone the repository
git clone https://github.com/steedware/WarehouseManager.git
cd WarehouseManager

# Install dependencies and run
mvn clean install
mvn spring-boot:run
```

## API Endpoints

### Products
- `GET /api/products` – List all products  
- `POST /api/products` – Add a new product  
- `GET /api/products/{id}` – Get product details  
- `PUT /api/products/{id}` – Update a product  
- `DELETE /api/products/{id}` – Delete a product  
- `GET /api/products/low-stock` – List low stock products  

### Locations
- `GET /api/locations` – List all locations  
- `POST /api/locations` – Add a new location  
- `GET /api/locations/{id}` – Get location details  
- `PUT /api/locations/{id}` – Update a location  
- `DELETE /api/locations/{id}` – Delete a location  

### Stock Movements
- `GET /api/stock-movements` – List all movements  
- `POST /api/stock-movements` – Add a new movement  
- `GET /api/stock-movements/product/{id}` – Movements by product  
- `GET /api/stock-movements/date-range` – Movements by date range  

### Reports
- `GET /api/reports/products` – Products report (CSV)  
- `GET /api/reports/low-stock` – Low stock report (CSV)  
- `GET /api/reports/stock-movements` – Stock movements report (CSV)  

## Database Schema

### Table: products
- `id` (PK) – Product ID  
- `name` – Product name  
- `description` – Product description  
- `sku` – SKU code (unique)  
- `price` – Product price  
- `stock` – Current stock  
- `min_stock` – Minimum stock level  
- `location_id` (FK) – Linked warehouse location  
- `created_at`, `updated_at` – Timestamps  

### Table: locations
- `id` (PK) – Location ID  
- `name` – Location name  
- `description` – Location description  
- `code` – Location code (unique)  
- `created_at` – Creation date  

### Table: stock_movements
- `id` (PK) – Movement ID  
- `product_id` (FK) – Linked product  
- `location_id` (FK) – Linked location  
- `movement_type` – Movement type (ENUM)  
- `quantity` – Quantity  
- `notes` – Notes  
- `reference` – Reference number  
- `created_at` – Creation date  

## Sample Requests

### Add a Product
```json
POST /api/products
{
    "name": "Laptop Dell XPS 13",
    "description": "Ultrabook for office work",
    "sku": "DELL-XPS13-001",
    "price": 4999.99,
    "minStock": 5,
    "locationId": 1
}
```

### Add a Stock Movement
```json
POST /api/stock-movements
{
    "productId": 1,
    "locationId": 1,
    "movementType": "IN",
    "quantity": 10,
    "notes": "Delivery from supplier",
    "reference": "DOC-2024-001"
}
```

## Technologies and Patterns

- **Layered architecture** (Controller → Service → Repository)  
- **DTO Pattern** for layer separation  
- **Repository Pattern** with Spring Data JPA  
- **Bean Validation** for data validation  
- **Transactions** for data consistency  
- **OpenAPI 3.0** for API documentation  

## Author

Created by [steedware](https://github.com/steedware)

## License

MIT License

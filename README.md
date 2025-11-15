# Car Workshop REST API - Assignment 2

## Technology Stack
- Spring Boot 3.5.7
- Spring Data JPA
- H2 Database (in-memory)
- Java 21
- Maven

## How to Run the Project

### Start the application

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

The application runs on: **http://localhost:8080**

### Access H2 Database Console
- URL: **http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:mem:workshopdb`
- Username: `sa`
- Password: (leave empty)

---

## API Endpoints

Base URL: `http://localhost:8080`

### 1. Create a customer

**POST** `/customers`

**Request Body:**
```json
{
  "name": "Anna Andersson",
  "phoneNumber": "070-1234567"
}
```

**Test:**
```bash
curl -X POST http://localhost:8080/customers \
  -H "Content-Type: application/json" \
  -d '{"name":"Anna Andersson","phoneNumber":"070-1234567"}'
```

---

### 2. Create a vehicle

**POST** `/vehicles`

**Request Body:**
```json
{
  "registrationNumber": "ABC123",
  "brand": "Volvo",
  "model": "V70",
  "productionYear": 2015
}
```

**Test:**
```bash
curl -X POST http://localhost:8080/vehicles \
  -H "Content-Type: application/json" \
  -d '{"registrationNumber":"ABC123","brand":"Volvo","model":"V70","productionYear":2015}'
```

---

### 3. Assign a vehicle to a customer

**POST** `/vehicles/{vehicleId}/customer/{customerId}`

**Example:** Assign vehicle 1 to customer 1

**Test:**
```bash
curl -X POST http://localhost:8080/vehicles/1/customer/1
```

---

### 4. Get all customers with their vehicles

**GET** `/customers`

**Test:**
```bash
curl http://localhost:8080/customers
```

**Browser:**
```
http://localhost:8080/customers
```

---

### 5. Get all vehicles

**GET** `/vehicles`

**Test:**
```bash
curl http://localhost:8080/vehicles
```

**Browser:**
```
http://localhost:8080/vehicles
```

---

### 6. Get all vehicles sorted by brand

**GET** `/vehicles/brands`

**Test:**
```bash
curl http://localhost:8080/vehicles/brands
```

**Browser:**
```
http://localhost:8080/vehicles/brands
```

---

### 7. Get customer ID by name

**GET** `/customers/id?name={customerName}`

**Example:** Get ID for "Anna Andersson"

**Test:**
```bash
curl "http://localhost:8080/customers/id?name=Anna%20Andersson"
```

**Browser:**
```
http://localhost:8080/customers/id?name=Anna%20Andersson
```

---

## Custom Queries Used

**CustomerRepository:**
- `findAllCustomersWithVehicles()` - Gets all customers with their vehicles using JOIN
- `findIdByName(String name)` - Returns customer ID based on name

**VehicleRepository:**
- `findVehiclesByCustomerId(Long customerId)` - Gets all vehicles for a specific customer
- `findAllOrderedByBrand()` - Gets all vehicles sorted by brand

---

## Project Structure

```
src/main/java/se/yrgo/cardemo/
├── entity/
│   ├── Customer.java
│   └── Vehicle.java
├── repository/
│   ├── CustomerRepository.java
│   └── VehicleRepository.java
├── dto/
│   ├── CustomerDTO.java
│   └── VehicleDTO.java
├── controller/
│   └── CarWorkshopController.java
└── CardemoApplication.java
```
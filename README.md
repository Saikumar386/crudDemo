# Spring Boot + MySQL CRUD Demo (Employee Management)

A small, ready-to-run Spring Boot REST API that performs CRUD (Create, Read, Update, Delete)
operations on an `Employee` resource, backed by MySQL.

## Tech Stack
- Java 17
- Spring Boot 3.2.5 (Spring Web, Spring Data JPA, Validation)
- MySQL 8
- Maven

## Project Structure
```
crud-demo/
├── pom.xml
└── src/main/
    ├── java/com/example/crud/
    │   ├── CrudDemoApplication.java       # main entry point
    │   ├── model/Employee.java            # JPA entity
    │   ├── repository/EmployeeRepository.java
    │   ├── service/EmployeeService.java
    │   ├── controller/EmployeeController.java
    │   └── exception/
    │       ├── ResourceNotFoundException.java
    │       └── GlobalExceptionHandler.java
    └── resources/application.properties
```

## 1. Prerequisites
- JDK 17+ installed (`java -version`)
- Maven installed (`mvn -version`) — or just use your IDE's built-in Maven
- MySQL Server running locally (or remotely)

## 2. Configure the database connection
Open `src/main/resources/application.properties` and update the username/password
to match your MySQL setup:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/crud_demo_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```

You do **not** need to manually create the database — `createDatabaseIfNotExist=true`
creates `crud_demo_db` automatically, and `spring.jpa.hibernate.ddl-auto=update` creates
the `employees` table automatically on startup.

## 3. Run the project

**Using Maven CLI:**
```bash
cd crud-demo
mvn spring-boot:run
```

**Using an IDE (IntelliJ / Eclipse / VS Code):**
- Open the folder as a Maven project
- Run `CrudDemoApplication.java`

The app starts on **http://localhost:8080**

## 4. API Endpoints

| Method | URL                       | Description           | Body required |
|--------|---------------------------|------------------------|----------------|
| POST   | `/api/employees`          | Create an employee     | Yes |
| GET    | `/api/employees`          | Get all employees      | No  |
| GET    | `/api/employees/{id}`     | Get one employee by id | No  |
| PUT    | `/api/employees/{id}`     | Update an employee     | Yes |
| DELETE | `/api/employees/{id}`     | Delete an employee     | No  |

### Sample request body (POST / PUT)
```json
{
  "name": "Ravi Kumar",
  "email": "ravi.kumar@example.com",
  "department": "Engineering",
  "salary": 55000
}
```

### Test with curl

**Create:**
```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"name":"Ravi Kumar","email":"ravi.kumar@example.com","department":"Engineering","salary":55000}'
```

**Get all:**
```bash
curl http://localhost:8080/api/employees
```

**Get one:**
```bash
curl http://localhost:8080/api/employees/1
```

**Update:**
```bash
curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Ravi Kumar","email":"ravi.kumar@example.com","department":"Sales","salary":60000}'
```

**Delete:**
```bash
curl -X DELETE http://localhost:8080/api/employees/1
```

You can also import these into **Postman** by creating requests with the same method/URL/body.

## 5. Validation & Error Handling
- `name` and `email` are required; `email` must be a valid format; `salary` must be ≥ 0.
- Invalid input → `400 Bad Request` with a field-level error map.
- Requesting a non-existent id → `404 Not Found` with a message.

## Notes / Next Steps
- This uses `ddl-auto=update`, which is convenient for development but not recommended
  for production (consider Flyway/Liquibase migrations instead).
- To add authentication, search-by-field endpoints, or pagination, the existing layered
  structure (Controller → Service → Repository) makes it easy to extend.

# Airline Reservation System – RESTful Passenger API

**Subject:** Object-Oriented Programming  
**Assignment 4:** Project Milestone 2  
**Student:** Ayazbek Abdanur

## 📌 Project Description

This milestone of the Airline Reservation System demonstrates how to build a **Java-based RESTful web service** using a lightweight built-in HTTP server. The API handles JSON-formatted requests and responses for managing `Passenger` data stored in a **PostgreSQL** database.

## 💡 Features

- 🌐 RESTful HTTP API built on Java's `HttpServer` class  
- ✅ Supports CRUD operations:
  - **GET**: Fetch all passengers  
  - **POST**: Add a new passenger  
  - **PUT**: Update passenger by ID  
  - **DELETE**: Remove passenger by ID  
- 📦 Request and response bodies are handled in **JSON format**  
- 🔌 Connects to a PostgreSQL database using **JDBC**  
- 🛡️ Uses `PreparedStatement` to prevent SQL injection

## 🧰 Technologies Used

- **Language**: Java  
- **Database**: PostgreSQL  
- **Web Server**: Java built-in HTTP server (`com.sun.net.httpserver.HttpServer`)  
- **Libraries**:
  - `json-20231013.jar` for JSON parsing  
  - `postgresql-42.7.5.jar` for PostgreSQL JDBC driver  
- **IDE**: IntelliJ IDEA  
- **Version Control**: Git + GitHub

## 🚀 How to Run

> ⚠️ Prerequisites:  
> - PostgreSQL installed and running  
> - Java 17+ installed  
> - Required JAR files in `lib/`:
>   - `json-20231013.jar`  
>   - `postgresql-42.7.5.jar`

1. Clone the repository:
   ```bash
   git clone https://github.com/MnstrsParago/OOP.ASG4.Milestone2.git
   cd OOP.ASG4.Milestone2
``

2. Create the `Passenger` table in your PostgreSQL database:

   ```sql
   CREATE TABLE Passenger (
       passenger_id SERIAL PRIMARY KEY,
       name VARCHAR(100),
       passport_number VARCHAR(50),
       nationality VARCHAR(50),
       date_of_birth DATE
   );
   ```

3. Compile and run the project in your IDE (ensure `lib/*.jar` files are included in the classpath).

4. Start the server by running `PassengerAPI.java`. The server will be accessible at:

   ```
   http://localhost:8080/passenger
   ```

## 📮 API Endpoints

| Method | Endpoint         | Description            | Body (JSON) or Query                                                                               |
| ------ | ---------------- | ---------------------- | -------------------------------------------------------------------------------------------------- |
| GET    | /passenger       | Get all passengers     | None                                                                                               |
| POST   | /passenger       | Add a new passenger    | `{ "name": "...", "passport_number": "...", "nationality": "...", "date_of_birth": "YYYY-MM-DD" }` |
| PUT    | /passenger?id=ID | Update passenger by ID | Same as POST body                                                                                  |
| DELETE | /passenger?id=ID | Delete passenger by ID | None                                                                                               |

## 🧪 Sample POST Request (using `curl`)

```bash
curl -X POST http://localhost:8080/passenger \
-H "Content-Type: application/json" \
-d '{
  "name": "Alice Smith",
  "passport_number": "XZ987654",
  "nationality": "UK",
  "date_of_birth": "1988-05-14"
}'
```

## 📘 Reflection

This project marks a major step in back-end development, combining web programming with database operations. Implementing a RESTful API from scratch using only Java’s standard libraries deepened my understanding of how low-level servers work and how to safely process HTTP requests and data in a real-world format like JSON.

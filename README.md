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

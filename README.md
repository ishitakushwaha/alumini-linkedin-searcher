# 🎓 Alumni LinkedIn Profile Searcher

A Spring Boot RESTful backend application that allows searching and saving alumni profiles (mocking LinkedIn data) using a free public API (DummyJSON Users API).


## 🚀 Features

* RESTful API built with Spring Boot 3 

* PostgreSQL database integration via Spring Data JPA 

* Uses DummyJSON API instead of PhantomBuster (free, no API key required)

* Alumni search results are saved in PostgreSQL 

* Retrieve alumni by:

  * Bulk search 
  * All records 
  * Individual ID

* Robust error handling with Global Exception Handler 

* Custom exception: AlumniNotFoundException → clean 404 Not Found 

* TDD support with JUnit + Mockito 

* Testable with Postman Collection (included)

## 🛠️ Tech Stack

* Java 17

* Spring Boot 3.x

* Spring Data JPA (Hibernate)

* PostgreSQL

* Lombok

* JUnit 5 + Mockito

* Postman (for API testing)

📂 Project Structure
src/main/java/com/alumni/searcher

`├── AlumniLinkedInSearcherApplication.java   # Main class`

`├── controller      # REST Controllers`

`├── service         # Business logic`

`├── repository      # JPA Repositories`

`├── model           # Entity classes`

`├── dto             # Request/Response DTOs + DummyJSON mappings`

`├── exception       # GlobalExceptionHandler + custom exceptions`

## ⚙️ Setup Instructions

### 1️⃣ Clone Repository

`git clone https://github.com/your-username/alumni-linkedin-searcher.git
cd alumni-linkedin-searcher`

### 2️⃣ Configure PostgreSQL

#### Create a database:

`CREATE DATABASE alumni_db;`


#### (Optional) Create a user:

`CREATE USER alumni_user WITH PASSWORD 'alumni_pass';
GRANT ALL PRIVILEGES ON DATABASE alumni_db TO alumni_user;
`

### 3️⃣ Update application.properties

`spring.datasource.url=jdbc:postgresql://localhost:5432/alumni_db
spring.datasource.username=alumni_user
spring.datasource.password=alumni_pass
spring.jpa.hibernate.ddl-auto=update`

### 4️⃣ Run the App

`./mvnw spring-boot:run`


The app will be available at:

👉 `http://localhost:8080`

## 🗄️ Database Schema

### SQL Schema (`alumni.sql`)

`CREATE TABLE alumni ( 
id SERIAL PRIMARY KEY,
name VARCHAR(255) NOT NULL,
current_role VARCHAR(255) NOT NULL,
university VARCHAR(255) NOT NULL,
location VARCHAR(255),
linkedin_headline VARCHAR(500),
passout_year INT
);`

### Sample Data (alumni_sample_data.sql)

`INSERT INTO alumni (name, current_role, university, location, linkedin_headline, passout_year)
VALUES
('John Doe', 'Software Engineer', 'University of XYZ', 'New York, NY', 'Passionate Software Engineer at XYZ Corp', 2020),
('Jane Smith', 'Data Scientist', 'University of XYZ', 'San Francisco, CA', 'Data Scientist | AI Enthusiast', 2019);
`

## Auto-load on App Start

Place a `data.sql` in `src/main/resources` and Spring Boot will auto-insert sample rows.

## 🔗 API Endpoints

### 1️⃣ Search Alumni Profiles

### POST `/api/alumni/search`

### Request Body:

`{
"university": "University of XYZ", 
"designation": "Software Engineer",
"passoutYear": 2020
}`


### Response:

`{
"status": "success",
"data": [
{
"name": "John Doe",
"currentRole": "Software Engineer",
"university": "University of XYZ",
"location": "New York, NY",
"linkedinHeadline": "Passionate Software Engineer at XYZ Corp",
"passoutYear": 2020
}
]
}`

### 2️⃣ Get All Alumni

### GET /api/alumni/all

### Response:

`{
"status": "success",
"data": [
{
"name": "Jane Smith",
"currentRole": "Data Scientist",
"university": "University of XYZ",
"location": "San Francisco, CA",
"linkedinHeadline": "Data Scientist | AI Enthusiast",
"passoutYear": 2019
}
]
}`

### 3️⃣ Get Alumni by ID

### GET /api/alumni/{id}

### ✅ Success
`{
"status": "success",
"data": {
"name": "John Doe",
"currentRole": "Software Engineer",
"university": "University of XYZ",
"location": "New York, NY",
"linkedinHeadline": "Passionate Software Engineer at XYZ Corp",
"passoutYear": 2020
}
}`

### ❌ Error (Not Found)
`{
"status": "NOT_FOUND",
"message": "Alumni with id 999 not found",
"timestamp": "2025-08-22T19:30:44.231",
"path": "/api/alumni/999"
}`

## 🧪 Testing

### Run Unit Tests

`./mvnw test`


### Unit test class:
`src/test/java/com/alumni/searcher/controller/AlumniControllerTest.java
`
## Postman Collection

📁 `AlumniLinkedInSearcher.postman_collection.json` includes:

* `POST /api/alumni/search`

* `GET /api/alumni/all`

* `GET /api/alumni/{id} (success + error demo)`
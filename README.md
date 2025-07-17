

# Bitespeed Identity Reconciliation – Java Spring Boot Backend

This is a backend implementation of the [Bitespeed Identity Reconciliation](https://bitespeed.notion.site/Bitespeed-Backend-Task-Identity-Reconciliation-1fb21bb2a930802eb896d4409460375c) task using Java, Spring Boot, MySQL, and Maven.

# Problem Statement

When a user logs in using different identifiers (email or phone), the backend must determine whether this is a new contact or one that already exists and reconcile identities accordingly.

**********

# Tech Stack

- Java 17
- Spring Boot 3.x
- MySQL (local or remote)
- Maven
- Spring Data JPA
- Thymeleaf (optional for web form testing)

---

## 🗂 Project Structure
src/
├── main/
│ ├── java/
│ │ └── com.example.bitespeed/
│ │ ├── controller/
│ │ ├── dto/
│ │ ├── model/
│ │ ├── repository/
│ │ └── service/
│ └── resources/
│ ├── templates/
│ │ └── application.properties
└── pom.xml


---

# 1. Clone the Repository with github

```gitbash
git clone https://github.com/<your-username>/bitespeed.git
cd bitespeed

# Mysql Database Application.properties Connectiong to maven to MySqldatabase

spring.datasource.url=jdbc:mysql://localhost:3306/bitespeed_db?useSSL=false
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

server.port=8080


Postman Apis Request;
API Endpoints
POST /identify — Reconcile identity

GET /contacts — Get all contacts

PUT /contacts/{id} — Update a contact

DELETE /contacts/{id} — Delete a contact

--> API: /identify (POST)
POST /identify
Content-Type: application/json

--> Request Body
json

{
  "email": "kanakalaprasannakumar@gmail.com",
  "phoneNumber": "7416115591"
}

 Response
json

{
  "contact": {
    "primaryContactId": 1,
    "emails": ["kanakalaprasannakumar.com"],
    "phoneNumbers": ["7416115591"],
    "secondaryContactIds": []
  }
}

**it show the sql database  example** -->

Example Contact Table After Above 3 Requests
| id | email                                           | phoneNumber | linkedId | linkPrecedence | createdAt           |
| -- | ----------------------------------------------- | ----------- | -------- | -------------- | ------------------- |
| 1  | [john@example.com](mailto:john@example.com)     | 9999999999  | null     | primary        | 2025-07-16 10:00:00 |
| 2  | [johnny@example.com](mailto:johnny@example.com) | 9999999999  | 1        | secondary      | 2025-07-16 10:05:00 |
| 3  | [john@example.com](mailto:john@example.com)     | 8888888888  | 1        | secondary      | 2025-07-16 10:10:00 |

 Author
Prasanna Kumar Kanakala
Backend Developer
🔗 LinkedIn


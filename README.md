# 🚀 Spring Boot Demo Project

A full-featured **Spring Boot** application that demonstrates:

- ✅ JWT Authentication (Login & Signup)
- ✅ User CRUD Management with Pagination
- ✅ Firebase Push Notifications Integration
- ✅ Scheduled Cron Jobs
- ✅ Global Exception Handling & Validation
- ✅ REST API with JSON responses
- ✅ MySQL Database Integration
- ✅ Docker support for local testing

---

## 🏗️ Tech Stack

| Layer              | Technology                     |
| ------------------ | ------------------------------ |
| Backend Framework  | Spring Boot 3.x                |
| Security           | Spring Security + JWT          |
| Database           | MySQL                          |
| Push Notifications | Firebase Cloud Messaging (FCM) |
| Scheduler          | Spring Boot `@Scheduled`       |
| Build Tool         | Maven                          |
| Java Version       | 17+                            |

---

## ⚙️ Features Overview

### 🔐 Authentication (JWT)

- Register new users (`/api/auth/signup`)
- Login with credentials (`/api/auth/login`)
- JWT token-based authentication and authorization

### 👥 User Management

- Get all users with pagination  
  `GET /api/users?page=0&size=10`
- Edit, Delete, and View users
- Data validation using `@Valid`

### 🔔 Firebase Push Notifications

- Integrated with **Firebase Cloud Messaging (FCM)**
- Send notifications from backend to device tokens
- Token retrieval via static HTML (FCM Token Generator)

### ⏰ Cron Jobs

- Automated scheduled tasks using Spring’s `@Scheduled`
- Example: Send daily summary email, clean temp data, etc.

### ⚡ Exception Handling

- Centralized `@ControllerAdvice` for error responses
- Returns clean, consistent JSON error structures

---

## 🗄️ API Endpoints

### Auth APIs

| Method | Endpoint           | Description             |
| ------ | ------------------ | ----------------------- |
| POST   | `/api/auth/signup` | Register new user       |
| POST   | `/api/auth/login`  | Login and get JWT token |

### User APIs

| Method | Endpoint          | Description               |
| ------ | ----------------- | ------------------------- |
| GET    | `/api/users`      | Get all users (paginated) |
| GET    | `/api/users/{id}` | Get user by ID            |
| PUT    | `/api/users/{id}` | Update user               |
| DELETE | `/api/users/{id}` | Delete user               |

---

## 🧩 Project Structure

```

src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── controller/        # REST Controllers
│   │   ├── model/             # Entities
│   │   ├── repository/        # JPA Repositories
│   │   ├── service/           # Business Logic
│   │   ├── config/            # Security, JWT, Firebase, etc.
│   │   └── scheduler/         # Cron Job tasks
│   └── resources/
│       ├── application.yml    # App Config
│       └── static/            # Static HTML (FCM Token Page)
└── test/

```

---

## 🧠 How to Run Locally

### 1️⃣ Clone the Repo

```bash
git clone https://github.com/<your-username>/spring-boot-demo.git
cd spring-boot-demo
```

### 2️⃣ Setup Database

Create a MySQL database:

```sql
CREATE DATABASE spring_demo;
```

Update your `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/spring_demo
    username: root
    password: your_password
```

### 3️⃣ Add Firebase Config

- Download your Firebase service account JSON from Firebase Console
- Save it locally (not in repo)
- Set environment variable:

  ```bash
  export FIREBASE_CREDENTIALS_PATH=/path/to/firebase.json
  ```

### 4️⃣ Run the Project

```bash
mvn spring-boot:run
```

Server will start at:
👉 **[http://localhost:8080](http://localhost:8080)**

---

## 🧪 Test with Curl

### Register User

```bash
curl -X POST http://localhost:8080/api/auth/signup \
-H "Content-Type: application/json" \
-d '{"name":"John Doe", "email":"john@example.com", "password":"123456"}'
```

### Login User

```bash
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{"email":"john@example.com", "password":"123456"}'
```

### Get Users

```bash
curl -X GET http://localhost:8080/api/users \
-H "Authorization: Bearer <JWT_TOKEN>"
```

---

## 🔔 Testing Firebase Push Notification

You can use the included HTML page to get an FCM token:

```
src/main/resources/static/fcm-token.html
```

Open in browser → allow notifications → copy the token printed in console.

Then send a notification using backend or directly via Postman:

```bash
curl -X POST http://localhost:8080/api/notifications/send \
-H "Content-Type: application/json" \
-d '{"token": "<device_token>", "title": "Hello", "body": "Test message"}'
```

---

## 🕒 Example Cron Job

```java
@Scheduled(cron = "0 0 * * * ?") // runs every hour
public void cleanupOldRecords() {
    log.info("Running scheduled cleanup task...");
}
```

---

## 🧰 Build Docker Image

```bash
docker build -t spring-boot-demo .
docker run -p 8080:8080 spring-boot-demo
```

---

## 🧤 Security

- Never commit your Firebase JSON or JWT secret keys.
- Store all credentials in environment variables.

## 🟦 Enter Kafka Container

```bash
docker exec -it kafka bash
```

---

## 🟩 Run Kafka Consumer

```bash
kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic test-topic \
  --from-beginning
```

---

## 🟨 Run Kafka Producer

```bash
kafka-console-producer \
  --bootstrap-server localhost:9092 \
  --topic test-topic
```

---

## 👨‍💻 Author

**Bhola Kumar**
Spring Boot Developer
📧 Contact: bholakumar2298@gmai.com
🔗 GitHub: [https://github.com/Bhola-Kr](https://github.com/Bhola-Kr)

---

## 🪪 License

This project is licensed under the [MIT License](LICENSE).

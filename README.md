# 🚴 Athvora - Smart Fitness Tracker with AI Recommendations

Athvora is a modern fitness tracking application built with Spring Boot that allows users to track fitness activities, monitor performance metrics, and receive AI-powered fitness recommendations generated using Google's Gemini AI.

---

## ✨ Features

### 🔐 Authentication & Authorization

* JWT-based authentication
* Secure login and registration
* Role-based access control
* Spring Security integration

### 🏃 Activity Tracking

* Create and manage fitness activities
* Track workout duration
* Track calories burned
* Store activity-specific metrics
* Support for multiple activity types:

  * Running
  * Cycling
  * Walking
  * Gym Workouts
  * Other custom activities

### 🤖 AI-Powered Recommendations

* Integration with Google Gemini AI
* Personalized fitness recommendations
* Improvement suggestions
* Safety guidelines
* Activity-specific coaching insights

### ⚡ Performance Optimization

* Redis caching integration
* Cached recommendation retrieval
* Reduced database load
* Faster API responses

### 🗄 Database Management

* MySQL database
* Spring Data JPA
* Hibernate ORM
* Entity relationships and mappings

---

## 🏗 System Architecture

```text
Client
   │
   ▼
Spring Boot REST API
   │
   ├── JWT Authentication
   │
   ├── MySQL Database
   │
   ├── Redis Cache
   │
   └── Gemini AI
           │
           ▼
   Personalized Recommendations
```

---

## 🛠 Tech Stack

### Backend

* Java 17
* Spring Boot 4
* Spring Security
* Spring Data JPA
* Hibernate

### Database

* MySQL

### Caching

* Redis

### AI Integration

* Google Gemini API

### Build Tool

* Maven

---

## 📁 Project Structure

```text
src/main/java
│
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
├── security
├── config
├── cache
└── exception
```

---

## 🚀 Getting Started

### Prerequisites

* Java 17+
* Maven
* MySQL
* Redis Server
* Gemini API Key

---

### Clone Repository

```bash
git clone https://github.com/your-username/Athvora-SmartActivityTrackerWithAIRecommendations.git

cd Athvora-SmartActivityTrackerWithAIRecommendations
```

---

### Configure Database

Update your `application-dev.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fitnessTracker
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

---

### Configure Redis

```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

---

### Configure Gemini API

```properties
gemini.api.key=YOUR_GEMINI_API_KEY
```

---

### Run Application

```bash
mvn spring-boot:run
```

Application will start at:

```text
http://localhost:8080
```

---

## 📌 Core APIs

### Authentication

| Method | Endpoint       |
| ------ | -------------- |
| POST   | /auth/register |
| POST   | /auth/login    |

---

### Activities

| Method | Endpoint         |
| ------ | ---------------- |
| POST   | /activities      |
| GET    | /activities      |
| GET    | /activities/{id} |
| PUT    | /activities/{id} |
| DELETE | /activities/{id} |

---

### Recommendations

| Method | Endpoint                               |
| ------ | -------------------------------------- |
| POST   | /recommendations/generate/{activityId} |
| GET    | /recommendations/user/{userId}         |
| GET    | /recommendations/activity/{activityId} |

---

## 🤖 Sample AI Recommendation

```json
{
  "type": "CYCLING",
  "recommendation": "Strong endurance-focused workout with good calorie expenditure.",
  "improvements": [
    "Increase interval training",
    "Focus on climbing workouts"
  ],
  "suggestions": [
    "Maintain hydration",
    "Add strength training"
  ],
  "safety": [
    "Wear a helmet",
    "Check bike condition before riding"
  ]
}
```

---

## ⚡ Redis Caching Strategy

### Cached Data

* User Recommendations
* Activity Recommendations

### Cache Invalidation

Whenever a new recommendation is generated:

```text
Generate Recommendation
        ↓
Save to Database
        ↓
Invalidate Redis Cache
        ↓
Fresh Data Loaded Next Request
```

---

## 🔮 Future Enhancements

* Fitness score generation
* Workout analytics dashboard
* Activity trends and charts
* Goal tracking system
* Email notifications
* AI-generated workout plans
* Mobile application support
* Docker deployment
* CI/CD pipeline

---

## 👨‍💻 Author

**Shiwank Rajput**

Java Backend Developer | Spring Boot Developer

GitHub: https://github.com/ShiwankRajput

---

## ⭐ Support

If you found this project useful, consider giving it a star on GitHub.

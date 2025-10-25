# TinyTasks — Full Stack Spring Boot + Tailwind App

A minimal full-stack application to practice a clean layered architecture with Spring Boot and a native frontend (HTML + JS + Tailwind).  
The project demonstrates the complete flow **Frontend → REST API → Logic → Data**, all running in-memory without any database.

---

## 🧩 Project Structure

```
tinytask/
 ├─ src/
 │   ├─ main/
 │   │   ├─ java/
 │   │   │   └─ com/project/tinytask/
 │   │   │        ├─ controller/
 │   │   │        ├─ entity/
 │   │   │        ├─ repository/
 │   │   │        ├─ service/
 │   │   │        └─ TinytaskApplication.java
 │   │   └─ resources/
 │   │        ├─ static/
 │   │        │    ├─ index.html
 │   │        │    └─ app.js
 │   │        ├─ templates/
 │   │        └─ application.properties
 └─ pom.xml
```

---

## ⚙️ Backend (Spring Boot)

### Base URL
```
http://localhost:8080/api/tasks
```

### Endpoints

| Method | Path | Description |
|--------|------|--------------|
| **GET** | `/api/tasks` | Returns all tasks |
| **POST** | `/api/tasks` | Creates a new task |
| **PUT** | `/api/tasks/{id}/toggle` | Toggles the `done` state |
| **DELETE** | `/api/tasks/{id}` | Deletes a task |

### Example JSON Response

```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "done": false
}
```

### Error Responses

| Code | Body |
|------|------|
| **400** | `{ "error": "Title is required" }` |
| **404** | `{ "error": "Not found" }` |

---

### 🧠 Business Rules

- Task titles must be **at least 3 characters long**.
- All data lives **in memory** (no database).
- Each task contains:  
  - `id: int`  
  - `title: string`  
  - `done: boolean`

---

### 🧪 Unit Tests (JUnit 5)

Minimum required test cases:

| Module | Test Case | Positive | Negative |
|---------|------------|-----------|-----------|
| **Service** | Create task | Valid title creates task with `done=false` | Invalid title throws exception |
| **Service** | Toggle task | Flips `done` true/false | Nonexistent ID throws error |
| **Service** | Delete task | Deletes successfully | Nonexistent ID returns false |

#### Run all tests
```bash
./mvnw test
```

#### Run a single test class
```bash
./mvnw -Dtest=TaskServiceTest test
```

---

## 💻 Frontend (Tailwind)

The frontend is served directly by Spring Boot from `/src/main/resources/static`.

### Files

- `index.html` — Main UI (uses Tailwind via CDN)
- `app.js` — Fetches data and interacts with the REST API

### Access

Once the backend is running, open:

👉 **http://localhost:8080**

The UI allows:
- Listing all tasks  
- Adding a new task  
- Toggling completed/pending  
- Deleting a task  

---

### Example API Calls (cURL)

```bash
# List all tasks
curl http://localhost:8080/api/tasks

# Create a task
curl -X POST -H "Content-Type: application/json" -d '{"title":"Buy milk"}' http://localhost:8080/api/tasks

# Toggle a task
curl -X PUT http://localhost:8080/api/tasks/1/toggle -i

# Delete a task
curl -X DELETE http://localhost:8080/api/tasks/1 -i
```

---

## 🚀 Run the Application

From the project root:

```bash
./mvnw spring-boot:run
```

Then open your browser at:

👉 `http://localhost:8080`

---

## 🔓 CORS

CORS is configured with a wildcard (`*`), allowing any origin.  
This setup is suitable for local development but **not recommended for production**.

---


## ✅ Summary

TinyTasks demonstrates how to build a clean and functional full-stack app:
**Frontend → REST API → Business Logic → Data (in memory)**,  
with clear separation of layers and testable logic.

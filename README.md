# TinyTasks — Full Project (Backend + Frontend)

> **Autor:** Crudzaso  
> **Frameworks:** Spring Boot (backend) + Tailwind (frontend)  
> **Lenguaje:** Java 21  
> **Testing:** JUnit 5  
> **Persistencia:** En memoria  

TinyTasks es una microaplicación diseñada para practicar un flujo completo **Front → API → Lógica → Datos** con la mínima estructura posible, pero respetando buenas prácticas de separación por capas y testabilidad.

---

## 🧩 Estructura del proyecto

crudzaso-tinytasks/
├─ backend/
│ └─ src/main/java/com/crudzaso/tinytasks/
│ ├─ controller/
│ ├─ service/
│ ├─ repository/
│ ├─ model/
│ └─ config/
├─ frontend/
│ ├─ index.html
│ ├─ app.js
│ └─ style.css (opcional)
└─ README.md

yaml
Copiar código

---

## ⚙️ Backend (Spring Boot)

### 📍 Endpoints

| Método | URL | Descripción |
|--------|-----|--------------|
| **GET** | `/api/tasks` | Lista todas las tareas |
| **POST** | `/api/tasks` | Crea una nueva tarea |
| **PUT** | `/api/tasks/{id}/toggle` | Alterna el estado `done` |
| **DELETE** | `/api/tasks/{id}` | Elimina una tarea |

### 💡 Formato de respuesta

```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "done": false
}
⚠️ Errores
Código	Respuesta
400	{ "error": "Title is required" }
404	{ "error": "Not found" }

🚀 Ejecución del backend
Desde la carpeta backend/, ejecuta:

Usando Maven Wrapper (recomendado)
bash
Copiar código
./mvnw spring-boot:run
Usando Maven instalado globalmente
bash
Copiar código
mvn spring-boot:run
O construyendo y ejecutando el .jar
bash
Copiar código
mvn package
java -jar target/tinytasks-0.0.1-SNAPSHOT.jar
La aplicación quedará disponible en:
👉 http://localhost:8080

🔓 CORS
Asegúrate de habilitar CORS para el origen del frontend, por ejemplo:

java
Copiar código
@CrossOrigin(origins = "http://localhost:5500")
Esto permite que el navegador haga peticiones desde la app web servida localmente.

🧪 Pruebas unitarias (JUnit 5)
Ejecutar todos los tests
bash
Copiar código
./mvnw test
Ejecutar una clase específica
bash
Copiar código
./mvnw -Dtest=TaskServiceTest test
Casos mínimos exigidos
Módulo	Caso	Positivo	Negativo
Service	Crear tarea	Título válido crea tarea con done=false	Título vacío o corto lanza excepción
Service	Alternar estado	Cambia done de false → true o viceversa	ID inexistente lanza error o devuelve vacío
Service	Eliminar tarea	Elimina correctamente	ID inexistente devuelve false

💻 Frontend (Tailwind)
🌐 Requisitos previos
Navegador moderno (Chrome, Firefox, Edge)

Backend corriendo en http://localhost:8080

Servidor local para servir archivos estáticos (Live Server, npx http-server, o python -m http.server)

📁 Estructura del frontend
pgsql
Copiar código
frontend/
 ├─ index.html
 └─ app.js
🏃 Ejecutar el frontend
Opción 1 — VS Code Live Server (recomendado)
Abre la carpeta frontend/ en VS Code.

Instala la extensión Live Server.

Haz clic derecho en index.html → Open with Live Server.

Se abrirá una URL como http://127.0.0.1:5500 (usa esa en @CrossOrigin).

Opción 2 — Node.js (sin instalación global)
bash
Copiar código
npx http-server -p 5500
Opción 3 — Python 3
bash
Copiar código
python -m http.server 5500
Ahora abre:
👉 http://localhost:5500
(o la dirección que indique tu servidor local)

🧠 Flujo de interacción
Listar tareas:
GET http://localhost:8080/api/tasks

Crear tarea:
POST con body JSON:

json
Copiar código
{ "title": "Learn Tailwind" }
Alternar estado:
PUT http://localhost:8080/api/tasks/{id}/toggle

Eliminar tarea:
DELETE http://localhost:8080/api/tasks/{id}

🧰 Ejemplos con cURL
bash
Copiar código
# Listar todas las tareas
curl http://localhost:8080/api/tasks

# Crear una nueva tarea
curl -X POST -H "Content-Type: application/json" -d '{"title":"Buy milk"}' http://localhost:8080/api/tasks

# Alternar estado
curl -X PUT http://localhost:8080/api/tasks/1/toggle -i

# Eliminar tarea
curl -X DELETE http://localhost:8080/api/tasks/1 -i
🧯 Solución de problemas
Problema	Solución
CORS error	Agregar @CrossOrigin(origins = "http://localhost:5500") en el controlador
El backend no responde	Verifica que Spring Boot esté corriendo en el puerto 8080
JS no carga	Asegúrate de que index.html y app.js estén en la misma carpeta
Frontend no se actualiza	Limpia la caché del navegador o reinicia Live Server

🧱 Mejoras opcionales
Configurar Tailwind con npm y postcss para compilación optimizada.

Añadir animaciones (p. ej. transición al crear o eliminar tareas).

Implementar edición de tareas en línea.

Agregar pruebas de interfaz con Playwright o Cypress.

✅ Conclusión
TinyTasks es un ejercicio completo pero minimalista para dominar el ciclo:
Frontend → API → Lógica → Datos,
siguiendo buenas prácticas de capas, manejo de errores y pruebas unitarias.

yaml
Copiar código

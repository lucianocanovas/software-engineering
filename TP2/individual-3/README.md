# Ejercicio Individual 3 - Conexión API REST CRUD React + Spring Boot (Gestión de Futbolistas)

Este proyecto corresponde a la resolución del **Ejercicio Individual N° 3** de la Guía Práctica TP2 de Ingeniería del Software II:
> **REACT: Conexión por API Frontend con Backend de distintas tecnologías: Realizar el ejercicio planteado en la Guía Audiovisual donde se utiliza React para el Frontend y Java para el Backend conectándose ambos por API. Visualizar los primeros videos de la guía "3) React con Java (PARTE B)"**  
> *Video de referencia:* https://www.youtube.com/watch?v=LuM5c_f6nUE

---

## 📁 Estructura del Proyecto

```text
individual-3/
├── backend/                  # API REST en Spring Boot con JPA e Hibernate
│   ├── src/main/java/        # Código fuente Java (Controller, Entity, Repository, Service)
│   ├── src/main/resources/   # Configuración de base de datos (MySQL y perfil H2)
│   ├── SQL/                  # Script SQL de creación de tablas y datos iniciales
│   ├── pom.xml               # Dependencias de Maven
│   └── mvnw / mvnw.cmd       # Maven Wrapper
├── frontend/                 # Aplicación SPA en React con Bootstrap
│   ├── public/               # index.html, favicon y assets
│   ├── src/                  # Componentes (ListFutbolistas, AddFutbolista, Header, Footer)
│   ├── src/services/         # Servicio Axios para consumo de la API REST
│   └── package.json          # Dependencias y scripts de npm
└── README.md                 # Documentación del ejercicio
```

---

## ⚙️ Tecnologías Utilizadas

- **Backend:**
  - Java 17+ (compatible con Java 21, 25 y 26)
  - Spring Boot 3.3.x
  - Spring Data JPA
  - MySQL Connector / H2 Database
  - Maven Wrapper

- **Frontend:**
  - React 18
  - React Router DOM 6
  - Axios
  - Bootstrap 4.6 (vía CDN)

---

## 🚀 Instrucciones de Ejecución

### 1. Iniciar el Backend (Spring Boot)

1. Abrir una terminal en `backend`:
   ```bash
   cd backend
   ```
2. Ejecutar la aplicación:
   - Modo normal con MySQL (base de datos `examen_civa` en `localhost:3306`):
     ```bash
     ./mvnw spring-boot:run
     ```
   - O en modo in-memory con **H2** (crea esquema y datos automáticamente):
     ```bash
     ./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
     ```
3. El backend quedará escuchando en `http://localhost:8090`.

#### Endpoints de la API:
- `GET /url/futbolista` - Lista todos los futbolistas.
- `GET /url/futbolista/posiciones` - Lista las posiciones (Arquero, Defensa, Mediocampista, Delantero).
- `GET /url/futbolista/buscarPorId/{id}` - Busca un futbolista por su ID.
- `POST /url/futbolista/registrarFutbolista` - Registra un nuevo futbolista.
- `PUT /url/futbolista/actualizaFutbolista` - Actualiza los datos de un futbolista existente.
- `DELETE /url/futbolista/eliminaFutbolista/{id}` - Elimina un futbolista por ID.

---

### 2. Iniciar el Frontend (React)

1. Abrir otra terminal en `frontend`:
   ```bash
   cd frontend
   ```
2. Instalar dependencias:
   ```bash
   npm install
   ```
3. Iniciar la aplicación:
   ```bash
   npm start
   ```
4. Se abrirá en el navegador en `http://localhost:3000` con el listado de futbolistas y formularios de alta/edición/baja.

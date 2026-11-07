# Ejercicio Individual 1 - Conexión API REST React + Spring Boot (Catálogo de Productos)

Este proyecto corresponde a la resolución del **Ejercicio Individual N° 1** de la Guía Práctica TP2 de Ingeniería del Software II:
> **REACT: Conexión por API Frontend con Backend de distintas tecnologías: Realizar el ejercicio planteado en la Guía Audiovisual donde se utiliza React para el Frontend y Java para el Backend conectándose ambos por API. "1) API REST PARTE 1: React con Java (PARTE B)"**  
> *Video de referencia:* https://www.youtube.com/watch?v=42XW4hwhjHA

---

## 📁 Estructura del Proyecto

```text
individual-1/
├── backend/                  # API REST desarrollada en Java con Spring Boot
│   ├── src/main/java/        # Código fuente Java (Controladores, Modelos, Repositorios, Servicios)
│   ├── src/main/resources/   # Configuración de base de datos (MySQL y perfil H2) y data.sql
│   ├── pom.xml               # Dependencias de Maven
│   └── mvnw / mvnw.cmd       # Maven Wrapper
├── frontend/                 # Aplicación de interfaz de usuario en React
│   ├── public/               # Recursos estáticos e index.html
│   ├── src/                  # Componentes de React (Appbar, Products, Product, axiosConfig)
│   ├── package.json          # Dependencias y scripts de npm
│   └── .env                  # Variables de entorno (puerto 3001)
└── README.md                 # Documentación del ejercicio
```

---

## ⚙️ Tecnologías Utilizadas

- **Backend:**
  - Java 17+ (compatible con Java 21, 25 y 26)
  - Spring Boot 3.3.x
  - Spring Data JPA / Hibernate
  - MySQL Connector / H2 Database (modo in-memory para pruebas directas)
  - Lombok
  - Maven

- **Frontend:**
  - React 18
  - Material-UI (MUI) `@mui/material`, `@emotion/react`, `@emotion/styled`
  - Axios para consumo de API REST
  - React Router DOM

---

## 🚀 Instrucciones de Ejecución

### 1. Iniciar el Backend (Spring Boot)

1. Abrir una terminal en el directorio `backend`:
   ```bash
   cd backend
   ```
2. Ejecutar la aplicación:
   - Con Maven Wrapper (perfil por defecto MySQL en localhost:3306 con base `productdb`):
     ```bash
     ./mvnw spring-boot:run
     ```
   - O en modo memoria con **H2** (sin necesidad de tener MySQL instalado):
     ```bash
     ./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
     ```
3. El backend quedará escuchando en `http://localhost:8080`.

#### Endpoints principales:
- `GET /products` - Listado paginado de productos.
- `GET /products/{id}` - Detalle de un producto específico.
- `GET /categories` - Listado de categorías disponibles.
- `GET /subcategories` - Listado de subcategorías.

---

### 2. Iniciar el Frontend (React)

1. Abrir otra terminal en el directorio `frontend`:
   ```bash
   cd frontend
   ```
2. Instalar las dependencias de node:
   ```bash
   npm install
   ```
3. Iniciar el servidor de desarrollo:
   ```bash
   npm start
   ```
4. La aplicación abrirá automáticamente en `http://localhost:3001` y se comunicará con el backend en `http://localhost:8080`.

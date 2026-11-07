# Ejercicio Individual 5 - React con Java y Seguridad (Spring Security + Auth + Protected Routes)

Este proyecto corresponde a la resolución del **Ejercicio Individual N° 5** de la Guía Práctica TP2 de Ingeniería del Software II:
> **REACT: Conexión por API Frontend con Backend de distintas tecnologías: Realizar el ejercicio planteado en la Guía Audiovisual donde se utiliza React para el Frontend y Java para el Backend conectándose ambos por API utilizando Seguridad. Visualizar los primeros videos de la guía "5) React con Java y seguridad (PARTE2)"**  
> *Playlist de referencia:* https://www.youtube.com/playlist?list=PL9l1zUfnZkZnmca4gYPVQQ9msSR5tQTdk

---

## 📁 Estructura del Proyecto

```text
individual-5/
├── backend/                  # API REST en Spring Boot con Spring Security 6
│   ├── src/main/java/        # Código fuente Java
│   │   ├── controllers/      # CategoryController, ProductController, SubCategoryController, UserController
│   │   ├── model/            # Category, Product, SubCategory, User, LoginRequest
│   │   ├── repositories/     # Interfaces JPA Repository
│   │   ├── security/         # SecurityConfig, UserPrincipal, MyUserDetailsService
│   │   ├── services/         # Servicios de negocio (UserService, ProductService, etc.)
│   │   └── ProductAppApplication.java
│   ├── src/main/resources/   # application.properties (MySQL) y application-h2.properties (H2 in-memory)
│   ├── pom.xml               # Configuración Maven con Spring Security y JPA
│   └── mvnw / mvnw.cmd       # Maven Wrapper
├── frontend/                 # Aplicación React con flujo de Autenticación
│   ├── public/               # Plantilla HTML y recursos estáticos
│   ├── src/                  # Código fuente React
│   │   ├── components/       # Login, Registration, ProtectedRoute, AuthContext, Products, Product, Appbar
│   │   ├── axiosConfig.js    # Interceptores de autenticación para llamadas a la API
│   │   └── App.js            # Enrutamiento protegido y público con React Router DOM
│   ├── package.json          # Dependencias (React 18, MUI, Axios, React Router)
│   └── .env                  # Configuración de variables de entorno y puerto 3001
└── README.md                 # Documentación del ejercicio
```

---

## ⚙️ Tecnologías Utilizadas

- **Backend:**
  - Java 17+ (compatible con Java 21, 25 y 26)
  - Spring Boot 3.3.x
  - Spring Security 6 (Autenticación HTTP Basic / Form Login / BCryptPasswordEncoder)
  - Spring Data JPA / Hibernate
  - MySQL Connector / H2 Database (modo memoria para ejecución directa)
  - Maven Wrapper

- **Frontend:**
  - React 18
  - Material-UI (MUI) `@mui/material`, `@emotion/react`, `@emotion/styled`
  - React Router DOM 6 con Rutas Protegidas (`<ProtectedRoute>`)
  - Context API (`AuthContext`) para gestión del estado de autenticación (login, logout, usuario activo)
  - Axios con interceptores

---

## 🔐 Características de Seguridad

1. **Gestión de Usuarios:**
   - Registro de usuarios con contraseña hasheada mediante `BCryptPasswordEncoder`.
   - Endpoint `/users/register` para altas de nuevos usuarios.
   - Endpoint `/users/login` para autenticación con credenciales.

2. **Rutas Protegidas en Frontend:**
   - Componente `ProtectedRoute` que restringe el acceso al catálogo y detalle de productos a usuarios autenticados.
   - Redirección automática a la vista de `/login` si el usuario no tiene una sesión activa.

---

## 🚀 Instrucciones de Ejecución

### 1. Iniciar el Backend (Spring Boot)

1. Abrir una terminal en `backend`:
   ```bash
   cd backend
   ```
2. Ejecutar la aplicación:
   - Modo con MySQL (base de datos `productdb` en `localhost:3306`):
     ```bash
     ./mvnw spring-boot:run
     ```
   - Modo in-memory con **H2** (sin necesidad de MySQL externo):
     ```bash
     ./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
     ```
3. El backend iniciará en el puerto `http://localhost:8080`.

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
3. Iniciar el servidor React:
   ```bash
   npm start
   ```
4. Ingresar a `http://localhost:3001`. Podrás registrar un usuario nuevo en `/register`, iniciar sesión en `/login` y una vez autenticado navegar por el catálogo protegido de productos en `/products`.

# Ejercicio Individual 2 - Conexión API Externa Clima (Spring Boot RestTemplate + React Vite)

Este proyecto corresponde a la resolución del **Ejercicio Individual N° 2** de la Guía Práctica TP2 de Ingeniería del Software II:
> **REACT: Conexión por API Frontend con Backend de distintas tecnologías: Realizar el ejercicio planteado en la Guía Audiovisual donde se utiliza React para el Frontend y Java para el Backend conectándose ambos por API. "2) API REST PARTE 2: React con Java (PARTE B)"**  
> *Video de referencia:* https://www.youtube.com/watch?v=L5yihOAQOyI

---

## 📁 Estructura del Proyecto

```text
individual-2/
├── backend/                  # API Backend en Spring Boot que consume OpenWeatherMap con RestTemplate
│   ├── src/main/java/        # Controlador y Servicio de Clima
│   ├── src/main/resources/   # Archivo application.properties
│   ├── pom.xml               # Configuración Maven
│   └── mvnw / mvnw.cmd       # Maven Wrapper
├── frontend/                 # Aplicación de Clima en React creada con Vite
│   ├── public/               # Recursos estáticos
│   ├── src/                  # Componentes (InputCiudad, BottonConsulta, DisplayClima, Traduccion)
│   ├── package.json          # Dependencias (axios, react, vite)
│   ├── index.html            # Plantilla HTML
│   └── vite.config.js        # Configuración de Vite (puerto 5174)
└── README.md                 # Documentación del ejercicio
```

---

## ⚙️ Tecnologías Utilizadas

- **Backend:**
  - Java 17+ (compatible con Java 21, 25 y 26)
  - Spring Boot 3.2.x
  - RestTemplate (para consumo de API externa OpenWeatherMap)
  - Maven Wrapper

- **Frontend:**
  - React 18
  - Vite
  - Axios
  - Bootstrap / CSS

---

## 🚀 Instrucciones de Ejecución

### 1. Iniciar el Backend (Spring Boot)

1. Abrir una terminal en `backend`:
   ```bash
   cd backend
   ```
2. Ejecutar con Maven Wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
3. El servidor iniciará en el puerto `http://localhost:8080`.

#### Endpoint disponible:
- `GET /ciudad/clima/{ciudad}`: Consume la API de OpenWeatherMap mediante `RestTemplate` en el servidor y retorna el objeto JSON del clima correspondiente a la ciudad ingresada.

---

### 2. Iniciar el Frontend (React + Vite)

1. Abrir otra terminal en `frontend`:
   ```bash
   cd frontend
   ```
2. Instalar dependencias:
   ```bash
   npm install
   ```
3. Iniciar el servidor de desarrollo Vite:
   ```bash
   npm run dev
   ```
4. Ingresar a `http://localhost:5174` en el navegador, ingresar el nombre de una ciudad (ej. *Mendoza*, *Buenos Aires*, *Madrid*) y presionar "Consultar Clima".

# Plan de Pruebas de Carga y Stress (Testing No Funcional)

**Sistema:** Club Deportivo - Plataforma de Gestión MVC con IA  
**Módulos Evaluados:** Control de Acceso en Molinetes Biométricos y Módulo de Pagos Multicanal (Efectivo, Transferencia, Mercado Pago).  
**Herramientas Empleadas:** Apache JMeter 5.6+, Python Concurrency Test Runner (`testing/stress_test_runner.py`).

---

## 1. Objetivos del Testing de Rendimiento

1. **Pruebas de Carga (Load Testing):**
   - Evaluar el comportamiento del sistema ante un flujo de demanda pico normal (concurrencia de socios en horarios de apertura de actividades deportivas: 08:00 - 10:00 y 18:00 - 20:00).
   - Medir tiempos de respuesta (latencia media, p90, p95) al verificar la condición financiera de cuotas e ingresar por molinete.
2. **Pruebas de Stress (Stress Testing):**
   - Identificar el punto de quiebre (*breaking point*) sometiendo a la aplicación a un volumen creciente de peticiones simultáneas de cobro y validación de acceso.
   - Analizar el comportamiento de la base de datos MySQL (saturación de conexiones del pool HikariCP, *deadlocks* en tablas transaccionales de `cuotas` y `pagos`).
3. **Pruebas de Resistencia (Endurance / Soak Testing):**
   - Verificar la estabilidad de la memoria JVM (detección de *memory leaks* por acumulación de sesiones Spring Security o entidades Hibernate en el contexto de persistencia de primer nivel).

---

## 2. Escenarios de Prueba Diseñados

### Escenario 1: Pico de Ingresos Concurrentes en Molinetes (Load Test)
- **Endpoint objetivo:** `POST /accesos/registrar`
- **Operación:** Lectura de DNI, consulta del estado de cuotas de la familia (`estaAlDia()`), verificación del estado operativo del molinete e inserción del registro de paso.
- **Perfil de Carga:**
  - Hilos concurrentes (Virtual Users): 100 usuarios.
  - Ramp-up: 30 segundos.
  - Duración: 5 minutos sostenidos.
  - Peticiones objetivo: ~50 req/segundo.

### Escenario 2: Avalancha de Pagos al Cierre de Vencimiento (Stress Test)
- **Endpoint objetivo:** `POST /pagos/procesar`
- **Operación:** Procesamiento transaccional de pagos alternando **Efectivo**, **Transferencia** y **Mercado Pago**.
- **Perfil de Stress:**
  - Hilos concurrentes: Crecimiento escalonado de 50 a 500 hilos (step de 50 hilos cada 30 segundos).
  - Duración: 10 minutos.
  - Verificación: Integridad de transacciones `@Transactional`, comprobando que no ocurra doble cobro de cuotas.

---

## 3. Criterios de Aceptación (SLAs y KPIs)

| Indicador (KPI) | Umbral Aceptable (Carga Normal) | Umbral Límite (Stress) |
|---|---|---|
| **Latencia Media (Average)** | < 150 ms | < 800 ms |
| **Percentil 95 (p95)** | < 300 ms | < 1,500 ms |
| **Percentil 99 (p99)** | < 500 ms | < 2,500 ms |
| **Throughput (RPS)** | > 100 req/s | > 250 req/s |
| **Tasa de Errores HTTP (5xx)** | 0.00% | < 1.00% |
| **Uso de CPU / JVM Heap** | < 65% CPU, Heap estable | Garbage Collector recupera sin OOM |

---

## 4. Configuración de Apache JMeter (`testing/jmeter_club_load_stress_plan.jmx`)

El plan de pruebas `.jmx` generado cuenta con:
1. **HTTP Cookie Manager:** Mantiene la cookie de sesión `JSESSIONID` tras la autenticación de Spring Security.
2. **HTTP Header Manager:** Inyecta cabeceras `Content-Type: application/x-www-form-urlencoded` y `User-Agent`.
3. **Controlador de Autenticación Previa:** Ejecuta `POST /login` con credenciales de `operador` / `operador123`.
4. **RegEx / CSS Extractor:** Extrae automáticamente el token CSRF `_csrf` del formulario HTML renderizado por Thymeleaf.
5. **CSV Data Set Config:** Inyecta DNI de socios y cuotas de prueba para simular variedad de usuarios.
6. **Receptores de Métricas:** *Summary Report*, *Aggregate Report*, *View Results Tree* y *Response Time Graph*.

---

## 5. Ejecutor de Pruebas Automatizado en Python (`testing/stress_test_runner.py`)

Para entornos sin interfaz gráfica de JMeter, se incluye un script autónomo multihilo en Python que:
- Inicia sesión contra Spring Security vía HTTP.
- Dispara solicitudes asíncronas concurrentes con `ThreadPoolExecutor`.
- Recolecta tiempos de respuesta por percentil (min, avg, p50, p90, p95, p99, max).
- Genera un reporte estadístico en consola.


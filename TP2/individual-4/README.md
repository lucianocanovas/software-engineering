# Ejercicio Individual 4 - Patrones de Diseño Creacionales en Java

Este proyecto corresponde a la resolución del **Ejercicio Individual N° 4** de la Guía Práctica TP2 de Ingeniería del Software II:
> **Realizar la siguiente Guía Audio Visual sobre Patrones:**  
> *Playlist de referencia:* https://www.youtube.com/watch?v=fzgSFu55Pbw&list=PLQxX2eiEaqbyrYNWCZPBVB32JIGwrAnht  
> *Repositorio de referencia:* https://github.com/todocodeacademy/CursoPatronesDisenio

---

## 📁 Estructura del Proyecto

El proyecto está configurado como un proyecto multimódulo de Maven compuesto por 5 submódulos correspondientes a los patrones creacionales implementados:

```text
individual-4/
├── 1-singleton/              # Patrón Singleton (Instancia única de configuración global)
│   ├── src/main/java/        # Configuracion.java y Main.java
│   └── pom.xml
├── 2-factory-method/         # Patrón Factory Method (Creación de tipos de envío)
│   ├── src/main/java/        # Envio, EnvioCamion, EnvioMoto, EnvioCorreo y Creators
│   └── pom.xml
├── 3-abstract-factory/       # Patrón Abstract Factory (Familias de ropa: Outfit)
│   ├── src/main/java/        # Jeans, Remeras y Fábricas (Urbano, Clásico, Elegante)
│   └── pom.xml
├── 4-builder/                # Patrón Builder (Construcción paso a paso de Casa)
│   ├── src/main/java/        # Casa, BuilderCasa, BuilderConcretoCasa, BuilderDirector
│   └── pom.xml
├── 5-prototype/              # Patrón Prototype (Clonación de pedidos de pizza)
│   ├── src/main/java/        # PrototipoPizza, PedidoPizza, PedidoPizzaEspecial
│   └── pom.xml
├── pom.xml                   # POM padre agregador
├── mvnw / mvnw.cmd           # Maven Wrapper
└── README.md                 # Documentación del ejercicio
```

---

## 📚 Resumen de Patrones Implementados

### 1. Singleton
- **Propósito:** Garantizar que una clase tenga una única instancia en toda la aplicación y proporcionar un punto de acceso global a ella.
- **Caso de uso aplicado:** Objeto de `Configuración` del sistema compartido entre módulos de Autenticación y Reportes. Ambos módulos obtienen exactamente la misma referencia en memoria.

### 2. Factory Method
- **Propósito:** Definir una interfaz para crear un objeto, pero dejar que las subclases decidan qué clase instanciar.
- **Caso de uso aplicado:** Sistema logístico de `Envío` (`EnvioCamion`, `EnvioMoto`, `EnvioCorreo`), donde cada `Creator` se encarga de instanciar el medio de transporte correspondiente.

### 3. Abstract Factory
- **Propósito:** Proporcionar una interfaz para crear familias de objetos relacionados o dependientes sin especificar sus clases concretas.
- **Caso de uso aplicado:** Tienda de ropa donde se construyen conjuntos de outfits (`Remera` + `Jean`) agrupados por estilo (`OutfitUrbano`, `OutfitClasico`, `OutfitElegante`).

### 4. Builder
- **Propósito:** Separar la construcción de un objeto complejo de su representación, permitiendo que el mismo proceso de construcción pueda crear diferentes representaciones.
- **Caso de uso aplicado:** Construcción de una `Casa` especificando paso a paso paredes, techo, puertas, ventanas y piscina a través de un `BuilderDirector`.

### 5. Prototype
- **Propósito:** Especificar los tipos de objetos a crear mediante una instancia prototípica y crear nuevos objetos copiando (clonando) este prototipo.
- **Caso de uso aplicado:** Clonación de pedidos de pizza base (`PedidoPizza`) para crear pedidos especiales (`PedidoPizzaEspecial`) modificando solo ingredientes adicionales sin reconstruir el objeto desde cero.

---

## 🚀 Instrucciones de Compilación y Ejecución

### Compilar todos los patrones juntos
Desde el directorio `individual-4`:
```bash
./mvnw clean compile
```

### Ejecutar cada patrón

- **1. Singleton:**
  ```bash
  java -cp "1-singleton/target/classes" com.todocodeacademy.Main
  ```

- **2. Factory Method:**
  ```bash
  java -cp "2-factory-method/target/classes" com.todocodeacademy.Main
  ```

- **3. Abstract Factory:**
  ```bash
  java -cp "3-abstract-factory/target/classes" com.todocodeacademy.Main
  ```

- **4. Builder:**
  ```bash
  java -cp "4-builder/target/classes" com.todocodeacademy.Main
  ```

- **5. Prototype:**
  ```bash
  java -cp "5-prototype/target/classes" com.todocodeacademy.Main
  ```

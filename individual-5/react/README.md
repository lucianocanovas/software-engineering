# Contact Manager App - Tutorial React

Proyecto desarrollado siguiendo el contenido teórico/práctico visto en clase y la **Guía Audiovisual**:
- **Video Tutorial**: [Learn React JS with Project in 2 Hours | React Tutorial for Beginners | React Project Crash Course](https://www.youtube.com/watch?v=QoJGKwo20is)
- **Repositorio de Referencia**: [dmalvia/React_Tutorial_Contact_Manager_App](https://github.com/dmalvia/React_Tutorial_Contact_Manager_App)

---

## 1. Conceptos Teóricos y Prácticos Aplicados

El proyecto implementa los fundamentos centrales de React de forma limpia y moderna:

| Concepto | Ubicación en el Código | Descripción y Aplicación |
|---|---|---|
| **Arquitectura Basada en Componentes** | `src/components/` | Descomposición de la interfaz en unidades independientes y reutilizables: `Header`, `AddContact`, `ContactList`, `ContactCard`. |
| **Sintaxis JSX** | Todos los componentes `.tsx` | Sintaxis declarativa que combina HTML y JavaScript (`className`, expresiones dinámicas `{...}`, eventos sintéticos). |
| **Props (Propiedades)** | `AddContact`, `ContactList`, `ContactCard` | Flujo de datos unidireccional de componentes padres a hijos. |
| **Elevación del Estado (*Lifting State Up*)** | `App.tsx` &harr; `AddContact.tsx` / `ContactCard.tsx` | Las funciones de mutación (`addContactHandler`, `removeContactHandler`) se declaran en el componente padre y se pasan como callbacks por props. |
| **Componentes Controlados** | `AddContact.tsx` | Los campos `name` y `email` están atados al estado reactivo (`value={name}` y `onChange`), siendo React la única fuente de la verdad. |
| **Hook `useState`** | `App.tsx`, `AddContact.tsx`, `ContactList.tsx` | Manejo del estado local inmutable tanto para la lista de contactos como para los formularios y la barra de búsqueda. |
| **Hook `useEffect` & Persistencia** | `App.tsx` | Sincronización automática con `localStorage` ante cualquier cambio en el estado `contacts`. |
| **Renderizado de Listas & Prop `key`** | `ContactList.tsx` | Uso de `.map()` con `key={contact.id}` único (mediante `uuid`) para optimizar el algoritmo de reconciliación del Virtual DOM. |
| **Filtrado Reactivo en Tiempo Real** | `ContactList.tsx` | Búsqueda interactiva de contactos por nombre o correo electrónico. |
| **Estilos con Semantic UI** | `index.html` & `App.css` | Uso del framework Semantic UI para contenedores, tarjetas, inputs con iconos, botones circulares y avatares. |

---

## 2. Estructura de Archivos

```
react/
├── index.html                  # Inclusión de Semantic UI y FontAwesome CDN
├── package.json                # Dependencias (React 19, TypeScript, uuid, Vite)
├── vite.config.ts
├── src/
│   ├── main.tsx                # Punto de entrada y montaje en el DOM (#root)
│   ├── App.tsx                 # Componente contenedor principal y estado global
│   ├── App.css                 # Estilos específicos del Contact Manager
│   ├── index.css               # Estilos base
│   ├── types/
│   │   └── contact.ts          # Interfaz TypeScript de Contacto (id, name, email)
│   ├── images/
│   │   ├── user.svg            # Avatar vectorial de usuario
│   │   └── user.png            # Avatar PNG para compatibilidad
│   └── components/
│       ├── Header.tsx          # Barra de navegación superior fija
│       ├── AddContact.tsx      # Formulario controlado de alta con validaciones
│       ├── ContactList.tsx     # Lista reactiva de contactos con barra de búsqueda
│       └── ContactCard.tsx     # Tarjeta individual con avatar y botón de borrado
```

---

## 3. Instrucciones de Ejecución

1. Abrir la terminal en la carpeta del proyecto React:
   ```bash
   cd c:\Users\Lucho\Desktop\software-engineering\individual-5\react
   ```

2. Instalar dependencias (si no se han instalado previamente):
   ```bash
   npm install
   ```

3. Iniciar el servidor de desarrollo Vite:
   ```bash
   npm run dev
   ```

4. Abrir en el navegador web la URL indicada por Vite (habitualmente **`http://localhost:5173`**).

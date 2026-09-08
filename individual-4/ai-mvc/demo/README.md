# Ferreteria MVC con IA

Implementacion del ejercicio integrador de Ferreteria usando arquitectura MVC, Thymeleaf, Bootstrap 5, ORM con JPA y una base SQLite local.

## Arquitectura

- `model/Producto.java`: entidad de dominio. `@Entity` la mapea a la tabla `productos`; las anotaciones de validacion protegen tanto el formulario como la API.
- `repository/ProductoRepository.java`: acceso a datos. Extiende `JpaRepository`, por lo que Spring Data genera las operaciones CRUD sin SQL manual.
- `service/ProductoService.java`: capa de negocio. Coordina el repositorio y concentra las operaciones listar, buscar, guardar, actualizar y eliminar.
- `controller/ProductoController.java`: controlador MVC. Recibe peticiones del navegador, carga el `Model` y selecciona las vistas Thymeleaf.
- `controller/ProductoRestController.java`: adaptador REST opcional. Expone el mismo caso de uso en `/api/productos`, sin duplicar reglas de negocio.
- `templates/productos/`: vistas HTML procesadas en servidor por Thymeleaf.
- `static/css/styles.css`: estilos propios de la interfaz; Bootstrap se incorpora por CDN.

## Funcionalidades

- Listado de productos en `/productos`.
- Alta desde `/productos/nuevo`.
- Edicion desde la accion `Editar`.
- Eliminacion con confirmacion desde la tabla.
- Validacion de nombre, marca, categoria, precio, stock y descripcion.
- API REST: `GET`, `POST`, `PUT` y `DELETE` en `/api/productos`.
- SQLite se guarda en `ferreteria.db` y Hibernate crea/actualiza la tabla automaticamente.

## Ejecutar

Desde esta carpeta:

```powershell
mvn spring-boot:run
```

Luego abrir `http://localhost:8080/productos`.

Para verificar el proyecto completo:

```powershell
mvn test
```

La aplicacion fue comprobada con Maven 3.9.9, Java 26 y Spring Boot 3.5.6; el `pom.xml` compila con nivel Java 21 para conservar compatibilidad.
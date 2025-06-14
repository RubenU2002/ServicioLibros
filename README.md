# ServicioLibros

Aplicación Spring Boot para gestionar préstamos de libros en una institución educativa. Permite registrar usuarios y libros, solicitar y devolver préstamos, calcular multas por retraso y consultar reportes.

## Configuración

La aplicación utiliza una base de datos en memoria H2 y se configura automáticamente al iniciar. Para ejecutar el proyecto desde la línea de comandos:

```bash
./mvnw spring-boot:run
```

Al levantar la aplicación estarán disponibles:

- Consola H2: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- Documentación Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Ejemplo de uso con Swagger

1. **Crear un usuario**
   - Endpoint: `POST /api/usuarios`
   - Cuerpo de ejemplo:
     ```json
     { "nombre": "Juan", "rol": "ESTUDIANTE" }
     ```

2. **Registrar un libro**
   - Endpoint: `POST /api/libros`
   - Cuerpo de ejemplo:
     ```json
     { "titulo": "La Odisea", "autor": "Homero" }
     ```

3. **Solicitar un préstamo**
   - Endpoint: `POST /api/prestamos/solicitar`
   - Cuerpo de ejemplo:
     ```json
     { "usuarioId": "1", "libroId": "1", "fechaEntrega": "2024-08-25" }
     ```

4. **Devolver un libro**
   - Endpoint: `POST /api/prestamos/devolver/{prestamoId}`

5. **Consultar reportes**
   - Libros: `GET /api/reportes/libros`
   - Libros prestados: `GET /api/reportes/libros-prestados`
   - Usuarios: `GET /api/reportes/usuarios`
   - Multas: `GET /api/reportes/multas`

Los endpoints anteriores pueden probarse directamente desde la interfaz Swagger.

## Docker

Para construir y ejecutar la aplicación en un contenedor Docker:

```bash
docker build -t serviciolibros .
docker run -p 8080:8080 serviciolibros
```

Al levantar el contenedor, la interfaz Swagger estará disponible en [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

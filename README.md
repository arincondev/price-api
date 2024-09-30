# Prices API

Este proyecto es una API que gestiona precios para productos y marcas. El sistema devuelve el precio correcto para un producto en función de una fecha de consulta.

## Requisitos

- Java 17
- Maven
- H2 Database (en memoria)

## Instrucciones de ejecución

1. Clona este repositorio.
2. Compila el proyecto con Maven:
    ```bash
    mvn clean install
    ```
3. Ejecuta la aplicación:
    ```bash
    mvn spring-boot:run
    ```
4. Accede a la API en:
    ```
    http://localhost:8080/api/prices?productId=35455&brandId=1&applicationDate=2020-06-14T10:00:00
    ```

## Parámetros de Entrada

### `productId` (Obligatorio)
- **Descripción**: ID del producto para el que se desea consultar el precio.
- **Tipo**: `Long`
- **Ejemplo**: `35455`

### `brandId` (Obligatorio)
- **Descripción**: ID de la marca a la que pertenece el producto.
- **Tipo**: `Long`
- **Ejemplo**: `1`

### `applicationDate` (Obligatorio)
- **Descripción**: Fecha y hora en la que se desea consultar el precio.
- **Tipo**: `String (ISO 8601 Date-Time)`
- **Formato**: `YYYY-MM-DDTHH:mm:ss`
- **Ejemplo**: `2020-06-14T10:00:00`

## Consola H2

Puedes acceder a la consola H2 en:
```
http://localhost:8080/h2-console
```
Usa los siguientes valores:
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: `password`

## Documentación Swagger

Puedes acceder a la documentación de Swagger en:
```
http://localhost:8080/swagger-ui.html
```

## Pruebas

Para ejecutar las pruebas unitarias, usa el siguiente comando:
```bash
mvn test
```

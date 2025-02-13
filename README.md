# ECommerce Order System API

## Descripción General
Se está utilizando una arquitectura en capas. El código está organizado en capas separadas, cada una con una responsabilidad específica.
- **Controlador (Controller)**: Maneja las solicitudes HTTP y las respuestas, delegando la lógica de negocio a los servicios.
- **Servicio (Service)**: Contiene la lógica de negocio y se comunica con los repositorios para acceder a los datos.
- **Repositorio (Repository)**: Interactúa con las bases de datos para realizar operaciones CRUD.
- **Modelo (Model)**: Define las entidades y sus relaciones.

Esta API proporciona endpoints para la gestión de productos, pedidos y clientes en un sistema de comercio electrónico. También incluye endpoints de autenticación para el inicio de sesión y registro de usuarios.

## Endpoints

### Endpoints de Productos
- **GET /products**: Recupera todos los productos.
- **GET /products/{id}**: Recupera un producto específico por su ID.
- **POST /products**: Crea un nuevo producto.
- **PUT /products/{id}**: Actualiza un producto existente por su ID.
- **DELETE /products/{id}**: Elimina un producto por su ID.

### Endpoints de Pedidos
- **GET /orders**: Recupera todos los pedidos.
- **GET /orders/{id}**: Recupera un pedido específico por su ID.
- **POST /orders**: Crea un nuevo pedido.
- **POST /orders/checkout**: Finaliza el pedido para un cliente específico.
- **DELETE /orders/{id}**: Elimina un pedido por su ID.
- **DELETE /orders/cart**: Elimina un producto del pedido pendiente de un cliente.

### Endpoints de Clientes
- **GET /customers**: Recupera todos los clientes.
- **GET /customers/{id}**: Recupera un cliente específico por su ID.
- **POST /customers**: Crea un nuevo cliente.
- **PUT /customers/{id}**: Actualiza un cliente existente por su ID.
- **DELETE /customers/{id}**: Elimina un cliente por su ID.

### Endpoints de Autenticación
- **POST /auth/login**: Autentica a un usuario y devuelve un token JWT.
- **POST /auth/register**: Registra un nuevo usuario y devuelve un token JWT.

## Ejecución de la API

### Prerrequisitos
- Docker
- Docker Compose

### Configuración
1. Clona el repositorio:
    ```sh
    git clone https://github.com/SefedeamU/Aplicaci-n-de-Gesti-n-de-Inventario.git
    cd Aplicaci-n-de-Gesti-n-de-Inventario
    ```

2. Configurar el archivo `.env` con las credenciales adecuadas y las variables de entorno requeridas.

3. Construye y ejecuta los contenedores Docker:
    ```sh
    docker-compose up --build
    ```

### Variables de Entorno
La API utiliza variables de entorno para la configuración. Asegúrate de que las siguientes variables estén configuradas en tu archivo `.env`:
- `SPRING_R2DBC_URL`
- `SPRING_R2DBC_USERNAME`
- `SPRING_R2DBC_PASSWORD`
- `JWT_SECRET`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`


# Relación entre Clases

### Productos
La clase `ProductPsql` representa un producto en la base de datos PostgreSQL. Contiene información como el nombre, la descripción y el precio del producto.

### Clientes
La clase `CustomerPsql` representa un cliente en la base de datos PostgreSQL. Contiene información como el nombre, el correo electrónico, la contraseña y el rol del cliente.

### Pedidos
La clase `OrderPsql` representa un pedido en la base de datos PostgreSQL. Contiene información como el ID del cliente, una lista de IDs de productos, el total del pedido y el estado del pedido.

## Relación
- Un cliente puede realizar múltiples pedidos.
- Un pedido puede contener múltiples productos.
- Los productos se almacenan en PostgreSQL, mientras que los pedidos se almacenan en MongoDB.

# Desarrollo y Características

### Manejo de Errores
El manejo de errores se realiza mediante un `GlobalExceptionHandler` que captura excepciones específicas como `ResourceNotFoundException`, `EmailNotRegisterdException`, `IncorrectPasswordException` y `UserAlreadyExistException`, devolviendo respuestas HTTP adecuadas.

### Manejo de Transacciones
El manejo de transacciones se realiza utilizando `TransactionUtil`, que ejecuta operaciones dentro de una transacción reactiva utilizando `ReactiveTransactionManager`.

### API Reactiva
La API está desarrollada utilizando **Spring WebFlux** para proporcionar un modelo de programación reactivo. Las operaciones de base de datos se realizan de manera no bloqueante utilizando `ReactiveCrudRepository` para PostgreSQL y `ReactiveMongoRepository` para MongoDB.

# Librerías Principales
- **Spring Boot**
- **Spring WebFlux**
- **Spring Data R2DBC**
- **Spring Data MongoDB**
- **Spring Security**
- **JWT**
- **Lombok**

# SwaggerHub:

```
https://app.swaggerhub.com/apis-docs/SergioFelipeDelgadoAmado(Sefedeam)/GestionInventario/1.0.0
```

# API de Gestión de Franquicias

Este proyecto implementa una API RESTful para la gestión de franquicias, sus sucursales y productos, siguiendo una arquitectura hexagonal y principios SOLID.

## Características principales

- **Arquitectura Hexagonal**: Separación clara entre dominio, aplicación e infraestructura.
- **Programación Reactiva**: Implementación basada en WebFlux para operaciones no bloqueantes.
- **MongoDB**: Persistencia de datos utilizando MongoDB.
- **Dockerizado**: Aplicación empaquetada en contenedores Docker para facilitar su despliegue.
- **Swagger UI**: Documentación interactiva de la API.
- **Validación de datos**: Implementación de validaciones en DTOs.
- **Programación Funcional**: Uso de streams y expresiones lambda para manipulación de datos.

## Requisitos previos

- JDK 17
- Maven 3.8+
- Docker y Docker Compose
- MongoDB (opcional, si no usas Docker)

## Estructura del proyecto

El proyecto sigue una arquitectura hexagonal (Ports and Adapters) con la siguiente estructura:

```
src/main/java/com/nequi/franquicias/
├── application
│   ├── ports
│   │   ├── input (Servicios - puertos de entrada)
│   │   └── output (Repositorios - puertos de salida)
│   └── services (Implementación de casos de uso)
├── domain
│   └── model (Entidades de dominio)
├── infrastructure
│   ├── adapters
│   │   ├── input
│   │   │   └── rest (Controladores)
│   │   └── output
│   │       └── persistence (Implementación de repositorios)
│   └── config (Configuraciones de la aplicación)
└── FranquiciasApplication.java
```

## Instalación y ejecución

### Usando Docker (recomendado)

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/franquicias-api.git
   cd franquicias-api
   ```

2. Construye y ejecuta los contenedores con Docker Compose:
   ```bash
   # En Linux/Mac
   chmod +x docker-build-run.sh
   ./docker-build-run.sh
   
   # En Windows (PowerShell)
   .\docker-build-run.ps1
   ```

3. La aplicación estará disponible en [http://localhost:8080](http://localhost:8080)

### Ejecución manual (sin Docker)

1. Asegúrate de tener MongoDB instalado y ejecutándose en el puerto 27017.

2. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/franquicias-api.git
   cd franquicias-api
   ```

3. Compila y ejecuta la aplicación:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. La aplicación estará disponible en [http://localhost:8080](http://localhost:8080)

## Documentación de la API

Una vez que la aplicación esté en ejecución, puedes acceder a la documentación interactiva de la API en:
- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Endpoints disponibles

### Endpoints REST tradicionales (no reactivos)

```
POST   /api/franquicias                                         # Crear franquicia
GET    /api/franquicias                                         # Obtener todas las franquicias
GET    /api/franquicias/{id}                                    # Obtener franquicia por ID
PUT    /api/franquicias/{id}                                    # Actualizar franquicia
DELETE /api/franquicias/{id}                                    # Eliminar franquicia

POST   /api/franquicias/{franquiciaId}/sucursales              # Agregar sucursal
DELETE /api/franquicias/{franquiciaId}/sucursales/{sucursalId}  # Eliminar sucursal

POST   /api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos               # Agregar producto
DELETE /api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}   # Eliminar producto
PATCH  /api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock  # Actualizar stock

GET    /api/franquicias/{franquiciaId}/productos/mas-stock      # Obtener productos con más stock por sucursal
```

### Endpoints Reactivos

```
POST   /api/reactive/franquicias                                         # Crear franquicia
GET    /api/reactive/franquicias                                         # Obtener todas las franquicias
GET    /api/reactive/franquicias/{id}                                    # Obtener franquicia por ID
PUT    /api/reactive/franquicias/{id}                                    # Actualizar franquicia
DELETE /api/reactive/franquicias/{id}                                    # Eliminar franquicia

PATCH  /api/reactive/franquicias/{id}/nombre                             # Actualizar nombre de franquicia
PATCH  /api/reactive/franquicias/{franquiciaId}/sucursales/{sucursalId}/nombre  # Actualizar nombre de sucursal
PATCH  /api/reactive/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/nombre  # Actualizar nombre de producto

POST   /api/reactive/franquicias/{franquiciaId}/sucursales              # Agregar sucursal
DELETE /api/reactive/franquicias/{franquiciaId}/sucursales/{sucursalId}  # Eliminar sucursal

POST   /api/reactive/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos               # Agregar producto
DELETE /api/reactive/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}   # Eliminar producto
PATCH  /api/reactive/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock  # Actualizar stock

GET    /api/reactive/franquicias/{franquiciaId}/productos/mas-stock      # Obtener productos con más stock por sucursal
```

## Ejemplos de uso con cURL

### Crear una franquicia

```bash
curl -X POST \
  http://localhost:8080/api/reactive/franquicias \
  -H 'Content-Type: application/json' \
  -d '{
    "nombre": "Mi Franquicia"
}'
```

### Agregar una sucursal

```bash
curl -X POST \
  http://localhost:8080/api/reactive/franquicias/{franquiciaId}/sucursales \
  -H 'Content-Type: application/json' \
  -d '{
    "nombre": "Sucursal Centro"
}'
```

### Agregar un producto

```bash
curl -X POST \
  http://localhost:8080/api/reactive/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos \
  -H 'Content-Type: application/json' \
  -d '{
    "nombre": "Producto 1",
    "stock": 50
}'
```

### Actualizar el stock de un producto

```bash
curl -X PATCH \
  http://localhost:8080/api/reactive/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock \
  -H 'Content-Type: application/json' \
  -d '{
    "nuevoStock": 100
}'
```

## Monitoreo y gestión

### Logs

Para ver los logs de la aplicación cuando se ejecuta en Docker:

```bash
docker-compose logs -f franquicias-api
```

### Base de datos MongoDB

Para acceder a la base de datos MongoDB:

```bash
# Acceder al contenedor de MongoDB
docker exec -it mongodb mongo

# Usar la base de datos
use franquicias_db

# Listar franquicias
db.franquicias.find()
```

## Patrones y principios de diseño implementados

- **SOLID**: Se siguen todos los principios SOLID.
  - **S** (Responsabilidad única): Cada clase tiene una única responsabilidad.
  - **O** (Abierto/Cerrado): Las extensiones se hacen sin modificar el código existente.
  - **L** (Sustitución de Liskov): Los subtipos son sustituibles por sus tipos base.
  - **I** (Segregación de interfaces): Interfaces pequeñas y específicas.
  - **D** (Inversión de dependencias): Dependencia de abstracciones, no implementaciones.

- **Arquitectura Hexagonal (Ports and Adapters)**: Separación clara entre dominio, aplicación e infraestructura.

- **DTO Pattern**: Objetos de transferencia de datos para la capa de presentación.

- **Repository Pattern**: Abstracción para el acceso a datos.

- **Programación Funcional**: Uso de streams, expresiones lambda y programación declarativa.

## Tecnologías utilizadas

- **Spring Boot**: Framework para el desarrollo de aplicaciones Java.
- **Spring WebFlux**: Soporte para programación reactiva.
- **Spring Data MongoDB Reactive**: Acceso reactivo a MongoDB.
- **MongoDB**: Base de datos NoSQL.
- **Lombok**: Reducción de código boilerplate.
- **Springdoc OpenAPI**: Documentación de la API.
- **Docker**: Contenedorización de la aplicación.
- **Maven**: Gestión de dependencias y construcción.

## Contribuciones

Las contribuciones son bienvenidas. Para contribuir:

1. Haz un fork del repositorio
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`)
3. Realiza tus cambios
4. Commit tus cambios (`git commit -am 'Añade nueva funcionalidad'`)
5. Push a la rama (`git push origin feature/nueva-funcionalidad`)
6. Crea un Pull Request

## Licencia

Este proyecto está licenciado bajo la licencia MIT.

## Autor

Desarrollado por [Tu Nombre] para la prueba técnica de Nequi.
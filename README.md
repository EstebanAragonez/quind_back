# Prueba

Este es un proyecto Spring Boot que utiliza PostgreSQL como base de datos y OpenAPI para la documentación de la API.

# Descripcion
Este backend proporciona una API para gestionar solicitudes de vacaciones, permisos y otros tipos de ausencias
para los empleados. La API permite crear, consultar, actualizar y gestionar las solicitudes de manera eficiente, integrándose
con una base de datos para el almacenamiento y recuperación de información.

# Características principales
- Gestión de solicitudes: Los usuarios pueden crear solicitudes de vacaciones, permisos, días no remunerados, y otros tipos de ausencias.
Validaciones de fechas: Se aplican reglas de negocio para validar las solicitudes de acuerdo con el tipo de permiso solicitado. Por ejemplo:

- Las solicitudes de vacaciones deben realizarse con al menos un mes de anticipación.
Los permisos de ausentismo requieren al menos 2 días de anticipación.

- Notificaciones y mensajes: El sistema devuelve mensajes de error personalizados si las solicitudes no cumplen con las reglas de negocio.
Estructura RESTful: La API sigue los principios REST, lo que facilita su integración con cualquier frontend.
Respuestas estructuradas: El sistema maneja tanto respuestas exitosas en formato JSON como mensajes de error en texto plano para facilitar la depuración y el manejo en el frontend.

# Tecnologías utilizadas:

- **Framework:** Java (Spring Boot recomendado)
- **Base de datos:** PostgreSQL
- **Arquitectura:** Hexagonal, para garantizar separación de responsabilidades y flexibilidad en futuras expansiones.

# Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes requisitos en tu entorno:

- **JDK**: versión 17 o superior
- **Maven**: versión 3.6.3 o superior
- **PostgreSQL**: versión 12 o superior

## Dependencias

El proyecto utiliza las siguientes dependencias:

- **Spring Boot Starter Data JPA**: Para el manejo de JPA.
- **Spring Boot Starter Web**: Para crear la API REST.
- **PostgreSQL Driver**: Para la conexión con la base de datos PostgreSQL.
- **Lombok**: Para reducir el código repetitivo (opcional).
- **Spring Boot DevTools**: Herramientas para el desarrollo.
- **Spring Boot Starter Test**: Para pruebas unitarias.
- **SpringDoc OpenAPI**: Para la documentación de la API.

## Ejecucion

### 1. Crear la base de datos

Se debe crear la base de datos en PostgreSQL. En este caso, el nombre de la base de datos es `quind`.

```sql
-- Crear la base de datos quind
CREATE DATABASE quind;
```
cuando se corra el proyecto y se creen la entidades en la base de datos se debe ejecutar las siguiente
sentencias sql para poder ejecutar el proyecto:

```sql
-- Insertar los datos en la tabla documentos
INSERT INTO public.documentos (id, nombre) VALUES
(1, 'CC'),
(2, 'CE'),
(3, 'PS');

-- Insertar los datos en la tabla tipo_solicitudes
INSERT INTO public.tipo_solicitudes (id, nombre) VALUES
(1, 'VC'),
(2, 'DNR'),
(3, 'AU'),
(4, 'OS'),
(5, 'IM');

-- Insertar los datos en la tabla empleados
INSERT INTO public.empleados (id, area, nombre_completo, numero_documento, id_documento) VALUES
(1, 'SISTEMAS', 'Leonardo Aragonez', '1000381834', 1);
```





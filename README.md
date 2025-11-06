# 🍽 Sabor Gourmet: Sistema de Gestión de Restaurantes

## Descripción General del Sistema

Este documento describe la implementación técnica del proyecto “Sabor Gourmet”, un sistema de gestión empresarial desarrollado con Spring Boot para la administración integral de un restaurante. El sistema permite gestionar operaciones CRUD, seguridad, trazabilidad y autenticación de usuarios, aplicando conceptos avanzados como AOP (Programación Orientada a Aspectos) y Spring Security.

Entre sus principales objetivos se encuentran:

* Automatizar el registro y control de clientes, mesas y usuarios.
* Garantizar la **seguridad y trazabilidad** de las operaciones mediante Spring Security y AOP.
* Proporcionar una interfaz moderna, adaptable y fácil de usar, desarrollada con Thymeleaf y Bootstrap/CSS Responsivo.

---

## 2. Tecnologías y Arquitectura

El sistema se construyó siguiendo el patrón **Model-View-Controller (MVC)** y bajo una arquitectura modular que favorece la escalabilidad y el mantenimiento.

### 2.1. Tecnologías y Dependencias Principales

| Componente | Tecnología |
| :--- | :--- |
| **Framework principal** | Spring Boot 3+ |
| **Frontend / Vistas** | Thymeleaf + Bootstrap |
| **Persistencia** | Spring Data JPA (conector MySQL) |
| **Seguridad** | Spring Security (con cifrado BCrypt) |
| **AOP / Auditoría** | Spring AOP |
| **Diseño y Estilo** | Bootstrap 5 y CSS Responsivo |

### 2.2. Dependencias esenciales (Maven)

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
</dependencies>
```
## 3. Configuración de la Base de Datos
Antes de ejecutar la aplicación, se debe crear una base de datos en MySQL con el nombre sabor_gourmet (o el nombre que se prefiera).

Si se desea cambiar el nombre o las credenciales de conexión, esto se configura en el archivo application.properties:

### Properties
```xml
# Configuración de la conexión a la base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/sabor_gourmet?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=tu_contraseña

# Configuración de JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```
💡 Nota: Si la base de datos no existe, se debe crear manualmente desde MySQL Workbench o la terminal antes de iniciar el proyecto.

## 4. Módulos Funcionales Implementados
### 4.1. Módulo de Clientes y Mesas (Módulo 1)
Estado: Completado ✅

Descripción:

Implementación completa del ciclo CRUD para las entidades Cliente y Mesa.

Se cubren los requisitos funcionales de registro de clientes (RF1) y gestión de mesas (RF2), permitiendo visualizar y modificar el estado del salón en tiempo real.

### 4.2. Módulo de Administración y Seguridad (Módulo 6)
Estado: Completado ✅

Características:

CRUD completo para la entidad Usuario, con roles predefinidos: ADMIN, MOZO y COCINERO.

Implementación de control de acceso basado en roles (RF18).

Cifrado de contraseñas mediante BCrypt (RNF1).

Prevención de auto-eliminación del usuario autenticado.

Integración con Thymeleaf Security para restringir vistas dinámicamente.

## 5. Implementación de Conceptos Avanzados
### 5.1. Spring Security (Autenticación y Autorización)
Configuración de rutas seguras según rol.

Integración con formularios personalizados de inicio de sesión.

Robustez de Vistas: Manejo de errores con el operador de navegación segura (?.) para evitar fallos de renderizado (Error 500) en Thymeleaf al intentar acceder a objetos de autenticación nulos.

### 5.2. Programación Orientada a Aspectos (AOP)
Implementación de un LoggingAspect que intercepta los métodos de los servicios de negocio.

Trazabilidad: Registro automático de todas las operaciones CRUD en la entidad Bitacora.

Cumplimiento del requisito RF17: trazabilidad completa de acciones realizadas por los usuarios en los módulos implementados.
## 📸 6. Evidencias del Sistema

### Figura 1. Pantalla de inicio de sesión con validación de credenciales.

<img width="1821" height="859" alt="image" src="https://github.com/user-attachments/assets/84d7d1f6-91eb-497a-a4f7-bdebba498c9f" />

###  Figura 2. Página principal o pantalla de inicio (Home)
<img width="1901" height="850" alt="image" src="https://github.com/user-attachments/assets/0e121211-b6c3-48b1-80a5-eb1c7339cf1f" />

###  Figura 3. Módulo de gestión de clientes.
<img width="1913" height="507" alt="image" src="https://github.com/user-attachments/assets/9a730d69-6837-4aa1-87af-0a1b9c190b88" />

### Figura 4. Módulo de gestión de mesas.
<img width="1912" height="638" alt="image" src="https://github.com/user-attachments/assets/f65c57cf-0365-4448-a70d-8053640db196" />

### Figura 5. Módulo de gestión de usuarios (solo rol ADMIN).
<img width="1917" height="555" alt="image" src="https://github.com/user-attachments/assets/c3881e04-a72a-4587-8959-0551cac9d23a" />

### Figura 6. Módulo de bitácora (auditoría del sistema).
<img width="1911" height="727" alt="image" src="https://github.com/user-attachments/assets/f677dfa7-84a7-49cf-86fa-60bd088b3fb7" />


# My Music Journal - Backend

## 🎯 Objetivos

MyMusicJournal es una aplicación web para registrar conciertos y eventos musicales. Los usuarios pueden crear, editar y eliminar entradas con información del artista, fecha, lugar, ciudad, calificación y notas. Desarrollada con React, Spring Boot y PostgreSQL, sigue una arquitectura MVC y utiliza una API RESTful.

## 🧩 Competencias Técnicas

Este proyecto desarrolla las siguientes competencias técnicas:

- **Backend Development:** Implementación de la lógica del servidor y los endpoints RESTful.
- **Database Creation:** Creación y estructuración de la base de datos PostgreSQL.
- **Data Access Components:** Desarrollo de componentes que permiten la comunicación entre la API y la base de datos.
- **Tests:** Validación del comportamiento del sistema utilizando herramientas como JUnit y Mockito.

## ⚙️ Tecnologías y Herramientas

Este proyecto fue desarrollado utilizando un conjunto moderno de tecnologías y herramientas que garantizan rendimiento, escalabilidad y buenas prácticas en el desarrollo backend:

- **Lenguaje de Programación:** Java 21
- **Framework Backend:** Spring Boot 3.3.5
- **Base de Datos:** PostgreSQL 42.7.3
- **Gestión de Proyectos:** Jira
- **Control de Versiones:** Git - GitHub
- **Pruebas de API:** Postman 11.41
- **Testing:**
  - spring-boot-starter-test
  - Mockito
  - JUnit

## ✨ Funcionalidades

La aplicación ofrece un conjunto completo de operaciones para la gestión de eventos de Code Crafters:

**Público (sin autenticación)**

- **Autenticación y cuentas**

- Registro e inicio de sesión con validaciones.
- Persistencia del token JWT en localStorage.
- Cierre de sesión seguro.

**Perfil de usuario**

- **Gestión de Conciertos**

-

## 📱 Relaciones:

- Usuario → Journal (1:N): Cada usuario puede tener muchas entradas en su diario.
- Concierto → Journal (1:N): Un concierto puede aparecer en muchas entradas de diario (de uno o varios usuarios).
- Usuario ↔ Concierto (N:M) a través de Journal: La relación entre usuarios y conciertos es muchos a muchos, materializada por JOURNAL.

## 🚀 Cómo iniciar el proyecto

### Requisitos previos

- Java 21 instalado
- PostgreSQL instalado y en ejecución
- Maven

### Pasos para iniciar el proyecto

1. **Clonar el repositorio**

   ```bash
   git clone https://github.com/DaniPacheco8/MyMusicJournal-BackEnd
   ```

2. **Configurar la base de datos**

   - Crear una base de datos en PostgreSQL:

   ```sql
   CREATE DATABASE mymusicjournal;
   ```

3. **Verificar la instalación**
   - La API estará disponible en: `http://localhost:8080`
   - Puedes probar los endpoints con Postman o cualquier cliente HTTP

## 🧩 Estructura del Proyecto

A continuación se muestra la estructura del proyecto My Music Journal, organizada por capas siguiendo la arquitectura estándar de una aplicación Spring Boot

```
MYMUSICJOURNAL-BACKEND

```

## 👩‍💻 Contactos

¿Tienes dudas o quieres saber más sobre el proyecto?

Puedes contactarme a través de mis perfiles profesionales:

| Nombre               | Rol       | LinkedIn                                                 | GitHub                                    |
| -------------------- | --------- | -------------------------------------------------------- | ----------------------------------------- |
| **Daniella Pacheco** | Developer | [LinkedIn](https://www.linkedin.com/in/daniellapacheco/) | [GitHub](https://github.com/DaniPacheco8) |


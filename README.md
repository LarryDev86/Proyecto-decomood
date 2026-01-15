# Proyecto-decomood (DecoMood)

Aplicación web desarrollada con **Spring Boot** y **Thymeleaf** que ofrece un **formulario de contacto**.
El usuario introduce su **nombre**, **email** y una **descripción**, y la aplicación:

- valida el formato del email (Gmail/Hotmail/iCloud)
- envía un correo de confirmación al usuario
- envía un correo con los datos a la empresa

Si el email es válido se muestra una vista de éxito, y si no, una vista de error.

---

## Tecnologías
- Java
- Spring Boot
- Thymeleaf
- Jakarta Mail (SMTP)
- Maven
- Html
- Css
---

## Funcionalidades
- Página principal: `GET /` → `index.html`
- Página del formulario: `GET /Formulario` → `Formulario.html`
- Gestión del formulario: `GET /gestion`
  - valida el email
  - envía 2 correos:
    - al usuario (mensaje de confirmación)
    - a la empresa (resumen de la solicitud)
  - devuelve:
    - `salidaOk.html` si todo va bien
    - `salidaError.html` si el email no cumple el formato esperado

---

## Estructura del proyecto
```text
src/
├── main/
│   ├── java/com/DecoMood/
│   │   ├── controller/
│   │   │   └── ControllerDecomood.java
│   │   └── service/
│   │       └── ClaseEmail.java
│   └── resources/
│       ├── static/
│       │   ├── estilos/
│       │   └── imagenes/
│       ├── templates/
│       │   ├── index.html
│       │   ├── Formulario.html
│       │   ├── salidaOk.html
│       │   └── salidaError.html
│       ├── application.example.properties
│       └── application.properties  (ignorado)

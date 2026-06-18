### 3. 🔑 `README.md` para **ms-anfitriones** (Puerto 8082)

```markdown
# 🔑 HomeRentSolution - Microservicio de Anfitriones (ms-anfitriones)

Este microservicio gestiona los perfiles de los usuarios arrendadores (Anfitriones), sus estados de verificación, datos de contacto y el scoring interno del negocio.

---

## 🛠️ Tecnologías y Requisitos

* **Java:** 25
* **Framework:** Spring Boot 4.0.6
* **Base de Datos:** MySQL 8.x (vía Spring Data JPA)
* **Documentación:** Springdoc OpenAPI v2 (Swagger)

---

## ⚙️ Configuración del Entorno (`application.yml`)

El servicio cuenta con una arquitectura de perfiles dinámicos:
* **Perfil `dev` (Desarrollo):** Conectado a la base de datos `db_anfitriones_dev`.
* **Perfil `test` (Pruebas):** Entorno aislado para validación de lógica de negocio.

### Puerto de Escucha:
* **Local:** `http://localhost:8082`

---

## 🚀 Instalación y Despliegue Local

1. Asegúrate de tener creada la base de datos en tu MySQL local: `db_anfitriones_dev`.

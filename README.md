# 🎮 JUEGOS.NET - Sistema de Gestión Comercial y Catálogo de Videojuegos

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![JasperReports](https://img.shields.io/badge/JasperReports-6.21.3-red.svg)](https://community.jaspersoft.com/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-purple.svg)](https://getbootstrap.com/)

Sistema web integral desarrollado en **Spring Boot** para la gestión comercial, control de inventario de videojuegos, directorio de proveedores, administración de usuarios y generación de reportes analíticos oficiales en PDF.

---

## 📌 Características Principales

### 1. 🔐 Seguridad y Control de Acceso por Roles (RBAC)
- **ADMINISTRADOR (`admin@juegos.net`):** Acceso integral al sistema. Puede crear, editar, dar de baja lógica y restaurar videojuegos, proveedores y clientes. Además cuenta con la potestad de administrar los roles de cada usuario y gestionar la papelera de reciclaje.
- **VENDEDOR (`vendedor1@juegos.net`):** Orientado a la operación diaria. Puede consultar los catálogos, acceder al Dashboard analítico y generar todos los reportes PDF. No posee privilegios para modificar datos ni cambiar roles de usuarios.
- **CLIENTE:** Registro libre y seguro con validaciones completas. Visualización interactiva del catálogo con filtros por categoría y stock.
- **Interceptor de Sesión (`AuthInterceptor`):** Protege las rutas internas evitando accesos indebidos sin autenticación.

### 2. ♻️ Borrado Lógico (Soft Delete) y Módulo de Papelera
- Ningún dato crítico se destruye físicamente de la base de datos (`activo = false`).
- Módulo exclusivo de **Papelera de Reciclaje** para el Administrador, permitiendo auditar y restaurar con un solo clic cualquier videojuego, cliente o proveedor dado de baja.
- Preserva la integridad referencial histórica de ventas y transacciones.

### 3. 📊 Dashboard Analítico en Tiempo Real
- Métricas consolidadas: total de videojuegos activos, alertas preventivas de bajo stock ($\le 30$), cantidad de proveedores y desglose de usuarios por rol (Clientes, Vendedores y Admins).
- Tabla interactiva de semaforización de stock (Crítico, Medio y Óptimo).

### 4. 📄 Reportes Profesionales con JasperReports
- Generación de reportes ejecutivos en PDF para:
  - **Catálogo de Videojuegos** (`juegos.pdf`)
  - **Directorio de Proveedores** (`proveedores.pdf`)
  - **Padrón de Usuarios y Clientes** (`clientes.pdf`)
- Incluyen:
  - Logotipo institucional (`logo.jpeg`).
  - Título y subtítulos estilizados.
  - **Fecha y hora exacta de emisión** tomada de la máquina cliente en tiempo real.
  - Numeración automática de páginas `"Página X de Y"`.

---

## 👥 Cuentas de Demostración

| Rol | Correo Electrónico | Contraseña | Capacidades |
|---|---|---|---|
| **Administrador** | `admin@juegos.net` | `12345` | Acceso total, gestión de roles, papelera y reportes |
| **Vendedor** | `vendedor1@juegos.net` | `12345` | Consulta de catálogos, Dashboard y emisión de reportes PDF |
| **Cliente Demo** | `cristofer@gmail.com` | `123456` | Exploración de catálogo y filtros |
| **Registro Libre** | `/registro` | — | Creación de cuenta con formulario validado |

---

## 🗂️ Estructura del Repositorio

```plaintext
proyecto-juegos-lp2/
├── database/                    # Scripts SQL estructurados
│   └── base_sql_juegos.net.sql  # Creación de tablas, datos semilla y usuarios
├── docs/                        # Documentación académica
│   ├── PPT_Juegos.Net.pptx      # Presentación de exposición
│   └── PROYECTO LP2.pdf         # Informe del proyecto
├── src/                         # Código fuente
│   ├── main/
│   │   ├── java/com/juegos/
│   │   │   ├── config/          # Interceptores y configuración Web MVC
│   │   │   ├── controller/      # Controladores Spring MVC
│   │   │   ├── dto/             # Objetos de transferencia y validaciones
│   │   │   ├── model/           # Entidades JPA
│   │   │   ├── repository/      # Repositorios Spring Data JPA
│   │   │   └── service/         # Lógica de negocio y reportes Jasper
│   │   └── resources/
│   │       ├── reports/         # Plantillas JRXML de JasperReports
│   │       ├── static/          # Recursos estáticos (CSS, JS, Logo e imágenes)
│   │       └── templates/       # Vistas Thymeleaf (auth, dashboard, papelera, etc.)
│   └── test/                    # Pruebas unitarias de servicios y reportes
├── pom.xml                      # Dependencias Maven
└── README.md                    # Documentación del proyecto
```

---

## 🚀 Requisitos e Instalación

### Requisitos Previos:
- **Java JDK 17** o superior.
- **MySQL Server 8.0** activo en `localhost:3306`.
- **Git** y navegador web moderno.

### Pasos de Ejecución:

1. **Configurar la Base de Datos:**
   Ejecutar el script [`database/base_sql_juegos.net.sql`](database/base_sql_juegos.net.sql) en MySQL Workbench o terminal:
   ```sql
   source database/base_sql_juegos.net.sql;
   ```
2. **Ajustar credenciales de conexión (si aplica):**
   Verificar `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/db_juegos_net?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=mysql
   ```
3. **Compilar y Ejecutar:**
   En Windows PowerShell:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```
   En macOS/Linux:
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Abrir en el Navegador:**
   Ingresar a [http://localhost:8080/login](http://localhost:8080/login).

---

## 🛠️ Tecnologías Empleadas

- **Backend:** Java 17, Spring Boot 4.1.1 (Spring MVC, Spring Data JPA, Bean Validation).
- **Frontend:** Thymeleaf, Bootstrap 5.3, Bootstrap Icons.
- **Base de Datos:** MySQL 8.0 con driver oficial `mysql-connector-j`.
- **Reportes:** JasperReports 6.21.3.
- **Pruebas:** JUnit 5, AssertJ.

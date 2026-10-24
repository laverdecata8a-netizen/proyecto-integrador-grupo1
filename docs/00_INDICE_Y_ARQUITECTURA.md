# Tutorial: Sistema de Inventario desde Cero
## Guía Completa de Desarrollo con Spring Boot, Vaadin Flow, Hibernate ORM y PostgreSQL

Bienvenido a esta guía paso a paso. En este tutorial aprenderás a construir desde cero una aplicación web empresarial completa para un **Sistema Básico de Inventario**.

El proyecto utiliza un enfoque moderno, limpio y sin complicaciones:
* **Frontend**: **Vaadin Flow** (permite construir interfaces web interactivas y reactivas 100% en Java, sin necesidad de escribir HTML, JavaScript o CSS).
* **Backend**: **Spring Boot**, el framework estándar de la industria para desarrollo en Java.
* **ORM (Capa de Persistencia)**: **Hibernate ORM** (mediante Jakarta Persistence / `EntityManager`), interactuando directamente con la base de datos sin necesidad de crear interfaces de repositorios repetitivas.
* **Base de Datos**: **PostgreSQL**, un motor de base de datos relacional potente y robusto.

---

## 1. Arquitectura General del Sistema

A diferencia de las arquitecturas tradicionales que requieren decenas de clases y carpetas intermedias, este proyecto utiliza una **arquitectura simplificada y directa**:

```
+-------------------------------------------------------------+
|                     NAVEGADOR WEB (CLIENTE)                 |
|                   http://localhost:8080                     |
+-------------------------------------------------------------+
                              ▲
                              │ Comunicación Web (Vaadin Flow)
                              ▼
+-------------------------------------------------------------+
|                      CAPA DE VISTA (UI)                     |
|                        MainView.java                        |
|   - Pestañas (TabSheet)                                     |
|   - Formularios (FormLayout) y Grillas de Datos (Grid)      |
|   - Eventos de botones (Crear, Consultar, Actualizar, etc.) |
+-------------------------------------------------------------+
                              ▲
                              │ Inyección de dependencias (@Autowired)
                              ▼
+-------------------------------------------------------------+
|                  CAPA DE SERVICIO GENÉRICO                  |
|                       CrudService.java                      |
|   - EntityManager de Hibernate directo                      |
|   - Métodos CRUD universales: persist, find, merge, remove  |
+-------------------------------------------------------------+
                              ▲
                              │ Mapeo Objeto-Relacional (JPA)
                              ▼
+-------------------------------------------------------------+
|                   MODELO DE ENTIDADES (JPA)                 |
|   - Producto.java    -> Tabla "productos"                   |
|   - Categoria.java   -> Tabla "categorias"                  |
|   - Proveedor.java   -> Tabla "proveedores"                 |
|   - Usuario.java     -> Tabla "usuarios"                    |
+-------------------------------------------------------------+
                              ▲
                              │ Conector JDBC (Driver de PostgreSQL)
                              ▼
+-------------------------------------------------------------+
|                 BASE DE DATOS POSTGRESQL                    |
|                      inventario_db                          |
+-------------------------------------------------------------+
```

---

## 2. Requisitos Previos e Instalación de Herramientas

Para seguir este tutorial y ejecutar el proyecto en tu computadora, asegúrate de tener instaladas las siguientes herramientas:

1. **Java Development Kit (JDK)**:
   - Versión 21 o superior (este proyecto está configurado para Java 25 o 21 LTS).
   - Puedes descargarlo de [Adoptium Eclipse Temurin](https://adoptium.net/) o de Oracle.
   - Para verificar en tu terminal:
     ```bash
     java -version
     ```

2. **Motor de Base de Datos PostgreSQL**:
   - Descarga e instala PostgreSQL desde su sitio oficial [postgresql.org](https://www.postgresql.org/download/).
   - Durante la instalación, toma nota del puerto (por defecto `5432`) y de la contraseña del usuario `postgres`.

3. **Entorno de Desarrollo (IDE)**:
   - Puedes usar Visual Studio Code, IntelliJ IDEA, Eclipse o NetBeans.

4. **Apache Maven**:
   - El proyecto incluye los archivos ejecutables `mvnw` (Linux/Mac) y `mvnw.cmd` (Windows), por lo que **no es obligatorio** tener Maven instalado globalmente; el wrapper se encarga de todo.

---

## 3. Estructura de Directorios del Proyecto

Al finalizar el tutorial, la estructura de archivos de tu proyecto será exactamente la siguiente:

```text
proyecto-integrador-b1-2026-2/
│
├── pom.xml                                # Dependencias y configuración de Maven
├── mvnw / mvnw.cmd                        # Maven Wrapper para compilar sin instalar Maven
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── Application.java   # Clase principal para iniciar Spring Boot y Vaadin
│   │   │           ├── MainView.java      # Vista web con formularios y grillas CRUD
│   │   │           │
│   │   │           ├── model/             # Entidades del dominio mapeadas con JPA
│   │   │           │   ├── Categoria.java
│   │   │           │   ├── Producto.java
│   │   │           │   ├── Proveedor.java
│   │   │           │   └── Usuario.java
│   │   │           │
│   │   │           └── service/           # Capa de operaciones CRUD con Hibernate
│   │   │               └── CrudService.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties     # Configuración de base de datos y Hibernate
│   │       └── META-INF/resources/styles.css # Estilos opcionales
│
└── docs/                                  # Manuales y tutoriales paso a paso
    ├── 00_INDICE_Y_ARQUITECTURA.md
    ├── 01_CONFIGURACION_INICIAL_Y_POM.md
    ├── 02_CONEXION_POSTGRESQL_Y_PROPERTIES.md
    ├── 03_ENTIDADES_MODELO_JPA.md
    ├── 04_SERVICIO_CRUD_CON_HIBERNATE.md
    ├── 05_INTERFAZ_GRAFICA_MAINVIEW.md
    ├── 06_EJECUCION_Y_PRUEBAS.md
    └── 07_GUIA_ADAPTACION_NUEVAS_IDEAS_DE_NEGOCIO.md
```

---

## 4. Índice del Tutorial

Sigue los capítulos en este orden para comprender cada parte del sistema:

1. [Capítulo 1: Configuración Inicial y Archivo pom.xml](file:///c:/Users/Jhon/Desktop/Proyectos/proyecto-integrador-b1-2026-2/docs/01_CONFIGURACION_INICIAL_Y_POM.md)
   * Creación del esqueleto del proyecto y explicación de dependencias.
2. [Capítulo 2: Conexión con PostgreSQL y application.properties](file:///c:/Users/Jhon/Desktop/Proyectos/proyecto-integrador-b1-2026-2/docs/02_CONEXION_POSTGRESQL_Y_PROPERTIES.md)
   * Creación de la base de datos y configuración del ORM Hibernate.
3. [Capítulo 3: Entidades del Modelo JPA](file:///c:/Users/Jhon/Desktop/Proyectos/proyecto-integrador-b1-2026-2/docs/03_ENTIDADES_MODELO_JPA.md)
   * Creación y anotación de Producto, Categoría, Proveedor y Usuario.
4. [Capítulo 4: Servicio CRUD Directo con Hibernate](file:///c:/Users/Jhon/Desktop/Proyectos/proyecto-integrador-b1-2026-2/docs/04_SERVICIO_CRUD_CON_HIBERNATE.md)
   * Creación de `CrudService` universal con `EntityManager` sin repositorios.
5. [Capítulo 5: Interfaz de Usuario con Vaadin Flow](file:///c:/Users/Jhon/Desktop/Proyectos/proyecto-integrador-b1-2026-2/docs/05_INTERFAZ_GRAFICA_MAINVIEW.md)
   * Construcción de la vista interactiva `MainView.java`.
6. [Capítulo 6: Ejecución, Pruebas y Validación](file:///c:/Users/Jhon/Desktop/Proyectos/proyecto-integrador-b1-2026-2/docs/06_EJECUCION_Y_PRUEBAS.md)
   * Puesta en marcha, pruebas funcionales y resolución de incidencias.
7. [Capítulo 7: Guía de Adaptación a Nuevas Ideas de Negocio](file:///c:/Users/Jhon/Desktop/Proyectos/proyecto-integrador-b1-2026-2/docs/07_GUIA_ADAPTACION_NUEVAS_IDEAS_DE_NEGOCIO.md)
   * Tutorial para estudiantes sobre cómo convertir este código en cualquier proyecto integrador (veterinaria, gimnasio, hotel, etc.).


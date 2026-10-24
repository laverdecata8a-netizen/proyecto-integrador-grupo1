# Capítulo 2: Conexión con PostgreSQL y application.properties

En este capítulo aprenderás cómo crear la base de datos en PostgreSQL y cómo configurar el archivo `application.properties` para que Spring Boot y Hibernate se conecten automáticamente.

---

## 1. Crear la Base de Datos en PostgreSQL

Antes de iniciar la aplicación por primera vez, es necesario crear una base de datos vacía en PostgreSQL. Puedes hacerlo usando la herramienta que prefieras:

### Opción A: Usando la terminal (psql)
Abre tu consola o terminal y ejecuta:

```bash
# Iniciar sesión en PostgreSQL con el usuario administrador
psql -U postgres

# Dentro de la consola psql, crear la base de datos:
CREATE DATABASE inventario_db;

# Salir de psql
\q
```

### Opción B: Usando pgAdmin o DBeaver
1. Abre pgAdmin o DBeaver y conéctate a tu servidor PostgreSQL local (`localhost:5432`).
2. Haz clic derecho sobre **Databases** (Bases de datos).
3. Selecciona **Create** > **Database...**.
4. Escribe el nombre: `inventario_db`.
5. Haz clic en **Save** (Guardar).

> [!NOTE]
> No es necesario crear tablas manualmente. Hibernate las creará por nosotros de forma automática al iniciar el proyecto.

---

## 2. El archivo de configuración `application.properties`

Ubicado en: `src/main/resources/application.properties`.

Este archivo contiene los parámetros del servidor web, las credenciales de la base de datos y el comportamiento del motor ORM Hibernate.

A continuación, se muestra el contenido completo:

```properties
# ==========================================
# 1. Configuración del Servidor y Vaadin
# ==========================================
# Puerto HTTP en el que corre la aplicación (8080 por defecto)
server.port=${PORT:8080}

# Reduce mensajes repetitivos en los logs de WebSocket
logging.level.org.atmosphere=warn

# Abre automáticamente el navegador predeterminado al iniciar en desarrollo
vaadin.launch-browser=true

# Paquetes permitidos para escaneo rápido de Vaadin
vaadin.allowed-packages=com.vaadin,org.vaadin,com.flowingcode,com.example

# ==========================================
# 2. Conexión a la Base de Datos PostgreSQL
# ==========================================
# URL de conexión JDBC hacia PostgreSQL (base de datos inventario_db)
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/inventario_db}

# Usuario de la base de datos (por defecto 'postgres')
spring.datasource.username=${DB_USERNAME:postgres}

# Contraseña del usuario en PostgreSQL
spring.datasource.password=${DB_PASSWORD:postgres}

# Clase del driver JDBC oficial de PostgreSQL
spring.datasource.driver-class-name=org.postgresql.Driver

# ==========================================
# 3. Configuración del ORM Hibernate / JPA
# ==========================================
# Generación y actualización automática del esquema en la base de datos
spring.jpa.hibernate.ddl-auto=update

# Imprime las sentencias SQL ejecutadas en la consola para depuración
spring.jpa.show-sql=true

# Formatea el SQL impreso con saltos de línea para facilitar su lectura
spring.jpa.properties.hibernate.format_sql=true

# Dialecto específico para aprovechar las funciones nativas de PostgreSQL
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

---

## 3. Explicación Detallada de cada Parámetro

### Variables con Valores por Defecto (`${VARIABLE:valor_defecto}`)
En la línea:
```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/inventario_db}
```
Se utiliza la sintaxis `${DB_URL:...}`. Esto significa:
* Si existe una variable de entorno llamada `DB_URL` (por ejemplo, en un contenedor Docker o en un servidor en la nube), se usará ese valor.
* Si no existe, se usará el valor por defecto: `jdbc:postgresql://localhost:5432/inventario_db`.
* Lo mismo aplica para el usuario (`${DB_USERNAME:postgres}`) y la contraseña (`${DB_PASSWORD:postgres}`).

### El parámetro `spring.jpa.hibernate.ddl-auto=update` (Magia de Hibernate)
Este es uno de los parámetros más importantes del proyecto:
* **`update`**: Al arrancar la aplicación, Hibernate lee todas las clases Java que tengan la anotación `@Entity` (Producto, Categoria, Proveedor, Usuario) y compara su estructura con las tablas existentes en PostgreSQL.
  * Si la tabla no existe en la base de datos, Hibernate ejecuta un `CREATE TABLE` automáticamente.
  * Si agregas un nuevo campo a una clase Java (por ejemplo, `codigo`), Hibernate añade la columna a la tabla sin borrar los datos existentes.

> [!WARNING]
> En entornos de producción reales se suelen usar herramientas de migración como Flyway o Liquibase. Sin embargo, para desarrollo y proyectos académicos, `ddl-auto=update` es la forma más rápida y cómoda de trabajar.

### `spring.jpa.show-sql=true` y `format_sql=true`
Cada vez que tu aplicación guarde un producto, consulte una categoría o elimine un usuario, Hibernate imprimirá en la consola de tu terminal la consulta SQL exacta que envió a PostgreSQL:

```sql
Hibernate: 
    insert 
    into
        productos
        (categoria, codigo, nombre, precio, stock) 
    values
        (?, ?, ?, ?, ?)
```
Esto te permite comprobar con total transparencia que las operaciones CRUD se están ejecutando correctamente en la base de datos.

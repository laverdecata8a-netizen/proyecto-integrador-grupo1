# Capítulo 1: Configuración Inicial y Archivo pom.xml

En este capítulo aprenderás cómo inicializar el proyecto base y cómo configurar todas las librerías necesarias utilizando Apache Maven.

---

## 1. ¿Cómo crear el proyecto desde cero?

Existen dos formas sencillas de generar la estructura base:

### Método A: Usando Vaadin Start (Recomendado para proyectos Vaadin)
1. Ingresa en tu navegador a [https://start.vaadin.com](https://start.vaadin.com).
2. Selecciona **Flow (Java only)** como framework web.
3. Elige el tema visual por defecto (**Aura** o **Lumo**).
4. Descarga el archivo `.zip`, descomprímelo en tu carpeta de proyectos y ábrelo en tu IDE.

### Método B: Usando Spring Initializr
1. Ingresa a [https://start.spring.io](https://start.spring.io).
2. Configura:
   - **Project**: Maven
   - **Language**: Java
   - **Spring Boot**: Versión 3.x o 4.x
   - **Group**: `com.example`
   - **Artifact**: `app`
   - **Dependencies**: Vaadin, Spring Data JPA, PostgreSQL Driver.
3. Genera y descarga el archivo comprimido.

---

## 2. El archivo de configuración `pom.xml`

El archivo `pom.xml` (Project Object Model) es el corazón de cualquier proyecto Maven. En él se especifican las versiones de Java, las dependencias externas (librerías) y los plugins de compilación.

A continuación, se muestra el archivo `pom.xml` completo del proyecto:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>app</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    
    <properties>
        <java.version>25</java.version>
        <vaadin.version>25.3.0</vaadin.version>
    </properties>
    
    <!-- Heredamos de Spring Boot Starter Parent para control de versiones -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>4.1.1</version>
        <relativePath/>
    </parent>
    
    <!-- Gestión centralizada de versiones de Vaadin mediante su BOM -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.vaadin</groupId>
                <artifactId>vaadin-bom</artifactId>
                <version>${vaadin.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <dependencies>
        <!-- Componentes visuales de Vaadin -->
        <dependency>
            <groupId>com.vaadin</groupId>
            <artifactId>vaadin</artifactId>
        </dependency>

        <!-- Herramientas de desarrollo en caliente (hot reload) de Vaadin -->
        <dependency>
            <groupId>com.vaadin</groupId>
            <artifactId>vaadin-dev</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Integración oficial entre Vaadin y Spring Boot -->
        <dependency>
            <groupId>com.vaadin</groupId>
            <artifactId>vaadin-spring-boot-starter</artifactId>
        </dependency>

        <!-- ORM Hibernate y persistencia JPA mediante Spring Boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- Conector JDBC oficial para PostgreSQL -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Dependencias para pruebas unitarias -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.vaadin</groupId>
            <artifactId>browserless-test-spring</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <defaultGoal>spring-boot:run</defaultGoal>
        <plugins>
            <!-- Plugin para empaquetar y ejecutar aplicaciones Spring Boot -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>

            <!-- Plugin de Vaadin para compilar recursos frontend en producción -->
            <plugin>
                <groupId>com.vaadin</groupId>
                <artifactId>vaadin-maven-plugin</artifactId>
                <version>${vaadin.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>build-frontend</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 3. Explicación Detallada de cada Dependencia

1. **`spring-boot-starter-parent`**:
   Define la configuración base de Spring Boot y gestiona automáticamente las versiones compatibles de la mayoría de librerías de Java para evitar conflictos de versiones.

2. **`vaadin-bom` y `vaadin`**:
   - `vaadin-bom` (Bill of Materials) fija la versión exacta de todos los módulos de Vaadin.
   - `vaadin` incluye toda la suite de componentes web (botones, formularios, grillas, diálogos, notificaciones, pestañas, etc.).

3. **`vaadin-spring-boot-starter`**:
   Permite que Spring Boot reconozca automáticamente las vistas de Vaadin (`@Route`), habilita la inyección de dependencias (`@Autowired`) dentro de los componentes visuales y arranca el servidor embebido Tomcat.

4. **`spring-boot-starter-data-jpa`**:
   Descarga e integra automáticamente:
   - **Hibernate ORM**: el motor que traduce los objetos Java a consultas SQL.
   - **Jakarta Persistence API (JPA)**: la interfaz estándar para interactuar con bases de datos relacionales en Java (`EntityManager`).
   - **HikariCP**: el gestor de grupo de conexiones (connection pool) de alto rendimiento.

5. **`org.postgresql:postgresql`**:
   Es el controlador (JDBC Driver) que permite a Java hablar el protocolo de comunicación nativo de PostgreSQL. Tiene `<scope>runtime</scope>` porque solo se necesita cuando el programa se ejecuta y conecta a la base de datos.

---

## 4. La Clase Principal: `Application.java`

Ubicada en: `src/main/java/com/example/Application.java`.

Es el punto de entrada de la aplicación (`public static void main`). Spring Boot la utiliza para inicializar el contenedor de servlets, escanear componentes y levantar el servidor web:

```java
package com.example;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.aura.Aura;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@StyleSheet(Aura.STYLESHEET)     // Aplica el tema visual Aura de Vaadin
@StyleSheet("styles.css")         // Carga una hoja de estilos CSS personalizada
@Push                             // Habilita comunicación bidireccional en tiempo real (WebSockets)
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### Elementos clave de esta clase:
* **`@SpringBootApplication`**: Indica a Spring que esta clase es el arranque, activando la autoconfiguración, el escaneo de componentes y la configuración de propiedades.
* **`implements AppShellConfigurator`**: Permite a Vaadin configurar el caparazón HTML de la página web (meta tags, viewport para móviles, título, etc.).
* **`@Push`**: Permite actualizaciones instantáneas en el navegador impulsadas desde el servidor mediante WebSockets.

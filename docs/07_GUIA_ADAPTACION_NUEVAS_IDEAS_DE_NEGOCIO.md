# Capítulo 7: Cómo Convertir este Proyecto en Cualquier Otra Idea de Negocio

Este capítulo es una **guía práctica para estudiantes**. Aquí aprenderás cómo tomar este proyecto base de inventario y transformarlo en el **Proyecto Integrador** que tú o tu equipo hayan elegido (por ejemplo: una clínica veterinaria, un gimnasio, un hotel, un taller mecánico, una biblioteca, un restaurante o cualquier otra idea).

---

## 1. La Gran Ventaja de esta Arquitectura

Gracias a cómo construimos este proyecto, **más del 70% del código no necesita modificarse**:

* **Maven (`pom.xml`)**: Ya tiene todas las librerías necesarias (Spring Boot, Vaadin, Hibernate, PostgreSQL). **No tienes que cambiar nada**.
* **La conexión a base de datos (`application.properties`)**: Solo cambias el nombre de la base de datos si lo deseas.
* **El servicio CRUD (`CrudService.java`)**: Al ser un servicio genérico (`<T>`), **¡NO TIENES QUE TOCARLO!** Funciona automáticamente para cualquier entidad nueva que crees.
* **El arranque (`Application.java`)**: Queda exactamente igual.

> [!TIP]
> **Tu único trabajo como estudiante consiste en:**
> 1. Pensar las entidades de tu negocio (3 o 4 clases en `com.example.model`).
> 2. Diseñar los formularios y grillas en `MainView.java` para mostrar tus datos.

---

## 2. Los 5 Pasos para Transformar el Proyecto

```
+-----------------------------------------------------------------+
| PASO 1: Define las 3 o 4 entidades de tu idea de negocio       |
+-----------------------------------------------------------------+
                                │
                                ▼
+-----------------------------------------------------------------+
| PASO 2: Crea las clases en el paquete com.example.model         |
|         (Anotadas con @Entity, @Table, @Id, @GeneratedValue)    |
+-----------------------------------------------------------------+
                                │
                                ▼
+-----------------------------------------------------------------+
| PASO 3: (Opcional) Cambia el nombre de la BD en properties      |
+-----------------------------------------------------------------+
                                │
                                ▼
+-----------------------------------------------------------------+
| PASO 4: Adapta las pestañas, formularios y grillas en MainView  |
+-----------------------------------------------------------------+
                                │
                                ▼
+-----------------------------------------------------------------+
| PASO 5: Inicia el proyecto y comprueba la creación automática   |
+-----------------------------------------------------------------+
```

---

## 3. Ejemplo Completo Paso a Paso: Transformación a "Clínica Veterinaria"

Para que veas exactamente qué debes hacer, vamos a transformar el sistema de inventario en un **Sistema de Gestión de Clínica Veterinaria**.

### Paso 1: Definir las Entidades del Negocio
En una veterinaria necesitamos gestionar:
1. **Mascota**: El paciente animal (nombre, especie, raza, edad, nombre del dueño).
2. **Veterinario**: El médico profesional (nombre, especialidad, teléfono, correo).
3. **CitaMedica**: La consulta médica (fecha, motivo, diagnóstico, costo).
4. **Usuario**: El personal que inicia sesión (administrador, recepcionista).

---

### Paso 2: Crear las Clases en `com.example.model`

Por ejemplo, eliminamos `Producto.java` y creamos en su lugar `Mascota.java`:

Archivo: `src/main/java/com/example/model/Mascota.java`

```java
package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mascotas") // Hibernate creará la tabla 'mascotas' en PostgreSQL
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String especie; // 'Perro', 'Gato', etc.
    private String raza;
    private Integer edad;
    private String nombreDueno;
    private String telefonoContacto;

    // 1. Constructor vacío (Obligatorio para Hibernate)
    public Mascota() {
    }

    // 2. Constructor sin ID (Para crear nuevas mascotas)
    public Mascota(String nombre, String especie, String raza, Integer edad, String nombreDueno, String telefonoContacto) {
        this(null, nombre, especie, raza, edad, nombreDueno, telefonoContacto);
    }

    // 3. Constructor completo
    public Mascota(Long id, String nombre, String especie, String raza, Integer edad, String nombreDueno, String telefonoContacto) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.nombreDueno = nombreDueno;
        this.telefonoContacto = telefonoContacto;
    }

    // 4. Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getNombreDueno() { return nombreDueno; }
    public void setNombreDueno(String nombreDueno) { this.nombreDueno = nombreDueno; }

    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }
}
```

---

### Paso 3: Ajustar el Nombre de la Base de Datos en `application.properties`

Abre `src/main/resources/application.properties` y cambia el nombre de la base de datos:

```properties
# Cambiamos 'inventario_db' por 'veterinaria_db'
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/veterinaria_db}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
```

Luego en tu PostgreSQL creas esa base de datos:
```sql
CREATE DATABASE veterinaria_db;
```

---

### Paso 4: Adaptar `MainView.java` a la Nueva Entidad

En `MainView.java`:
1. Cambias el título de la pestaña a `"Mascotas"`.
2. Creas el método `crearSeccionMascotas()`. Observa lo sencillo que resulta:

```java
// ==========================================
// SECCIÓN MASCOTAS (CLÍNICA VETERINARIA)
// ==========================================
private Component crearSeccionMascotas() {
    VerticalLayout layout = new VerticalLayout();
    
    // 1. Campos del formulario
    TextField idField = new TextField("ID");
    TextField nombreField = new TextField("Nombre de la Mascota");
    TextField especieField = new TextField("Especie (Perro, Gato...)");
    TextField razaField = new TextField("Raza");
    TextField edadField = new TextField("Edad (Años)");
    TextField duenoField = new TextField("Nombre del Dueño");
    TextField telefonoField = new TextField("Teléfono de Contacto");

    FormLayout form = new FormLayout(idField, nombreField, especieField, razaField, edadField, duenoField, telefonoField);

    // 2. Grilla con las columnas de Mascota
    Grid<Mascota> grid = new Grid<>(Mascota.class, false);
    grid.addColumn(Mascota::getId).setHeader("ID").setAutoWidth(true);
    grid.addColumn(Mascota::getNombre).setHeader("Nombre").setAutoWidth(true);
    grid.addColumn(Mascota::getEspecie).setHeader("Especie").setAutoWidth(true);
    grid.addColumn(Mascota::getRaza).setHeader("Raza").setAutoWidth(true);
    grid.addColumn(Mascota::getEdad).setHeader("Edad").setAutoWidth(true);
    grid.addColumn(Mascota::getNombreDueno).setHeader("Dueño").setAutoWidth(true);
    grid.addColumn(Mascota::getTelefonoContacto).setHeader("Teléfono").setAutoWidth(true);

    // 3. Función para refrescar datos desde PostgreSQL usando CrudService
    Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Mascota.class));
    refrescar.run();

    // 4. Al seleccionar una mascota en la grilla, cargarla en el formulario
    grid.asSingleSelect().addValueChangeListener(e -> {
        Mascota m = e.getValue();
        if (m != null) {
            idField.setValue(m.getId() != null ? m.getId().toString() : "");
            nombreField.setValue(m.getNombre() != null ? m.getNombre() : "");
            especieField.setValue(m.getEspecie() != null ? m.getEspecie() : "");
            razaField.setValue(m.getRaza() != null ? m.getRaza() : "");
            edadField.setValue(m.getEdad() != null ? m.getEdad().toString() : "");
            duenoField.setValue(m.getNombreDueno() != null ? m.getNombreDueno() : "");
            telefonoField.setValue(m.getTelefonoContacto() != null ? m.getTelefonoContacto() : "");
        }
    });

    // 5. Botón CREAR
    Button btnCrear = new Button("Crear", e -> {
        Mascota m = new Mascota(
            nombreField.getValue(),
            especieField.getValue(),
            razaField.getValue(),
            parseInt(edadField.getValue()),
            duenoField.getValue(),
            telefonoField.getValue()
        );
        crudService.guardar(m); // ¡Se guarda en PostgreSQL con Hibernate!
        refrescar.run();
        idField.setValue(m.getId() != null ? m.getId().toString() : "");
        Notification.show("Mascota registrada con ID: " + m.getId());
    });
    btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    // 6. Botón CONSULTAR por ID
    Button btnConsultar = new Button("Consultar", e -> {
        Mascota m = crudService.buscarPorId(Mascota.class, parseId(idField));
        if (m != null) {
            grid.asSingleSelect().setValue(m);
            Notification.show("Mascota encontrada: " + m.getNombre());
        } else {
            Notification.show("No encontrada");
        }
    });

    // 7. Botón ACTUALIZAR
    Button btnActualizar = new Button("Actualizar", e -> {
        Mascota m = crudService.buscarPorId(Mascota.class, parseId(idField));
        if (m != null) {
            m.setNombre(nombreField.getValue());
            m.setEspecie(especieField.getValue());
            m.setRaza(razaField.getValue());
            m.setEdad(parseInt(edadField.getValue()));
            m.setNombreDueno(duenoField.getValue());
            m.setTelefonoContacto(telefonoField.getValue());
            crudService.actualizar(m);
            refrescar.run();
            Notification.show("Mascota actualizada correctamente");
        } else {
            Notification.show("ID no encontrado");
        }
    });

    // 8. Botón ELIMINAR
    Button btnEliminar = new Button("Eliminar", e -> {
        crudService.eliminar(Mascota.class, parseId(idField));
        refrescar.run();
        limpiar(idField, nombreField, especieField, razaField, edadField, duenoField, telefonoField);
        Notification.show("Mascota eliminada");
    });
    btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

    // 9. Botón LIMPIAR
    Button btnLimpiar = new Button("Limpiar", e -> {
        limpiar(idField, nombreField, especieField, razaField, edadField, duenoField, telefonoField);
        grid.asSingleSelect().clear();
    });

    layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
    return layout;
}
```

¡Y listo! Al iniciar la aplicación, Hibernate creará la tabla `mascotas` en PostgreSQL y la interfaz web te permitirá gestionar mascotas en tiempo real.

---

## 4. Catálogo de Ideas de Proyecto Integrador Listas para Usar

A continuación tienes 5 ideas de negocio habituales en proyectos integradores, con las entidades y atributos recomendados para que tú y tu equipo solo tengan que aplicarlos:

### Idea 1: Sistema para Gimnasio y Fitness Center
* **`Miembro`**: `id`, `documento`, `nombreCompleto`, `telefono`, `email`, `estado` (Activo/Inactivo).
* **`PlanMembresia`**: `id`, `nombrePlan` (Mensual, Trimestral, Anual), `precio`, `duracionDias`, `beneficios`.
* **`Entrenador`**: `id`, `nombre`, `especialidad` (Pesas, Cardio, Yoga), `telefono`, `horarioTurno`.
* **`Usuario`**: `id`, `nombre`, `email`, `rol` (Recepcionista, Administrador), `estado`.

### Idea 2: Sistema para Taller Mecánico / Automotriz
* **`Vehiculo`**: `id`, `placa`, `marca`, `modelo`, `anio`, `color`, `nombrePropietario`.
* **`Cliente`**: `id`, `cedula`, `nombre`, `telefono`, `email`, `direccion`.
* **`Mecanico`**: `id`, `nombre`, `especialidad` (Frenos, Motor, Eléctrico), `telefono`.
* **`OrdenServicio`**: `id`, `fechaIngreso`, `fallaReportada`, `diagnostico`, `costoTotal`, `estado` (Pendiente, En Reparación, Entregado).

### Idea 3: Sistema de Gestión Hotelera / Hospedaje
* **`Habitacion`**: `id`, `numeroHabitacion`, `tipo` (Sencilla, Doble, Suite), `precioNoche`, `estado` (Disponible, Ocupada, Mantenimiento).
* **`Huesped`**: `id`, `documentoIdentidad`, `nombreCompleto`, `nacionalidad`, `telefono`, `email`.
* **`Reserva`**: `id`, `fechaCheckIn`, `fechaCheckOut`, `numeroHuespedes`, `totalPagar`, `estadoPago`.
* **`ServicioAdicional`**: `id`, `nombreServicio` (Desayuno buffet, Lavandería, Spa), `precio`.

### Idea 4: Sistema de Restaurante y Menú Digital
* **`Plato`**: `id`, `codigoMenu`, `nombre`, `categoria` (Entrada, Fuerte, Postre, Bebida), `precio`, `disponible` (Sí/No).
* **`Mesa`**: `id`, `numeroMesa`, `capacidadPersonas`, `ubicacion` (Terraza, Salón principal), `estado` (Libre, Ocupada, Reservada).
* **`Mesero`**: `id`, `nombre`, `telefono`, `turno` (Mañana, Tarde, Noche).
* **`Pedido`**: `id`, `numeroMesa`, `descripcionPlatos`, `totalVenta`, `horaPedido`, `estado` (En cocina, Servido, Pagado).

### Idea 5: Sistema de Biblioteca / Préstamos de Libros
* **`Libro`**: `id`, `isbn`, `titulo`, `autor`, `categoria` (Ciencia, Novela, Historia), `ejemplaresDisponibles`.
* **`Lector`**: `id`, `codigoEstudiante`, `nombreCompleto`, `carrera`, `telefono`, `email`.
* **`Prestamo`**: `id`, `fechaPrestamo`, `fechaDevolucionLimite`, `estadoPrestamo` (Prestado, Devuelto, Vencido).
* **`Bibliotecario`**: `id`, `nombre`, `correo`, `rol`.

---

## 5. Lista de Verificación (Checklist) para el Estudiante

Antes de entregar o presentar tu proyecto integrador, asegúrate de haber completado:

- [ ] Elegir la idea de negocio y definir los nombres de las 3 o 4 entidades.
- [ ] Crear las clases Java dentro de `com.example.model` con las anotaciones JPA (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`).
- [ ] Asegurarte de que cada clase tenga un constructor vacío y sus getters/setters.
- [ ] Verificar que `CrudService.java` esté en `com.example.service` (no requiere cambios).
- [ ] Adaptar las pestañas y formularios de `MainView.java` para que muestren los campos de tus nuevas entidades.
- [ ] Crear la base de datos en PostgreSQL con el nombre que colocaste en `application.properties`.
- [ ] Ejecutar el proyecto con `./mvnw.cmd spring-boot:run` y comprobar que Hibernate crea las tablas automáticamente.
- [ ] Probar desde el navegador (`http://localhost:8080`) que funcionen las 4 operaciones (Crear, Consultar, Actualizar y Eliminar) para cada pestaña.
- [ ] Verificar con `psql` o pgAdmin que los datos se guardan en las tablas de PostgreSQL.

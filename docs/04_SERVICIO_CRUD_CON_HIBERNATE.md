# Capítulo 4: Servicio CRUD Directo con Hibernate

En este capítulo aprenderás cómo interactuar directamente con el ORM Hibernate utilizando el **`EntityManager`** de JPA sin necesidad de crear interfaces de repositorios repetitivas.

---

## 1. ¿Por qué usar directamente `EntityManager` sin repositorios?

Tradicionalmente, en Spring Boot se acostumbra crear una interfaz `Repository` por cada entidad:
* `ProductoRepository`
* `CategoriaRepository`
* `ProveedorRepository`
* `UsuarioRepository`

Si bien esto es útil en proyectos con consultas muy especializadas, en la gran mayoría de sistemas básicos de inventario las operaciones que se necesitan son exactamente las mismas: **Guardar, Buscar por ID, Listar todos, Actualizar y Eliminar**.

Al usar directamente el **`EntityManager`** con métodos genéricos (`<T>`), podemos crear **una sola clase** que resuelva el CRUD para todas las entidades actuales y cualquier entidad futura, reduciendo el código en más de un 70%.

---

## 2. Código Fuente Completo de `CrudService.java`

Ubicado en: `src/main/java/com/example/service/CrudService.java`.

```java
package com.example.service;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio genérico que interactúa directamente con el ORM Hibernate (vía JPA EntityManager)
 * sin necesidad de crear interfaces de Repositorios para cada entidad.
 */
@Service
@Transactional
public class CrudService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * CREATE / Guardar una nueva entidad en la base de datos
     */
    public <T> T guardar(T entidad) {
        entityManager.persist(entidad);
        return entidad;
    }

    /**
     * READ / Consultar una entidad por su ID
     */
    @Transactional(readOnly = true)
    public <T> T buscarPorId(Class<T> clase, Long id) {
        if (id == null) {
            return null;
        }
        return entityManager.find(clase, id);
    }

    /**
     * READ / Obtener todos los registros de una entidad
     */
    @Transactional(readOnly = true)
    public <T> List<T> listarTodos(Class<T> clase) {
        String hql = "SELECT e FROM " + clase.getSimpleName() + " e";
        return entityManager.createQuery(hql, clase).getResultList();
    }

    /**
     * UPDATE / Actualizar una entidad existente
     */
    public <T> T actualizar(T entidad) {
        return entityManager.merge(entidad);
    }

    /**
     * DELETE / Eliminar una entidad por su ID
     */
    public <T> boolean eliminar(Class<T> clase, Long id) {
        if (id == null) {
            return false;
        }
        T entidad = entityManager.find(clase, id);
        if (entidad != null) {
            entityManager.remove(entidad);
            return true;
        }
        return false;
    }
}
```

---

## 3. Explicación Detallada del Código

### Las Anotaciones de Clase
1. **`@Service`**:
   Le indica a Spring que esta clase es un componente de servicio empresarial. Spring la registrará en su contenedor de Beans y la inyectará automáticamente donde sea solicitada (por ejemplo, en `MainView`).
2. **`@Transactional`**:
   Indica que cada método de la clase debe ejecutarse dentro de una **transacción de base de datos**. Si la operación tiene éxito, se ejecuta un `COMMIT`; si ocurre un error inesperado, se realiza un `ROLLBACK` automático para no corromper la información.

### El objeto `EntityManager` y `@PersistenceContext`
```java
@PersistenceContext
private EntityManager entityManager;
```
* **`EntityManager`**: Es la interfaz central de JPA / Hibernate. Proporciona los métodos nativos para interactuar con el contexto de persistencia.
* **`@PersistenceContext`**: Permite a Spring inyectar el EntityManager asociado a la conexión activa de PostgreSQL.

---

## 4. Desglose de cada Operación CRUD

### A) Operación CREATE (`guardar`)
```java
public <T> T guardar(T entidad) {
    entityManager.persist(entidad);
    return entidad;
}
```
* **`entityManager.persist(entidad)`**: Pasa el objeto Java de un estado "nuevo" (transient) a un estado "gestionado" (managed). Hibernate genera la instrucción SQL `INSERT INTO tabla (...) VALUES (...)`.
* Al guardarse, PostgreSQL genera el ID autoincremental y Hibernate actualiza automáticamente el campo `id` del objeto Java.

### B) Operación READ por ID (`buscarPorId`)
```java
@Transactional(readOnly = true)
public <T> T buscarPorId(Class<T> clase, Long id) {
    return entityManager.find(clase, id);
}
```
* **`entityManager.find(clase, id)`**: Ejecuta un `SELECT * FROM tabla WHERE id = ?`. Si el registro existe, Hibernate instancia la clase y la retorna. Si no existe, devuelve `null`.
* **`readOnly = true`**: Es una optimización que avisa a Hibernate que solo vamos a leer datos, evitando trabajo innecesario de verificación de cambios.

### C) Operación READ de Todos los Registros (`listarTodos`)
```java
@Transactional(readOnly = true)
public <T> List<T> listarTodos(Class<T> clase) {
    String hql = "SELECT e FROM " + clase.getSimpleName() + " e";
    return entityManager.createQuery(hql, clase).getResultList();
}
```
* Utiliza **HQL (Hibernate Query Language)** o JPQL.
* `clase.getSimpleName()` obtiene el nombre de la clase (por ejemplo, `Producto`, `Categoria`, etc.).
* Devuelve una lista tipada `List<T>` con todos los registros de la tabla.

### D) Operación UPDATE (`actualizar`)
```java
public <T> T actualizar(T entidad) {
    return entityManager.merge(entidad);
}
```
* **`entityManager.merge(entidad)`**: Toma los valores modificados del objeto Java y genera un `UPDATE tabla SET columna1 = ?, ... WHERE id = ?`.

### E) Operación DELETE (`eliminar`)
```java
public <T> boolean eliminar(Class<T> clase, Long id) {
    T entidad = entityManager.find(clase, id);
    if (entidad != null) {
        entityManager.remove(entidad);
        return true;
    }
    return false;
}
```
* Primero busca la entidad con `find()`. Si la encuentra, ejecuta **`entityManager.remove(entidad)`**, lo cual genera un `DELETE FROM tabla WHERE id = ?` en PostgreSQL y retorna `true`. Si no existe, retorna `false`.

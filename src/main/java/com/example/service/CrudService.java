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
     * Crear / Guardar una nueva entidad en la base de datos
     */
    public <T> T guardar(T entidad) {
        entityManager.persist(entidad);
        return entidad;
    }

    /**
     * Consultar una entidad por su ID
     */
    @Transactional(readOnly = true)
    public <T> T buscarPorId(Class<T> clase, Long id) {
        return entityManager.find(clase, id);
    }

    /**
     * Obtener todos los registros de una entidad
     */
    @Transactional(readOnly = true)
    public <T> List<T> listarTodos(Class<T> clase) {
        String hql = "SELECT e FROM " + clase.getSimpleName() + " e";
        return entityManager.createQuery(hql, clase).getResultList();
    }

    /**
     * Actualizar una entidad existente
     */
    public <T> T actualizar(T entidad) {
        return entityManager.merge(entidad);
    }

    /**
     * Eliminar una entidad por su ID
     */
    public <T> boolean eliminar(Class<T> clase, Long id) {
        T entidad = entityManager.find(clase, id);
        if (entidad != null) {
            entityManager.remove(entidad);
            return true;
        }
        return false;
    }
}

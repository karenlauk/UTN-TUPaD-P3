package com.tp.jpa.repositories;

import com.tp.jpa.model.Base;
import jakarta.persistence.EntityManager;
import java.util.List;

public class BaseRepository<T extends Base> {

    protected EntityManager em;
    private Class<T> entityClass;

    public BaseRepository(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    // CUMPLE: Guardar / Actualizar unificado
    public void guardar(T entidad) {
        em.getTransaction().begin();
        if (entidad.getId() == null) {
            em.persist(entidad); // Si no tiene ID, es un ALTA
        } else {
            em.merge(entidad);   // Si tiene ID, es una MODIFICACIÓN
        }
        em.getTransaction().commit();
    }

    public T buscarPorId(Long id) {
        return em.find(entityClass, id);
    }

    // CUMPLE: Listar solo los activos (eliminado = false)
    public List<T> listarActivos() {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.eliminado = false";
        return em.createQuery(jpql, entityClass).getResultList();
    }

    // CUMPLE: Baja Lógica (HU-04 y HU-08). NUNCA usamos em.remove()
    public void eliminarLogico(Long id) {
        T entidad = buscarPorId(id);
        if (entidad != null && !entidad.isEliminado()) {
            em.getTransaction().begin();
            entidad.setEliminado(true); // Se marca como borrado
            em.merge(entidad);
            em.getTransaction().commit();
        }
    }
}
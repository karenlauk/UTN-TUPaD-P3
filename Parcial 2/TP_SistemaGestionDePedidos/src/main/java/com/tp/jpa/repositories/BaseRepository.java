package com.tp.jpa.repositories;

import com.tp.jpa.util.JPAUtil;
import com.tp.jpa.model.Base;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class BaseRepository<T extends Base> {
    private final Class<T> claseEntidad;

    public BaseRepository(Class<T> claseEntidad) {
        this.claseEntidad = claseEntidad;
    }

    // 1. Guardar con manejo de transacciones, rollback explícito y cierre
    public T guardar(T entidad) {
        EntityManager em = JPAUtil.getEntityManager(); // Abre su propio EM
        try {
            em.getTransaction().begin();
            if (entidad.getId() == null) {
                em.persist(entidad);
            } else {
                entidad = em.merge(entidad);
            }
            em.getTransaction().commit();
            return entidad;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback explícito (Exigido)
            }
            throw e;
        } finally {
            em.close(); // Cierre explícito en bloque finally (Exigido)
        }
    }

    // 2. Buscar por ID retornando Optional<T> y cerrando el EM
    public Optional<T> buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager(); // Abre su propio EM
        try {
            T entidad = em.find(claseEntidad, id);
            return Optional.ofNullable(entidad); // Retorna un Optional (Exigido)
        } finally {
            em.close(); // Cierre explícito en bloque finally (Exigido)
        }
    }

    // 3. Eliminar Lógico retornando boolean, con rollback y cierre
    public boolean eliminarLogico(Long id) {
        EntityManager em = JPAUtil.getEntityManager(); // Abre su propio EM
        try {
            em.getTransaction().begin();
            T entidad = em.find(claseEntidad, id);

            // Verificamos que exista y no esté ya dado de baja
            if (entidad != null && !entidad.isEliminado()) {
                entidad.setEliminado(true);
                em.merge(entidad);
                em.getTransaction().commit();
                return true; // Retorna boolean indicando éxito (Exigido)
            }

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false; // Retorna false si no existía o ya estaba eliminado
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close(); // Cierre explícito en bloque finally
        }
    }

    // Metodo para listar activos
    public List<T> listarActivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT e FROM " + claseEntidad.getSimpleName() + " e WHERE e.eliminado = false";
            return em.createQuery(jpql, claseEntidad).getResultList();
        } finally {
            em.close();
        }
    }
}
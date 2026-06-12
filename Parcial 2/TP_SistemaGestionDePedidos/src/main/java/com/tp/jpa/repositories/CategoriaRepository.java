package com.tp.jpa.repositories;

import com.tp.jpa.model.Categoria;
import jakarta.persistence.EntityManager;

public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository(EntityManager em) {
        super(em, Categoria.class);
    }

    // CUMPLE HU-01: Validar que el nombre de la categoría no se repita
    public boolean existeNombre(String nombre) {
        String jpql = "SELECT COUNT(c) FROM Categoria c WHERE c.nombre = :nombre AND c.eliminado = false";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("nombre", nombre)
                .getSingleResult();
        return count > 0;
    }
}
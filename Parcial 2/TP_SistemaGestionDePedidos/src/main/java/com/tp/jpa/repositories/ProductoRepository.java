package com.tp.jpa.repositories;

import com.tp.jpa.model.Producto;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository(EntityManager em) {
        super(em, Producto.class);
    }

    // CUMPLE HU-09: Consulta JPQL con parámetros nombrados sin casteos manuales
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        String jpql = "SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId AND p.eliminado = false";
        return em.createQuery(jpql, Producto.class)
                .setParameter("categoriaId", categoriaId)
                .getResultList();
    }
}
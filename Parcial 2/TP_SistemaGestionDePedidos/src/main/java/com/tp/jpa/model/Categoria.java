package com.tp.jpa.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorias")
public class Categoria extends Base {

    private String nombre;
    private String descripcion;

    // mappedBy indica que el dueño de la relación es el atributo "categoria" en la clase Producto
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Producto> productos = new HashSet<>();

    public Categoria() {}

    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Set<Producto> getProductos() { return productos; }
    public void setProductos(Set<Producto> productos) { this.productos = productos; }
}
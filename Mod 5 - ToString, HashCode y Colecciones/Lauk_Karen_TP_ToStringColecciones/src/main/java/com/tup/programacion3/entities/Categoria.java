package com.tup.programacion3.entities;

import java.util.HashSet;
import java.util.Set;

public class Categoria {
    private Long id;
    private boolean eliminado;
    private String nombre;
    private Set<Producto> productos;

    public Categoria() {
        this.productos = new HashSet<>();
    }

    public Categoria(Long id, boolean eliminado, String nombre) {
        this.id = id;
        this.eliminado = eliminado;
        this.nombre = nombre;
        this.productos = new HashSet<>();
    }

    public void addProducto(Producto producto) {
        if (producto != null) {
            this.productos.add(producto);
        }
    }

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Set<Producto> getProductos() { return productos; }
    // TO STRING
    public String toString() {
        return "Categoria{id=" + id + ", nombre='" + nombre + "', productos=" + productos.size() + "}";
    }
}

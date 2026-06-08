package com.tup.programacion3.entities;
import java.util.Objects;

// INDICAMOS QUE HEREDA DE BASE
public class Producto {
    private Long id;
    private boolean eliminado;
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String urlImagen;
    private boolean disponible;

    public Producto() {}

    public Producto(Long id, boolean eliminado,
                    String nombre, Double precio, String descripcion,
                    int stock, String urlImagen, boolean disponible) {
        this.id = id;
        this.eliminado = eliminado;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.urlImagen = urlImagen;
        this.disponible = disponible;
    }

    // El contrato equals y hashCode para detectar duplicados por nombre y precio
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(nombre, producto.nombre) && Objects.equals(precio, producto.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, precio);
    }

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    // TO STRING
    @Override
    public String toString() {
        return "[ID: " + id + "] " + nombre +
                " - $" + String.format("%.2f", precio) +
                " | Stock: " + stock + " | Disponible: " + disponible;
    }
}

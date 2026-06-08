package com.tup.programacion3.entities;

public class DetallePedido {
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido() {}

    public DetallePedido(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
        calcularSubtotal();
    }

    public void calcularSubtotal() {
        if (this.producto != null) {
            this.subtotal = this.cantidad * this.producto.getPrecio();
        } else {
            this.subtotal = 0.0;
        }
    }

    // GETTERS Y SETTERS
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public Double getSubtotal() { return subtotal; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) {
        this.producto = producto;
        calcularSubtotal();
    }

    // TO STRING
    @Override
    public String toString() {
        return "DetallePedido{producto=" + (producto != null ? producto.getNombre() : "null") +
                ", cantidad=" + cantidad +
                ", subtotal=$" + subtotal + "}";
    }
}

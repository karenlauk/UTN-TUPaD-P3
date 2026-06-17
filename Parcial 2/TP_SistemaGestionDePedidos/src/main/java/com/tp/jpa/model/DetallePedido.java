package com.tp.jpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles_pedido")
public class DetallePedido extends Base {

    private Integer cantidad;
    private Double subtotal; // Correcto según el UML (C1)

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public DetallePedido() {
    }

    public DetallePedido(Integer cantidad, Producto producto, Pedido pedido) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.pedido = pedido;
        // Método seguro para calcular: evita NullPointerException si el producto viene vacío
        this.subtotal = (cantidad != null && producto != null) ? cantidad * producto.getPrecio() : 0.0;
    }

    // Método sugerido para recalcular el subtotal si cambian los datos en caliente
    public void calcularSubtotal() {
        if (this.cantidad != null && this.producto != null) {
            this.subtotal = this.cantidad * this.producto.getPrecio();
        }
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal(); // Se recalcula automáticamente si cambia la cantidad
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        calcularSubtotal(); // Se recalcula automáticamente si cambia el producto
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
package com.tp.jpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles_pedido")
public class DetallePedido extends Base {

    private Integer cantidad;
    private Double subtotal;

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
        this.subtotal = cantidad * producto.getPrecio();
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
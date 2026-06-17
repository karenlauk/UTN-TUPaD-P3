package com.tp.jpa.model;

import com.tp.jpa.model.enums.*;
import com.tp.jpa.model.enums.Estado;
import com.tp.jpa.model.enums.FormaPago;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pedidos")
public class Pedido extends Base implements Calculable {
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Double total = 0.0;

    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Relación correcta usando Set para JPA (C7)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<DetallePedido> detalles = new HashSet<>();

    public Pedido() {}

    // Lógica del negocio organizada con la relación bidireccional asegurada
    public void addDetallePedido(int cantidad, Producto producto) {
        // Usamos el constructor con parámetros que creamos en DetallePedido
        DetallePedido detalle = new DetallePedido(cantidad, producto, this);

        this.detalles.add(detalle);
        calcularTotal();
    }

    // Método requerido por el UML: Buscar detalle por producto (C1)
    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        if (detalles == null || producto == null) return null;
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto() != null && detalle.getProducto().getId().equals(producto.getId())) {
                return detalle;
            }
        }
        return null;
    }

    // Método requerido por el UML: Eliminar detalle por producto (C1)
    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalleEncontrado = findDetallePedidoByProducto(producto);
        if (detalleEncontrado != null) {
            this.detalles.remove(detalleEncontrado);
            calcularTotal();
        }
    }

    // Recalcular el total sumando los subtotales de cada detalle (Calculable)
    @Override
    public void calcularTotal() {
        this.total = 0.0;
        if (detalles != null) {
            for (DetallePedido detalle : detalles) {
                if (detalle.getSubtotal() != null) {
                    this.total += detalle.getSubtotal();
                }
            }
        }
    }

    // Getters y Setters
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Set<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(Set<DetallePedido> detalles) { this.detalles = detalles; }
}
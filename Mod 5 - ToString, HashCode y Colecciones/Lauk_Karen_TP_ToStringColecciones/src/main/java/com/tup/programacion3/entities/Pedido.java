package com.tup.programacion3.entities;
import com.tup.programacion3.enums.Estado;
import com.tup.programacion3.enums.FormaPago;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Pedido {
    private Long id;
    private boolean eliminado;
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Set<DetallePedido> detalles;

    public Pedido() {
        this.fecha = LocalDate.now();
        this.detalles = new HashSet<>();
        this.total = 0.0;
    }

    public Pedido(Long id, boolean eliminado, Estado estado, FormaPago formaPago) {
        this.id = id;
        this.eliminado = eliminado;
        this.fecha = LocalDate.now();
        this.estado = estado;
        this.formaPago = formaPago;
        this.detalles = new HashSet<>();
        this.total = 0.0;
    }

    public void calcularTotal() {
        double suma = 0.0;
        for (DetallePedido dp : detalles) {
            suma += dp.getSubtotal();
        }
        this.total = suma;
    }

    public void addDetallePedido(int cantidad, Producto producto) {
        DetallePedido nuevoDetalle = new DetallePedido(cantidad, producto);
        this.detalles.add(nuevoDetalle);
        calcularTotal();
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido dp : detalles) {
            if (dp.getProducto() != null && dp.getProducto().equals(producto)) {
                return dp;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido aEliminar = findDetallePedidoByProducto(producto);
        if (aEliminar != null) {
            this.detalles.remove(aEliminar);
            calcularTotal();
        }
    }

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Double getTotal() { return total; }

    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }

    public Set<DetallePedido> getDetalles() { return detalles; }

    // TO STRING
    @Override
    public String toString() {
        return "Pedido{id=" + id + ", eliminado=" + eliminado +
                ", fecha=" + fecha + ", estado=" + estado +
                ", total=$" + total + ", items=" + detalles.size() + "}";
    }
}

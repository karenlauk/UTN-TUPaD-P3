package com.utn.entities;
import lombok.*;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true) // Le decimos que muestre el ID de la base
@EqualsAndHashCode(callSuper = false) // Evita que use los campos de Base para comparar detalles
@SuperBuilder
public class DetallePedido extends Base {
    private Integer cantidad;
    private Producto producto;
    public Double calcularSubtotal() {
        // Una validación simple
        if (this.producto == null || this.cantidad == null) {
            return 0.0;
        }
        return this.cantidad * this.producto.getPrecio();
    }
}
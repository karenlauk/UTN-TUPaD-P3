package ar.edu.tup.programacion3.entities;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder

public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public void calcularSubtotal() {
        if (producto != null) {
            subtotal = cantidad * producto.getPrecio();
        } else {
            subtotal = 0.0;
        }
    }
}
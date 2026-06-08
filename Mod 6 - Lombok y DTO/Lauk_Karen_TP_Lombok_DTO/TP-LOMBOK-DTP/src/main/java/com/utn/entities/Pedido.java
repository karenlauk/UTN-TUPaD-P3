package com.utn.entities;
import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true) // Muestra los datos del pedido y su ID heredado
@EqualsAndHashCode(callSuper = false) // Evita conflictos de duplicados en colecciones por culpa de Base
@SuperBuilder
public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private FormaPago formaPago;
    private Double total; // <-- ¡FALTABA AGREGAR ESTA LÍNEA AQUÍ!

    @Builder.Default
    private Set<DetallePedido> detalles = new HashSet<>();
    public void addDetallePedido(Integer cantidad, Producto producto) {
        if (producto != null && cantidad > 0) {
            DetallePedido detalle = DetallePedido.builder()
                    .cantidad(cantidad)
                    .producto(producto)
                    .build();
            detalles.add(detalle);
        }
    }
    @Override
    public void calcularTotal() {
        double acumulador = 0.0;
        for (DetallePedido detalle : detalles) {
            acumulador += detalle.calcularSubtotal();
        }
        this.setTotal(acumulador);
    }
}
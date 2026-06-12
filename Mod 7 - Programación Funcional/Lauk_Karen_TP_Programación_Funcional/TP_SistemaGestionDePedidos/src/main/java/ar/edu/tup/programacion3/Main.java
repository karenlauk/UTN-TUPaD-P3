package ar.edu.tup.programacion3;

import ar.edu.tup.programacion3.entities.*;
import ar.edu.tup.programacion3.enums.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciamos las Categorías
        Categoria catBurgers = Categoria.builder().nombre("Hamburguesas").descripcion("Smash burgers").build();
        Categoria catPizzas = Categoria.builder().nombre("Pizzas").descripcion("Pizzas artesanales").build();
        Categoria catBebidas = Categoria.builder().nombre("Bebidas").descripcion("Gaseosas").build();

        // 2. Instanciamos los Productos (Utilizando los campos del UML)
        Set<Producto> menu = new HashSet<>();

        Producto b1 = Producto.builder().id(1L).nombre("Hamburguesa Triple").precio(25000.0).stock(12).disponible(true).categoria(catBurgers).build();
        Producto b2 = Producto.builder().id(2L).nombre("Hamburguesa Smash Especial").precio(22000.0).stock(3).disponible(true).categoria(catBurgers).build(); // Stock < 5
        Producto p1 = Producto.builder().id(3L).nombre("Pizza Mozzarella").precio(18500.0).stock(20).disponible(true).categoria(catPizzas).build();
        Producto m1 = Producto.builder().id(4L).nombre("Bebida Cola").precio(1500.0).stock(50).disponible(true).categoria(catBebidas).build();
        Producto m2 = Producto.builder().id(5L).nombre("Cerveza Tirada").precio(3000.0).stock(0).disponible(false).categoria(catBebidas).build(); // No disponible
        menu.add(b1); menu.add(b2); menu.add(p1); menu.add(m1); menu.add(m2);

        // 3. Crear Pedidos usando el addDetallePedido
        Pedido pedido1 = Pedido.builder().id(101L).fecha(LocalDate.now()).estado(Estado.PENDIENTE).formaPago(FormaPago.EFECTIVO).build();
        pedido1.addDetallePedido(2, b1);
        pedido1.addDetallePedido(2, m1);
        pedido1.calcularTotal(); // Ejecuta Consigna 1

        Pedido pedido2 = Pedido.builder().id(102L).fecha(LocalDate.now()).estado(Estado.CONFIRMADO).formaPago(FormaPago.TARJETA).build();
        pedido2.addDetallePedido(1, p1);
        pedido2.calcularTotal();

        // Impresión en consola
        System.out.println("Practico del mod 7 - Programacion Funcional");
        System.out.println("---------------------------------------------------------");

        // Consigna 1
        System.out.println("1- TOTALES CALCULADOS");
        System.out.println("   Pedido #101 Total: $" + pedido1.getTotal());
        System.out.println("   Pedido #102 Total: $" + pedido2.getTotal());
        System.out.println("---------------------------------------------------------");

        // Consigna 2
        System.out.println("2- PRODUCTOS DISPONIBLES:");
        menu.stream()
                .filter(Producto::getDisponible)
                .forEach(producto ->
                        System.out.println(producto.getNombre())
                );
        System.out.println("---------------------------------------------------------");

        // Consigna 3
        System.out.println("3- UNIDADES TOTALES POR PEDIDO:");
        int itemsP1 = pedido1.getDetalles().stream().mapToInt(DetallePedido::getCantidad).sum();
        System.out.println("   Pedido #101 tiene: " + itemsP1 + " ítems en total.");
        System.out.println("---------------------------------------------------------");

        // Consigna 4
        System.out.println("4- PRODUCTOS CON STOCK CRÍTICO:");
        menu.stream()
                .filter(producto -> producto.getStock() < 5)
                .forEach(producto ->
                        System.out.println(producto.getNombre()
                                + " - Stock: "
                                + producto.getStock())
                );
        System.out.println("---------------------------------------------------------");
    }
}
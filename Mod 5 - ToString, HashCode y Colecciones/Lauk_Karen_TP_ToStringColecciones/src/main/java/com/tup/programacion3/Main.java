package com.tup.programacion3;
import com.tup.programacion3.enums.Estado;
import com.tup.programacion3.enums.FormaPago;
import com.tup.programacion3.enums.Rol;
import com.tup.programacion3.entities.*;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciar Categorías
        Categoria hamburguesas = new Categoria(1L, false, "Hamburguesas");
        Categoria pizzas = new Categoria(2L, false, "Pizzas");
        Categoria bebidas = new Categoria(3L, false, "Bebidas");

        // 2. Instanciar Catálogo de 10 Productos
        Set<Producto> catalogoProductos = new HashSet<>();

        Producto p1 = new Producto(1L, false,
                "Hamburguesa Triple", 25000.0, "Tres carnes y cheddar",
                15, "h1.jpg", true);
        Producto p2 = new Producto(2L, false,
                "Pizza Calabresa", 20500.0, "Longaniza premium",
                10, "p1.jpg", true);
        Producto p3 = new Producto(3L, false,
                "Bebida Cola", 1500.0, "Lata 354ml",
                50, "b1.jpg", true);
        Producto p4 = new Producto(4L, false,
                "Papas Fritas", 7500.0, "Porción grande",
                30, "f1.jpg", true);
        Producto p5 = new Producto(5L, false,
                "Pizza Mozzarella", 18500.0, "Mucha mozzarella",
                12, "p2.jpg", true);
        Producto p6 = new Producto(6L, false,
                "Garden Ensalada", 15500.0, "Sana y fresca",
                8, "e1.jpg", true);
        Producto p7 = new Producto(7L, false,
                "Sandwich Italiano", 17000.0, "En pan de campo",
                14, "s1.jpg", true);
        Producto p8 = new Producto(8L, false,
                "Papas Fritas con Cheddar", 9000.0, "Papas con cheddar y bacon",
                25, "f2.jpg", true);
        Producto p9 = new Producto(9L, false,
                "Hamburguesa Smash Supremo", 22000.0, "Dos carnes finitas",
                18, "h2.jpg", true);
        Producto p10 = new Producto(10L, false,
                "Agua Mineral", 1200.0, "Con o sin gas",
                40, "b2.jpg", true);

        catalogoProductos.add(p1); catalogoProductos.add(p2); catalogoProductos.add(p3);
        catalogoProductos.add(p4); catalogoProductos.add(p5); catalogoProductos.add(p6);
        catalogoProductos.add(p7); catalogoProductos.add(p8); catalogoProductos.add(p9);
        catalogoProductos.add(p10);

        // Asociar a categorías
        hamburguesas.addProducto(p1); hamburguesas.addProducto(p9);
        pizzas.addProducto(p2); pizzas.addProducto(p5);
        bebidas.addProducto(p3); bebidas.addProducto(p10);

        // 3. Instanciar Usuarios
        Usuario u1 = new Usuario(101L, false, "Ana",
                "Martinez", "ana@mail.com",
                "112233", "123", Rol.USUARIO);
        Usuario u2 = new Usuario(102L, false, "Carlos",
                "Gomez", "carlos@mail.com",
                "445566", "456", Rol.ADMIN);

        // 4. Instanciar Pedidos
        Pedido pedido1 = new Pedido(1001L, false, Estado.PENDIENTE, FormaPago.EFECTIVO);
        pedido1.addDetallePedido(2, p1);
        pedido1.addDetallePedido(1, p4);

        Pedido pedido2 = new Pedido(1002L, false, Estado.CONFIRMADO, FormaPago.TARJETA);
        pedido2.addDetallePedido(1, p2);
        pedido2.addDetallePedido(2, p3);

        Pedido pedido3 = new Pedido(1003L, false, Estado.TERMINADO, FormaPago.TRANSFERENCIA);
        pedido3.addDetallePedido(1, p8);
        pedido3.addDetallePedido(1, p9);

        u1.addPedido(pedido2);
        u1.addPedido(pedido3);
        u2.addPedido(pedido1);

        // 4. MOSTRAR RESULTADOS
        System.out.println("INFORME GENERAL\n");
        System.out.println(" A- MUESTRA DE UN PRODUCTO INDIVIDUAL:");
        System.out.println(p1 + "\n");

        System.out.println(" B- LISTADO COMPLETO DEL CATÁLOGO DE PRODUCTOS:");
        for (Producto prod : catalogoProductos) {
            System.out.println(prod);
        }
        System.out.println();

        System.out.println(" C- DETALLE DE PEDIDOS DEL USUARIO CON MÁS COMPRAS:");
        Usuario masActivo = (u1.getPedidos().size() > u2.getPedidos().size()) ? u1 : u2;
        System.out.println("Cliente más activo: " + masActivo.getNombre() + " " + masActivo.getApellido());
        for (Pedido p : masActivo.getPedidos()) {
            System.out.println("-------------------------------------------------- ");
            System.out.println(p);
            System.out.println("   Detalles de este pedido:");
            for (DetallePedido dp : p.getDetalles()) {
                System.out.println("     -> " + dp);
            }
        }
        System.out.println("-------------------------------------------------- \n");

        // 5. TEST DE DUPLICADOS MEDIANTE EQUALS Y SET
        System.out.println("PRUEBA DE COMPARACIÓN Y UNICIDAD");
        Producto productoClonado = new Producto(999L, false,
                "Hamburguesa Triple", 25000.0, "Copia fraudulenta",
                1, "falso.jpg", true);
        System.out.println("Producto clonado creado para evaluar: " + productoClonado.getNombre() + " ($" + productoClonado.getPrecio() + ")");

        boolean existe = catalogoProductos.contains(productoClonado);
        System.out.println("¿El sistema detecta el clon por compartir Nombre y Precio?: " + (existe ? "SÍ, YA EXISTE" : "NO EXISTE"));

        boolean agregado = catalogoProductos.add(productoClonado);
        System.out.println("¿El Set permitió añadir el producto repetido?: " + (agregado ? "SÍ (Error)" : "NO (Rechazado correctamente)"));
        System.out.println("Cantidad total de productos finales en la colección: " + catalogoProductos.size());
    }
}
package com.tp.jpa;

import com.tp.jpa.util.JPAUtil;
import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Producto;
import com.tp.jpa.repositories.CategoriaRepository;
import com.tp.jpa.repositories.ProductoRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Obtenemos el EntityManager a través de JPAUtil
        EntityManager em = JPAUtil.getEntityManager();
        Scanner scanner = new Scanner(System.in);

        CategoriaRepository catRepo = new CategoriaRepository(em);
        ProductoRepository prodRepo = new ProductoRepository(em);

        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- SISTEMA DE GESTIÓN (PARCIAL 2) ---");
            System.out.println("1. Alta Categoría (HU-01)");
            System.out.println("2. Listar Categorías Activas (HU-02)");
            System.out.println("3. Modificar Descripción de Categoría (HU-03)");
            System.out.println("4. Baja Lógica de Categoría (HU-04)");
            System.out.println("5. Alta Producto (HU-05)");
            System.out.println("6. Listar Productos Activos (HU-06)");
            System.out.println("7. Modificar Precio/Stock de Producto (HU-07)");
            System.out.println("8. Baja Lógica de Producto (HU-08)");
            System.out.println("9. Listar Productos por Categoría (HU-09)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            try {
                switch (opcion) {
                    case 1:
                        System.out.print("Nombre de Categoría: ");
                        String nombreCat = scanner.nextLine();
                        if (catRepo.existeNombre(nombreCat)) {
                            System.out.println("Error: El nombre de la categoría ya existe.");
                        } else {
                            System.out.print("Descripción: ");
                            String descCat = scanner.nextLine();
                            Categoria nuevaCat = new Categoria(nombreCat, descCat);
                            catRepo.guardar(nuevaCat);
                            System.out.println("Categoría guardada con éxito.");
                        }
                        break;

                    case 2:
                        List<Categoria> categorias = catRepo.listarActivos();
                        System.out.println("\n--- Categorías Activas ---");
                        categorias.forEach(c -> System.out.println("ID: " + c.getId() + " | Nombre: " + c.getNombre() + " | Desc: " + c.getDescripcion()));
                        break;

                    case 3:
                        System.out.print("Ingrese ID de la Categoría a modificar: ");
                        Long idModCat = scanner.nextLong();
                        scanner.nextLine();
                        Categoria catAModificar = catRepo.buscarPorId(idModCat);
                        if (catAModificar != null && !catAModificar.isEliminado()) {
                            System.out.print("Nueva Descripción: ");
                            catAModificar.setDescripcion(scanner.nextLine());
                            catRepo.guardar(catAModificar);
                            System.out.println("Descripción actualizada.");
                        } else {
                            System.out.println("Error: Categoría no encontrada o inactiva.");
                        }
                        break;

                    case 4:
                        System.out.print("Ingrese ID de la Categoría a dar de baja: ");
                        Long idBajaCat = scanner.nextLong();
                        catRepo.eliminarLogico(idBajaCat);
                        System.out.println("Baja lógica procesada.");
                        break;

                    case 5:
                        System.out.print("Ingrese ID de la Categoría para el Producto: ");
                        Long idCatProd = scanner.nextLong();
                        scanner.nextLine();
                        Categoria catAsignada = catRepo.buscarPorId(idCatProd);

                        if (catAsignada == null || catAsignada.isEliminado()) {
                            System.out.println("Error: Categoría inválida o inactiva.");
                            break;
                        }

                        System.out.print("Nombre del Producto: ");
                        String nombreProd = scanner.nextLine();
                        System.out.print("Precio: ");
                        Double precioProd = scanner.nextDouble();
                        System.out.print("Stock: ");
                        int stockProd = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Descripción: ");
                        String descProd = scanner.nextLine();

                        Producto nuevoProd = new Producto(nombreProd, precioProd, descProd, stockProd, "img.png", true, catAsignada);
                        prodRepo.guardar(nuevoProd);
                        System.out.println("Producto guardado con éxito.");
                        break;

                    case 6:
                        List<Producto> productos = prodRepo.listarActivos();
                        System.out.println("\n--- Productos Activos ---");
                        productos.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock() + " | Categoría: " + p.getCategoria().getNombre()));
                        break;

                    case 7:
                        System.out.print("Ingrese ID del Producto a modificar: ");
                        Long idModProd = scanner.nextLong();
                        Producto prodAModificar = prodRepo.buscarPorId(idModProd);
                        if (prodAModificar != null && !prodAModificar.isEliminado()) {
                            System.out.print("Nuevo Precio: ");
                            prodAModificar.setPrecio(scanner.nextDouble());
                            System.out.print("Nuevo Stock: ");
                            prodAModificar.setStock(scanner.nextInt());
                            prodRepo.guardar(prodAModificar);
                            System.out.println("Producto actualizado.");
                        } else {
                            System.out.println("Error: Producto no encontrado o inactivo.");
                        }
                        break;

                    case 8:
                        System.out.print("Ingrese ID del Producto a dar de baja: ");
                        Long idBajaProd = scanner.nextLong();
                        prodRepo.eliminarLogico(idBajaProd);
                        System.out.println("Baja lógica procesada.");
                        break;

                    case 9:
                        System.out.print("Ingrese ID de la Categoría a filtrar: ");
                        Long idFiltroCat = scanner.nextLong();
                        List<Producto> prodFiltrados = prodRepo.buscarPorCategoria(idFiltroCat);
                        if (prodFiltrados.isEmpty()) {
                            System.out.println("No hay productos activos para esta categoría.");
                        } else {
                            System.out.println("\n--- Productos de la Categoría ---");
                            prodFiltrados.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre()));
                        }
                        break;

                    case 0:
                        System.out.println("Cerrando sistema...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error de entrada de datos. Intente de nuevo.");
                scanner.nextLine(); // Limpiar el buffer si falla un número
            }
        }

        scanner.close();
        em.close();
        em.close();
    }
}
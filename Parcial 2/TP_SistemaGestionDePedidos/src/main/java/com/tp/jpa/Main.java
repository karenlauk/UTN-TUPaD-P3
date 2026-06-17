package com.tp.jpa;

import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Producto;
import com.tp.jpa.model.Usuario;
import com.tp.jpa.repositories.CategoriaRepository;
import com.tp.jpa.repositories.ProductoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CategoriaRepository catRepo = new CategoriaRepository();
        ProductoRepository prodRepo = new ProductoRepository();

        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n SISTEMA DE GESTION");
            System.out.println("1. Alta Categoria");
            System.out.println("2. Listar Categorias Activas");
            System.out.println("3. Modificar Categoria");
            System.out.println("4. Baja Logica de Categoria");
            System.out.println("5. Alta Producto");
            System.out.println("6. Listar Productos Activos");
            System.out.println("7. Modificar Producto");
            System.out.println("8. Baja Logica de Producto");
            System.out.println("9. Submenu de Reportes"); // Se movió la consulta a un submenú
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer

                switch (opcion) {
                    case 1:
                        System.out.println("\n --------------------------");
                        System.out.println("Alta de Categoría");
                        String nombreCat = "";
                        // Validación de texto no vacío (C8)
                        while (nombreCat.trim().isEmpty()) {
                            System.out.print("Nombre de Categoría: ");
                            nombreCat = scanner.nextLine();
                            if (nombreCat.trim().isEmpty()) {
                                System.out.println("Error: El nombre no puede estar vacío.");
                            }
                        }

                        // Reemplazo de existeNombre usando listarActivos para evitar métodos extra (C7, C8)
                        String finalNombreCat = nombreCat;
                        boolean yaExisteCat = catRepo.listarActivos().stream()
                                .anyMatch(c -> c.getNombre().equalsIgnoreCase(finalNombreCat));

                        if (yaExisteCat) {
                            System.out.println("Error: El nombre de la categoría ya existe.");
                        } else {
                            System.out.print("Descripción: ");
                            String descCat = scanner.nextLine();
                            Categoria nuevaCat = new Categoria(nombreCat, descCat);
                            Categoria guardada = catRepo.guardar(nuevaCat);
                            // Muestra el ID generado exitosamente (C8)
                            System.out.println("Categoría guardada con éxito. ID Generado: " + guardada.getId());
                        }
                        break;

                    case 2:
                        listarCategoríasAux(catRepo);
                        break;

                    case 3:
                        System.out.println("\n --------------------------");
                        System.out.println("Modificación de Categoría ");
                        listarCategoríasAux(catRepo); // Lista las categorías activas antes de pedir ID (C9)

                        System.out.print("Ingrese ID de la Categoría a modificar: ");
                        Long idModCat = scanner.nextLong();
                        scanner.nextLine();

                        // Se maneja el retorno Optional de buscarPorId mediante .orElse(null) (C6)
                        Categoria catAModificar = catRepo.buscarPorId(idModCat).orElse(null);

                        if (catAModificar != null && !catAModificar.isEliminado()) {
                            // Muestra los valores actuales antes de pedir los nuevos (C9)
                            System.out.println("Valores actuales -> Nombre: " + catAModificar.getNombre() + " | Descripción: " + catAModificar.getDescripcion());

                            System.out.print("Nueva Descripción (Presione Enter para mantener actual): ");
                            String nuevaDesc = scanner.nextLine();

                            // Si se deja en blanco no se sobrescribe con vacío, se mantiene el anterior (C9)
                            if (!nuevaDesc.trim().isEmpty()) {
                                catAModificar.setDescripcion(nuevaDesc);
                                catRepo.guardar(catAModificar);
                                System.out.println("Descripción actualizada.");
                            } else {
                                System.out.println("Operación omitida. Se conservó el valor anterior.");
                            }
                        } else {
                            System.out.println("Error: Categoría no encontrada o inactiva."); // Error explícito (C9)
                        }
                        break;

                    case 4:
                        System.out.println("\n --------------------------");
                        System.out.println("Baja Lógica de Categoría");
                        listarCategoríasAux(catRepo); // Lista antes de pedir ID (C10)

                        System.out.print("Ingrese ID de la Categoría a dar de baja: ");
                        Long idBajaCat = scanner.nextLong();
                        scanner.nextLine();

                        Categoria catABajar = catRepo.buscarPorId(idBajaCat).orElse(null);

                        if (catABajar != null && !catABajar.isEliminado()) {
                            String nombreAfectado = catABajar.getNombre();
                            boolean exito = catRepo.eliminarLogico(idBajaCat); // Recibe el boolean del refactor (C6)
                            if (exito) {
                                // Confirma la operación mostrando el nombre afectado (C10)
                                System.out.println("Operación exitosa. Se dio de baja la categoría: " + nombreAfectado);
                            }
                        } else {
                            // Informa si el ID no existe o ya estaba de baja (C10)
                            System.out.println("Error: El ID no existe o la categoría ya está dada de baja.");
                        }
                        break;

                    case 5:
                        System.out.println("\n --------------------------");
                        System.out.println("Alta de Producto");
                        listarCategoríasAux(catRepo); // Lista las categorías para que el operador elija (C11)

                        System.out.print("Ingrese ID de la Categoría para el Producto: ");
                        Long idCatProd = scanner.nextLong();
                        scanner.nextLine();
                        Categoria catAsignada = catRepo.buscarPorId(idCatProd).orElse(null);

                        if (catAsignada == null || catAsignada.isEliminado()) {
                            System.out.println("Error: Categoría inválida o inactiva.");
                            break;
                        }

                        String nombreProd = "";
                        while (nombreProd.trim().isEmpty()) {
                            System.out.print("Nombre del Producto: ");
                            nombreProd = scanner.nextLine();
                            if (nombreProd.trim().isEmpty()) {
                                System.out.println("Error: El nombre no puede estar vacío.");
                            }
                        }

                        // Validación precio > 0 (C11)
                        Double precioProd = -1.0;
                        while (precioProd <= 0) {
                            System.out.print("Precio (mayor a 0): ");
                            precioProd = scanner.nextDouble();
                            if (precioProd <= 0) {
                                System.out.println("Error: El precio debe ser mayor a 0.");
                            }
                        }

                        // Validación stock >= 0 (C11)
                        int stockProd = -1;
                        while (stockProd < 0) {
                            System.out.print("Stock (mayor o igual a 0): ");
                            stockProd = scanner.nextInt();
                            if (stockProd < 0) {
                                System.out.println("Error: El stock no puede ser negativo.");
                            }
                        }
                        scanner.nextLine();

                        System.out.print("Descripción: ");
                        String descProd = scanner.nextLine();

                        Producto nuevoProd = new Producto(nombreProd, precioProd, descProd, stockProd, "img.png", false, catAsignada);
                        Producto prodGuardado = prodRepo.guardar(nuevoProd);
                        // Muestra el ID generado y la categoría asignada (C11)
                        System.out.println("Producto guardado con éxito. ID: " + prodGuardado.getId() + " | Categoría: " + catAsignada.getNombre());
                        break;

                    case 6:
                        listarProductosAux(prodRepo);
                        break;

                    case 7:
                        System.out.println("\n --------------------------");
                        System.out.println("Modificación de Producto");
                        listarProductosAux(prodRepo); // Lista los productos activos previamente (C12)

                        System.out.print("Ingrese ID del Producto a modificar: ");
                        Long idModProd = scanner.nextLong();
                        scanner.nextLine();

                        Producto prodAModificar = prodRepo.buscarPorId(idModProd).orElse(null);

                        if (prodAModificar != null && !prodAModificar.isEliminado()) {
                            // Muestra los valores actuales por pantalla (C12)
                            System.out.println("Valores actuales -> Nombre: " + prodAModificar.getNombre() + " | Precio: $" + prodAModificar.getPrecio() + " | Stock: " + prodAModificar.getStock());

                            // Modificación segura: si presiona enter/deja en blanco mantiene el valor anterior (C12)
                            System.out.print("Nuevo Precio (Presione Enter para mantener actual): ");
                            String inputPrecio = scanner.nextLine();
                            if (!inputPrecio.trim().isEmpty()) {
                                double nuevoPrecio = Double.parseDouble(inputPrecio);
                                if(nuevoPrecio > 0) { // Validación de rango (C12)
                                    prodAModificar.setPrecio(nuevoPrecio);
                                } else {
                                    System.out.println("Precio inválido. Se mantiene el valor anterior.");
                                }
                            }

                            System.out.print("Nuevo Stock (Presione Enter para mantener actual): ");
                            String inputStock = scanner.nextLine();
                            if (!inputStock.trim().isEmpty()) {
                                int nuevoStock = Integer.parseInt(inputStock);
                                if(nuevoStock >= 0) { // Validación de rango (C12)
                                    prodAModificar.setStock(nuevoStock);
                                } else {
                                    System.out.println("Stock inválido. Se mantiene el valor anterior.");
                                }
                            }

                            prodRepo.guardar(prodAModificar);
                            System.out.println("Producto actualizado.");
                        } else {
                            System.out.println("Error: Producto no encontrado o inactivo."); // Mensaje explícito (C12)
                        }
                        break;

                    case 8:
                        System.out.println("\n --------------------------");
                        System.out.println("Baja Lógica de Producto");
                        listarProductosAux(prodRepo); // Lista los productos antes del ID (C13)

                        System.out.print("Ingrese ID del Producto a dar de baja: ");
                        Long idBajaProd = scanner.nextLong();
                        scanner.nextLine();

                        Producto prodABajar = prodRepo.buscarPorId(idBajaProd).orElse(null);

                        if (prodABajar != null && !prodABajar.isEliminado()) {
                            String nombreProdAfectado = prodABajar.getNombre();
                            boolean exito = prodRepo.eliminarLogico(idBajaProd); // Uso de boolean (C6)
                            if (exito) {
                                // Muestra confirmación con el nombre del producto (C13)
                                System.out.println("Operación exitosa. Se dio de baja el producto: " + nombreProdAfectado);
                            }
                        } else {
                            // Informa explícitamente la inexistencia o baja previa (C13)
                            System.out.println("Error: El ID no existe o el producto ya está dado de baja.");
                        }
                        break;

                    case 9:
                        // Submenú de Reportes (C14)
                        mostrarSubmenuReportes(scanner, catRepo, prodRepo);
                        break;

                    case 0:
                        System.out.println("Cerrando sistema...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error de entrada de datos o proceso. Intente de nuevo.");
                scanner.nextLine(); // Limpiar buffer ante excepciones
            }
        }
        scanner.close();
    }

    // Métodos auxiliares para evitar duplicar código y cumplir con listar antes de pedir datos
    private static void listarCategoríasAux(CategoriaRepository catRepo) {
        List<Categoria> categorias = catRepo.listarActivos();
        System.out.println("Categorías Activas");
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías activas registradas.");
        } else {
            categorias.forEach(c -> System.out.println("ID: " + c.getId() + " | Nombre: " + c.getNombre() + " | Desc: " + c.getDescripcion()));
        }
    }

    private static void listarProductosAux(ProductoRepository prodRepo) {
        List<Producto> productos = prodRepo.listarActivos();
        System.out.println("\n Productos Activos");
        if (productos.isEmpty()) {
            System.out.println("No hay productos activos registrados.");
        } else {
            productos.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock() + " | Categoría: " + p.getCategoria().getNombre()));
        }
    }

    // Submenú de reportes (C9)
    private static void mostrarSubmenuReportes(Scanner scanner, CategoriaRepository catRepo, ProductoRepository prodRepo) {
        System.out.println("\n SUBMENÚ DE REPORTES");
        System.out.println("1. Listar Productos por Categoría");
        System.out.println("2. Ver Usuarios Base del Sistema (C9)");
        System.out.println("0. Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
        int opReporte = scanner.nextInt();
        scanner.nextLine();

        if (opReporte == 1) {
            // 1. Primero traemos la lista de categorías activas
            List<Categoria> categorias = catRepo.listarActivos();

            // 2. CONTROL CLAVE: Si está vacía, avisamos y salimos del reporte
            if (categorias.isEmpty()) {
                System.out.println("No hay categorías activas registradas en el sistema. No se puede filtrar.");
            } else {
                // 3. Si hay categorías, las mostramos y recién ahí pedimos el ID
                System.out.println("Categorías Activas:");
                categorias.forEach(c -> System.out.println("ID: " + c.getId() + " | Nombre: " + c.getNombre() + " | Desc: " + c.getDescripcion()));

                System.out.print("\nIngrese ID de la Categoría a filtrar: ");
                Long idFiltroCat = scanner.nextLong();
                scanner.nextLine();

                List<Producto> prodFiltrados = prodRepo.buscarPorCategoria(idFiltroCat);
                if (prodFiltrados.isEmpty()) {
                    System.out.println("No hay productos activos para esta categoría.");
                } else {
                    System.out.println("\n Productos de la Categoría ");
                    prodFiltrados.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock()));
                }
            }
        }
        else if (opReporte == 2) {
            EntityManager em = null;
            try {
                em = Persistence.createEntityManagerFactory("miUnidad").createEntityManager();

                List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();

                System.out.println("\n Usuarios Persistidos Base:");
                if (usuarios.isEmpty()) {
                    System.out.println("No hay usuarios registrados en la base de datos todavía.");
                } else {
                    usuarios.forEach(u -> System.out.println("ID: " + u.getId() + " | Nombre: " + u.getNombre() + " " + u.getApellido() + " | Mail: " + u.getMail() + " | Rol: " + u.getRol()));
                }
            } catch (Exception e) {
                System.out.println("Error al conectar con la base de datos para listar usuarios.");
                e.printStackTrace();
            } finally {
                if (em != null && em.isOpen()) em.close();
            }
        }
    }
}
package ar.edu.tup.programacion3;

import ar.edu.tup.programacion3.entities.*;
import ar.edu.tup.programacion3.enums.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("-----------------------------------------");
            System.out.println("INICIANDO TRANSACCIÓN DE DATOS INICIALES");
            System.out.println("-----------------------------------------");
            em.getTransaction().begin();

            // 1. Instanciar y Persistir Categorías (Tus 6 categorías del Food Store)
            Categoria c1 = new Categoria("Hamburguesas", "Variedad de hamburguesas smash");
            Categoria c2 = new Categoria("Pizzas", "Pizzas artesanales al horno");
            Categoria c3 = new Categoria("Bebidas", "Bebidas y gaseosas lineas premium");
            Categoria c4 = new Categoria("Guarniciones", "Acompañamientos crujientes");
            Categoria c5 = new Categoria("Ensaladas", "Opciones frescas y saludables");
            Categoria c6 = new Categoria("Sandwiches", "Sandwiches gourmet en pan baguette");
            em.persist(c1); em.persist(c2); em.persist(c3);
            em.persist(c4); em.persist(c5); em.persist(c6);

            // 2. Instanciar y Persistir Productos (Tus 10 productos reales con sus precios)
            Producto p1 = new Producto("Hamburguesa Triple", 25000.0, c1);
            Producto p2 = new Producto("Hamburguesa Smash Supremo", 22000.0, c1);
            Producto p3 = new Producto("Pizza Calabresa", 20500.0, c2);
            Producto p4 = new Producto("Pizza Mozzarella", 18500.0, c2);
            Producto p5 = new Producto("Bebida Cola", 1500.0, c3);
            Producto p6 = new Producto("Papas Fritas", 7500.0, c4);
            Producto p7 = new Producto("Papas Fritas con Cheddar", 9000.0, c4);
            Producto p8 = new Producto("Garden Ensalada", 15500.0, c5);
            Producto p9 = new Producto("Sandwich Italiano", 17000.0, c6);
            Producto p10 = new Producto("Sandwich Milanesa", 18000.0, c6);

            em.persist(p1); em.persist(p2); em.persist(p3); em.persist(p4); em.persist(p5);
            em.persist(p6); em.persist(p7); em.persist(p8); em.persist(p9); em.persist(p10);

            // 3. Instanciar y Persistir Usuarios (Tus usuarios reales: Admin Karen y Cliente Juan)
            Usuario u1 = new Usuario("Karen", "Lauk", "karen.lauk@mail.com", "karenPass2026", "123", Rol.ADMIN);
            Usuario u2 = new Usuario("Juan", "Lopez", "Juan@gmail.com", "Juanlopez", "abc", Rol.USUARIO);
            em.persist(u1);
            em.persist(u2);

            // 4. Crear 3 Pedidos Reales (Con al menos 2 detalles cada uno)
            // Pedido 1: Combo Finde de Karen (2 Burgers Triples + 2 Bebidas Cola)
            Pedido pedido1 = new Pedido(LocalDate.now(), Estado.TERMINADO, FormaPago.EFECTIVO, u1);
            pedido1.addDetallePedido(2, p1);
            pedido1.addDetallePedido(2, p5);
            pedido1.calcularTotal();
            em.persist(pedido1);

            // Pedido 2: Cena de Karen (1 Pizza Calabresa + 1 Papas Cheddar)
            Pedido pedido2 = new Pedido(LocalDate.now(), Estado.CONFIRMADO, FormaPago.TARJETA, u1);
            pedido2.addDetallePedido(1, p3);
            pedido2.addDetallePedido(1, p7);
            pedido2.calcularTotal();
            em.persist(pedido2);

            // Pedido 3: Almuerzo de Juan (1 Sandwich Italiano + 1 Garden Ensalada)
            Pedido pedido3 = new Pedido(LocalDate.now(), Estado.PENDIENTE, FormaPago.TRANSFERENCIA, u2);
            pedido3.addDetallePedido(1, p9);
            pedido3.addDetallePedido(1, p8);
            pedido3.calcularTotal();
            em.persist(pedido3);

            em.getTransaction().commit();
            System.out.println("-----------------------------------------");
            System.out.println("INSERCIONES EXITOSAS E INFORMACIÓN PERSISTIDA");

            // 5. Actualización de Entidades (Actualiza al menos 2 productos)
            em.getTransaction().begin();
            System.out.println("-----------------------------------------");
            System.out.println("ACTUALIZANDO PRECIO DE PRODUCTOS");

            Producto productoA = em.find(Producto.class, p1.getId()); // Hamburguesa Triple
            if (productoA != null) {
                productoA.setPrecio(27000.0);
            }
            Producto productoB = em.find(Producto.class, p3.getId()); // Pizza Calabresa
            if (productoB != null) {
                productoB.setPrecio(22000.0);
            }
            em.getTransaction().commit();

            // 6. Buscar Usuario por ID (Completando requerimiento faltante)
            System.out.println("-----------------------------------------");
            System.out.println("BUSCANDO USUARIO POR ID");
            Usuario usuarioPorId = em.find(Usuario.class, u1.getId()); // Te busca a vos
            if (usuarioPorId != null) {
                System.out.println("Usuario Encontrado por ID -> Nombre: " + usuarioPorId.getNombre() + " " + usuarioPorId.getApellido());
            }

            // 7. Buscar Usuario por Mail empleando JPQL (Completando requerimiento faltante)
            System.out.println("-----------------------------------------");
            System.out.println("BUSCANDO USUARIO POR MAIL");
            Usuario usuarioPorMail = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :email", Usuario.class)
                    .setParameter("email", "Juan@gmail.com")
                    .getSingleResult();
            System.out.println("Usuario Encontrado por Mail -> Nombre: " + usuarioPorMail.getNombre() + " | Mail: " + usuarioPorMail.getMail());

            // 8. Borrar 1 producto (Completando requerimiento de baja sobre el Sandwich de Milanesa)
            em.getTransaction().begin();
            System.out.println("-----------------------------------------");
            System.out.println("BORRANDO UN PRODUCTO");
            Producto productoABorrar = em.find(Producto.class, p10.getId());
            if (productoABorrar != null) {
                em.remove(productoABorrar);
                System.out.println("Producto '" + productoABorrar.getNombre() + "' eliminado correctamente.");
            }
            em.getTransaction().commit();

            // 9. Aplicación Combinada: Consultas JPA + Transformaciones con Streams
            System.out.println("-----------------------------------------");
            System.out.println("PROCESANDO CONSULTAS CON STREAMS SOBRE LOS DATOS DE LA BD");

            List<Pedido> pedidosDeBaseDeDatos = em.createQuery("SELECT p FROM Pedido p", Pedido.class).getResultList();

            List<Pedido> pedidosEntregadosAltos = pedidosDeBaseDeDatos.stream()
                    .filter(p -> p.getEstado() == Estado.TERMINADO)
                    .filter(p -> p.getTotal() > 5000.0)
                    .collect(Collectors.toList());
            System.out.println("-----------------------------------------");
            System.out.println("Pedidos Entregados con montos mayores a $5000:");
            pedidosEntregadosAltos.forEach(p ->
                    System.out.println("ID Pedido: " + p.getId() + " | Cliente: " + p.getUsuario().getNombre() + " | Total: $" + p.getTotal())
            );

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
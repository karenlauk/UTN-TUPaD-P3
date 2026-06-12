package ar.edu.tup.programacion3;

import ar.edu.tup.programacion3.entities.*;
import ar.edu.tup.programacion3.enums.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            System.out.println("-----------------------------------------");
            System.out.println("INICIANDO TRANSACCIÓN DE DATOS INICIALES");
            System.out.println("-----------------------------------------");

            // 1. PERSISTIR EXACTAMENTE 3 CATEGORÍAS (C9)
            Categoria cat1 = new Categoria("Comida", "Platos principales");
            Categoria cat2 = new Categoria("Bebidas", "Gaseosas y jugos");
            Categoria cat3 = new Categoria("Postres", "Dulces y helados");
            em.persist(cat1);
            em.persist(cat2);
            em.persist(cat3);

            // 2. PERSISTIR 10 PRODUCTOS ORIGINALES (C9)
            Producto p1 = new Producto("Hamburguesa", 1500.0, "Con queso", 10, "img1.png", true, cat1);
            Producto p2 = new Producto("Papas Fritas", 800.0, "Porción grande", 20, "img2.png", true, cat1);
            Producto p3 = new Producto("Pizza", 2000.0, "Muzzarella", 5, "img3.png", true, cat1);
            Producto p4 = new Producto("Empanada", 300.0, "Carne", 50, "img4.png", true, cat1);
            Producto p5 = new Producto("Coca Cola", 600.0, "Lata 354ml", 30, "img5.png", true, cat2);
            Producto p6 = new Producto("Agua Mineral", 500.0, "Sin gas 500ml", 40, "img6.png", true, cat2);
            Producto p7 = new Producto("Cerveza", 1200.0, "Ipa", 15, "img7.png", true, cat2);
            Producto p8 = new Producto("Helado", 700.0, "Tacita 2 gustos", 12, "img8.png", true, cat3);
            Producto p9 = new Producto("Flan", 500.0, "Con dulce de leche", 8, "img9.png", true, cat3);
            Producto p10 = new Producto("Torta", 900.0, "Porción de Lemon Pie", 6, "img10.png", true, cat3);

            em.persist(p1); em.persist(p2); em.persist(p3); em.persist(p4); em.persist(p5);
            em.persist(p6); em.persist(p7); em.persist(p8); em.persist(p9); em.persist(p10);

            // PRODUCTO SEPARADO N°11 CONSTRUIDO SOLO PARA ELIMINAR SIN MANDAR AL DETALLE DE NINGÚN PEDIDO (C14)
            Producto pParaBorrar = new Producto("Producto Prueba", 100.0, "Temporal", 5, "test.png", true, cat2);
            em.persist(pParaBorrar);

            // 3. PERSISTIR 2 USUARIOS CON SUS ATRIBUTOS COMPLETOS (C9)
            Usuario u1 = new Usuario();
            u1.setNombre("Karen");
            u1.setApellido("Lauk");
            u1.setMail("karen@mail.com");
            u1.setRol(Rol.USUARIO);
            em.persist(u1);

            Usuario u2 = new Usuario();
            u2.setNombre("Juan");
            u2.setApellido("Perez");
            u2.setMail("juan@mail.com");
            u2.setRol(Rol.ADMIN);
            em.persist(u2);

            // 4. PERSISTIR 3 PEDIDOS CON SUS DETALLES (C10)
            Pedido ped1 = new Pedido();
            ped1.setFecha(LocalDate.now());
            ped1.setEstado(Estado.PENDIENTE);
            ped1.setFormaPago(FormaPago.EFECTIVO);
            ped1.setUsuario(u1);
            ped1.addDetallePedido(2, p1);
            ped1.addDetallePedido(1, p5);
            em.persist(ped1);

            Pedido ped2 = new Pedido();
            ped2.setFecha(LocalDate.now());
            ped2.setEstado(Estado.CONFIRMADO);
            ped2.setFormaPago(FormaPago.TARJETA);
            ped2.setUsuario(u1);
            ped2.addDetallePedido(1, p3);
            ped2.addDetallePedido(3, p6);
            em.persist(ped2);

            @SuppressWarnings("unused")
            Pedido ped3 = new Pedido();
            ped3.setFecha(LocalDate.now());
            ped3.setEstado(Estado.TERMINADO);
            ped3.setFormaPago(FormaPago.TRANSFERENCIA);
            ped3.setUsuario(u2);
            ped3.addDetallePedido(1, p10);
            ped3.addDetallePedido(2, p2);
            em.persist(ped3);

            // 5. ACTUALIZAR AL MENOS 2 PRODUCTOS (C11)
            p1.setPrecio(1700.0);
            p5.setPrecio(650.0);

            // 6. BORRAR 1 PRODUCTO (C14)
            em.remove(pParaBorrar);

            em.getTransaction().commit();
            System.out.println("-----------------------------------------");
            System.out.println("TRANSACCIÓN FINALIZADA CON ÉXITO");
            System.out.println("-----------------------------------------");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // 7. BUSCAR USUARIO POR ID (C12)
            Usuario usuarioId = em.find(Usuario.class, 1L);
            if(usuarioId != null) {
                System.out.println("Busqueda ID -> Usuario: " + usuarioId.getNombre() + " " + usuarioId.getApellido());
            }

            // 8. BUSCAR USUARIO POR MAIL (C13)
            try {
                Usuario usuarioMail = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :email", Usuario.class)
                        .setParameter("email", "karen@mail.com")
                        .getSingleResult();
                System.out.println("Busqueda JPQL -> Usuario encontrado: " + usuarioMail.getNombre());
            } catch (Exception ex) {
                System.out.println("No se localizó el email buscado.");
            }

            em.close();
            emf.close();
        }
    }
}
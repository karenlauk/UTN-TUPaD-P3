package com.utn;
import com.utn.dtos.UsuarioDTO;
import com.utn.entities.*;
import com.utn.enums.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        // INSTANCIAR LAS CATEGORÍAS REALES DEL FRONTEND
        Categoria catBurgers = Categoria.builder().nombre("Hamburguesas").descripcion("Variedad de hamburguesas smash").build();
        Categoria catPizzas = Categoria.builder().nombre("Pizzas").descripcion("Pizzas artesanales al horno").build();
        Categoria catBebidas = Categoria.builder().nombre("Bebidas").descripcion("Bebidas y gaseosas lineas premium").build();
        Categoria catGuarniciones = Categoria.builder().nombre("Guarniciones").descripcion("Acompañamientos crujientes").build();
        Categoria catEnsaladas = Categoria.builder().nombre("Ensaladas").descripcion("Opciones frescas y saludables").build();
        Categoria catSandwiches = Categoria.builder().nombre("Sandwiches").descripcion("Sandwiches gourmet en pan baguette").build();

        // INSTANCIAR LOS PRODUCTOS REALES (Mismos nombres y precios que el archivo de TypeScript)
        Set<Producto> menuProductos = new HashSet<>();

        // Hamburguesas
        Producto burgerTriple = Producto.builder().nombre("Hamburguesa Triple").precio(25000.0).categoria(catBurgers).build();
        Producto burgerSmash = Producto.builder().nombre("Hamburguesa Smash Supremo").precio(22000.0).categoria(catBurgers).build();

        // Pizzas
        Producto pizzaCalabresa = Producto.builder().nombre("Pizza Calabresa").precio(20500.0).categoria(catPizzas).build();
        Producto pizzaMuzzarella = Producto.builder().nombre("Pizza Mozzarella").precio(18500.0).categoria(catPizzas).build();

        // Bebidas
        Producto bebidaCola = Producto.builder().nombre("Bebida Cola").precio(1500.0).categoria(catBebidas).build();

        // Guarniciones
        Producto papasFritas = Producto.builder().nombre("Papas Fritas").precio(7500.0).categoria(catGuarniciones).build();
        Producto papasCheddar = Producto.builder().nombre("Papas Fritas con Cheddar").precio(9000.0).categoria(catGuarniciones).build();

        // Ensaladas y Sandwiches
        Producto ensaladaGarden = Producto.builder().nombre("Garden Ensalada").precio(15500.0).categoria(catEnsaladas).build();
        Producto sandwichItaliano = Producto.builder().nombre("Sandwich Italiano").precio(17000.0).categoria(catSandwiches).build();
        Producto sandwichMilanesas = Producto.builder().nombre("Sandwich Milanesa").precio(18000.0).categoria(catSandwiches).build();

        // Agregamos los 10 productos reales al catálogo
        menuProductos.add(burgerTriple);
        menuProductos.add(burgerSmash);
        menuProductos.add(pizzaCalabresa);
        menuProductos.add(pizzaMuzzarella);
        menuProductos.add(bebidaCola);
        menuProductos.add(papasFritas);
        menuProductos.add(papasCheddar);
        menuProductos.add(ensaladaGarden);
        menuProductos.add(sandwichItaliano);
        menuProductos.add(sandwichMilanesas);

        // INSTANCIAR 3 PEDIDOS REALIZADOS EN EL LOCAL
        // Pedido 1: Combo de hamburguesas para el fin de semana
        Pedido pedidoFinde = Pedido.builder().fecha(LocalDate.now()).estado(Estado.PENDIENTE).formaPago(FormaPago.EFECTIVO).build();
        pedidoFinde.addDetallePedido(2, burgerTriple);  // 2 Hamburguesas Triples
        pedidoFinde.addDetallePedido(2, bebidaCola);     // 2 Bebidas Cola
        pedidoFinde.calcularTotal(); // Orden correcto: Calcula e impacta internamente mediante el setTotal de Lombok

        // Pedido 2: Cena de pizzas y guarnición
        Pedido pedidoCena = Pedido.builder().fecha(LocalDate.now()).estado(Estado.CONFIRMADO).formaPago(FormaPago.TARJETA).build();
        pedidoCena.addDetallePedido(1, pizzaCalabresa);  // 1 Pizza Calabresa
        pedidoCena.addDetallePedido(1, papasCheddar);   // 1 Papas con Cheddar
        pedidoCena.calcularTotal(); // Orden correcto: Calcula e impacta internamente mediante el setTotal de Lombok

        // Pedido 3: Opción más liviana o almuerzo rápido
        Pedido pedidoAlmuerzo = Pedido.builder().fecha(LocalDate.now()).estado(Estado.PENDIENTE).formaPago(FormaPago.TRANSFERENCIA).build();
        pedidoAlmuerzo.addDetallePedido(1, sandwichItaliano); // 1 Sandwich Italiano
        pedidoAlmuerzo.addDetallePedido(1, ensaladaGarden);   // 1 Garden Ensalada
        pedidoAlmuerzo.calcularTotal(); // Orden correcto: Calcula e impacta internamente mediante el setTotal de Lombok

        // INSTANCIAR 2 USUARIOS REALES (Admin Karen Lauk y un Cliente de prueba)
        Usuario adminKaren = Usuario.builder().nombre("Karen").apellido("Lauk").mail("karen.lauk@mail.com").contraseña("karenPass2026").rol(Rol.ADMIN).build();
        Usuario clientejuan = Usuario.builder().nombre("Juan").apellido("Lopez").mail("Juan@gmail.com").contraseña("Juanlopez").rol(Rol.USUARIO).build();

        // Vinculamos los pedidos calculados a los historiales de los usuarios
        adminKaren.getPedidos().add(pedidoFinde);
        adminKaren.getPedidos().add(pedidoCena);
        clientejuan.getPedidos().add(pedidoAlmuerzo);

        Set<Usuario> listaUsuarios = Set.of(adminKaren, clientejuan);

        // MUESTRAS SOLICITADAS POR CONSOLA
        System.out.println("VISTA DE UN PRODUCTO INDIVIDUAL:");
        System.out.println(burgerTriple);

        System.out.println("\nMENU COMPLETO DISPONIBLE EN EL FOOD STORE:");
        for (Producto prod : menuProductos) {
            System.out.println(prod);
        }

        // BUSQUEDA MANUAL DEL USUARIO CON MAS PEDIDOS (Estilo Algorítmico Tradicional)
        System.out.println("\nDETECCION DEL CLIENTE CON MAS ACTIVIDAD:");
        Usuario usuarioMasActivo = null;
        int maxPedidos = -1;

        for (Usuario user : listaUsuarios) {
            if (user.getPedidos().size() > maxPedidos) {
                maxPedidos = user.getPedidos().size();
                usuarioMasActivo = user;
            }
        }

        if (usuarioMasActivo != null) {
            System.out.println("Cliente frecuente detectado: " + usuarioMasActivo.getNombre() + " " + usuarioMasActivo.getApellido());
            System.out.println("Historial de compras de este usuario:");
            for (Pedido ped : usuarioMasActivo.getPedidos()) {
                System.out.println(ped); // Imprime toda la información estructurada por Lombok
                System.out.println("-> Total leído desde el atributo del Pedido: $" + ped.getTotal()); // Muestra el total real calculado
            }
        }

        // VALIDACIÓN DE EQUALS Y HASHCODE (Control de Duplicados en la Carta)
        System.out.println("\nDETECCION DE COMIDA DUPLICADA EN LA CARTA:");
        // Intentamos crear un producto lógicamente igual a uno existente (Mismo nombre)
        Producto hamburguesaClonada = Producto.builder()
                .nombre("Hamburguesa Triple")
                .precio(25000.0)
                .categoria(catBurgers)
                .build();

        System.out.println("¿El catalogo detecta que la hamburguesa ya existe logicamente?: " + menuProductos.contains(hamburguesaClonada));
        boolean sePudoAgregar = menuProductos.add(hamburguesaClonada);
        System.out.println("¿El sistema permitio ingresar el elemento duplicado?: " + sePudoAgregar);
        System.out.println("Cantidad de items reales remanentes en el menu: " + menuProductos.size());

        // MOSTRAR LA VISTA OBLIGATORIA DEL DTO
        System.out.println("\nVISTA DTO SEGURA (Para visualizacion o repartidores):");
        UsuarioDTO vistaSegura = UsuarioDTO.desdeEntidad(adminKaren);
        System.out.println(vistaSegura);
    }
}
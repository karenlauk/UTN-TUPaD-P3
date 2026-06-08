/*La Base de Datos*/

/*Arrays de lista de categorías*/
const categorias = ["Hamburguesas", "Pizzas", "Bebidas" , "Papas Fritas"]   /*Lista de string*/
const productos = [
    {
        id: 1,                                                  /*identificador*/
        nombre: "Hamburguesa Triple",                           /*Nombre del producto*/
        descripcion: "Triple carne, cheddar y bacon",           /*Detalle*/
        precio: 25000,                                          /*Valor*/
        imagen: "assets/img1.jpg",                              /*Ruta de la imagen*/
        categoria:"Hamburguesas"                                /*A qué grupo pertenece*/
    },
    {
        id: 2, 
        nombre: "Pizza Muzzarrella",
        descripcion:"Salsa casera y orégano",
        precio: 30000,
        imagen: "assets/img2.jpg" ,
        categoria:"Pizzas",
    },
        {
        id: 3, 
        nombre: "Bebidas",
        descripcion:"A elección: Coca-cola, Sprite, Pepsi y Fanta",
        precio: 1500,
        imagen: "assets/img3.jpg",
        categoria:"Bebidas",
    },
        {
        id: 4, 
        nombre: "Papas Fritas",
        descripcion:"Guarnición en bastones y sin sal",
        precio: 7000,
        imagen: "assets/img4.jpg",
        categoria:"Papas Fritas",
    },
];


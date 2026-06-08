/*Lógica de renderizado*/

/*Categorías - Leer el array y crear un <li> por cada categoría*/
function cargarCategorias(){                                        /*Definimos la función*/
    const lista = document.getElementById("lista-categorias");      /*Busca en el HTML el <ul>*/
    categorias.forEach(cat => {                                     /*Recorre el array*/                                    
    const li = document.createElement("li");                        /*Crea un <li> nuevo*/
    li.innerHTML = `<a href="#">${cat}</a>`;                        /*Le pone contenido*/
    lista.appendChild(li);                                          /*Lo agrega al HTML*/
    });
}                   /*Por cada categoría, crea un elemento y lo agrega a la lista*/

/*Producto - Leer productos y crear tarjetas*/
function cargarProductos() {                                                
    const contenedor = document.getElementById("contenedor-productos");     /*Busca donde van los productos*/
    productos.forEach(prod => {                                             /*Recorre todos los productos*/
        const article = document.createElement("article");                  /*Crea una tarjeta*/   
        article.innerHTML = `
        <img src="${prod.imagen}">
        <h3>${prod.nombre}</h3>
        <p>${prod.descripcion}</p>
        <strong>$${prod.precio}</strong>
        <button onclick="alert('Agregaste ${prod.nombre}')">
            Agregar
        </button>
        `;                                                              /*Llenamos la tarjeta con datos dinámicos*/
        contenedor.appendChild(article);                  
    });
}                    /*Por cada producto, se crea una tarjeta con sus datos y se inserta en el DOM*/  

/*Ejecución final*/
cargarCategorias();
cargarProductos();


/*
array.forEach(elemento => {
    crear elemento HTML
    llenarlo con datos
    agregarlo al DOM
});
*/
document.addEventListener("DOMContentLoaded", function () {
    const menuMobile = document.querySelector(".menu-mobile");
    const navDesktop = document.querySelector(".nav-desktop");
    // Seleccionamos los enlaces internos que van a secciones con '#'
    const menuLinks = document.querySelectorAll(".nav-desktop a[href^='#']");
    const icon = menuMobile ? menuMobile.querySelector("i") : null;

    if (menuMobile && navDesktop) {
        // 1. COMPORTAMIENTO AL PULSAR EL BOTÓN HAMBURGUESA
        menuMobile.addEventListener("click", function () {
            navDesktop.classList.toggle("active");

            // Cambia el icono de barras (☰) a una equis (✕) al abrir
            if (icon) {
                if (icon.classList.contains("fa-bars")) {
                    icon.classList.remove("fa-bars");
                    icon.classList.add("fa-xmark");
                } else {
                    icon.classList.remove("fa-xmark");
                    icon.classList.add("fa-bars");
                }
            }
        });

        // 2. COMPORTAMIENTO AL PULSAR EN "COLECCIONES" O "NOSOTROS"
        menuLinks.forEach(function (link) {
            link.addEventListener("click", function () {
                // Quitamos la clase para que la cortina suba
                navDesktop.classList.remove("active");

                // Forzamos que el icono vuelva a ser el de las tres barras (☰)
                if (icon) {
                    icon.classList.remove("fa-xmark");
                    icon.classList.add("fa-bars");
                }

            });
        });
    }
});
//Esto hace que desaparezca en 3 seg la alerta
document.addEventListener("DOMContentLoaded", function () {
    setTimeout(function () {
        const mensaje = document.getElementById("mensaje");
        const mensajeError = document.getElementById('mensajeError')
        if (mensaje) {
            mensaje.style.display = "none";
        }
        if (mensajeError) {
            mensajeError.style.display = "none";
        }
    }, 3000);
});

// funciones para el carrusel de fotos que interactuen con las flechas
function crearCarrusel(selectorCards, selectorPrev, selectorNext, visibles) {

    // En móvil no hacemos nada
    if (window.innerWidth <= 900) {
        return;
    }

    const cards = document.querySelectorAll(selectorCards);
    const prev = document.querySelector(selectorPrev);
    const next = document.querySelector(selectorNext);

    if (!cards.length || !prev || !next) {
        return;
    }

    let inicio = 0;

    function mostrar() {

        // Ocultamos todas
        cards.forEach(card => {
            card.style.display = "none";
        });

        // Mostramos solo las necesarias
        for (let i = inicio; i < inicio + visibles && i < cards.length; i++) {
            cards[i].style.display = "";
        }

        // Activar / desactivar botones
        prev.disabled = inicio === 0;
        next.disabled = inicio + visibles >= cards.length;
    }

    mostrar();

    next.addEventListener("click", function () {

        if (inicio + visibles < cards.length) {
            inicio += visibles;
            mostrar();
        }

    });

    prev.addEventListener("click", function () {

        if (inicio - visibles >= 0) {
            inicio -= visibles;
            mostrar();
        }

    });
}
//Llamada al metodo del carrusel
document.addEventListener("DOMContentLoaded", function () {

    crearCarrusel(
        ".collection-card",
        ".prev-btn",
        ".next-btn",
        3
    );

    crearCarrusel(
        ".product-card",
        ".products-prev",
        ".products-next",
        4
    );

});

//Ventana emergente sobre nosotros
const abrir = document.getElementById("abrirHistoria");

const modal = document.getElementById("modalHistoria");

const cerrar = document.querySelector(".cerrar");
console.log("abrir:", abrir);
console.log("modal:", modal);
console.log("cerrar:", cerrar);
abrir.addEventListener("click",function(e){

    e.preventDefault();

    modal.style.display="flex";

});

cerrar.addEventListener("click",function(){

    modal.style.display="none";

});

window.addEventListener("click",function(e){

    if(e.target===modal){

        modal.style.display="none";

    }

});
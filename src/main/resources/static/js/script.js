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
        if (mensaje) {
            mensaje.style.display = "none";
        }
    }, 3000);
});
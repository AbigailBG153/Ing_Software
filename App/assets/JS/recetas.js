function toggleDropdown() {
    var dropdownContent = document.getElementById("dropdownContent");
    dropdownContent.classList.toggle("show");
}

document.querySelector(".notification-btn").addEventListener("click", function() {
    
});
document.getElementById("barra-tiempo").addEventListener("input", function() {
    var minutos = this.value;
    document.getElementById("minutos").value = minutos;
});
document.addEventListener('DOMContentLoaded', function() {
    const recetasContainer = document.querySelector('.recetas-container');
    const recetas = document.querySelectorAll('.receta');
    const flechaIzquierda = document.querySelector('.flecha-izquierda');
    const flechaDerecha = document.querySelector('.flecha-derecha');
    let indiceVisible = 0;

    function mostrarRecetas() {
        recetas.forEach((receta, index) => {
            if (index >= indiceVisible && index < indiceVisible + 3) {
                receta.style.display = 'block';
            } else {
                receta.style.display = 'none';
            }
        });
    }

    function avanzarRecetas() {
        if (indiceVisible < recetas.length - 3) {
            indiceVisible++;
            mostrarRecetas();
        }
    }

    function retrocederRecetas() {
        if (indiceVisible > 0) {
            indiceVisible--;
            mostrarRecetas();
        }
    }

    flechaDerecha.addEventListener('click', avanzarRecetas);
    flechaIzquierda.addEventListener('click', retrocederRecetas);

    mostrarRecetas(); 
});

document.addEventListener('DOMContentLoaded', function() {
    const contenedorOpciones = document.querySelector('.que-cocinar .opciones');
    const flechaIzquierda = document.querySelector('.que-cocinar .flecha-izquierda');
    const flechaDerecha = document.querySelector('.que-cocinar .flecha-derecha');
    let indiceVisible = 0;

    function mostrarOpciones() {
        const opciones = Array.from(contenedorOpciones.children);
        opciones.forEach((opcion, index) => {
            if (index >= indiceVisible && index < indiceVisible + 7) {
                opcion.style.display = 'block';
            } else {
                opcion.style.display = 'none';
            }
        });
    }

    function avanzarOpciones() {
        const totalOpciones = contenedorOpciones.children.length;
        if (indiceVisible < totalOpciones - 7) {
            indiceVisible++;
            mostrarOpciones();
        }
    }

    function retrocederOpciones() {
        if (indiceVisible > 0) {
            indiceVisible--;
            mostrarOpciones();
        }
    }

    flechaDerecha.addEventListener('click', avanzarOpciones);
    flechaIzquierda.addEventListener('click', retrocederOpciones);

    mostrarOpciones();
});
document.addEventListener('DOMContentLoaded', function() {
    const recetasContainer = document.querySelector('.ver-recetas .recetas-container');
    const recetas = document.querySelectorAll('.ver-recetas .receta');
    const flechaIzquierda = document.querySelector('.ver-recetas .flecha-izquierda');
    const flechaDerecha = document.querySelector('.ver-recetas .flecha-derecha');
    let indiceVisible = 0;

    function mostrarRecetas() {
        recetas.forEach((receta, index) => {
            if (index >= indiceVisible && index < indiceVisible + 4) {
                receta.style.display = 'block';
            } else {
                receta.style.display = 'none';
            }
        });
    }

    function avanzarRecetas() {
        if (indiceVisible < recetas.length - 4) {
            indiceVisible++;
            mostrarRecetas();
        }
    }

    function retrocederRecetas() {
        if (indiceVisible > 0) {
            indiceVisible--;
            mostrarRecetas();
        }
    }

    flechaDerecha.addEventListener('click', avanzarRecetas);
    flechaIzquierda.addEventListener('click', retrocederRecetas);

    mostrarRecetas(); 
});
window.addEventListener('scroll', function() {
    var sidebar = document.querySelector('.sidebar');
    var footer = document.querySelector('.footer-container');
    var sidebarPosition = sidebar.getBoundingClientRect();
    var footerPosition = footer.getBoundingClientRect();
    
    var sidebarHeight = sidebar.offsetHeight;
    
    var windowHeight = window.innerHeight;
    
    var footerTop = footerPosition.top - windowHeight;
    
    var sidebarTop = sidebarPosition.top;
    
    var scrollY = window.scrollY || window.pageYOffset;
    
    var documentHeight = Math.max(
        document.body.scrollHeight, document.documentElement.scrollHeight,
        document.body.offsetHeight, document.documentElement.offsetHeight,
        document.body.clientHeight, document.documentElement.clientHeight
    );
    
    if (sidebarTop + sidebarHeight >= footerTop) {
        sidebar.style.position = 'absolute';
        sidebar.style.bottom = '0';
    } else {
        sidebar.style.position = 'fixed';
        sidebar.style.bottom = '';
    }
});

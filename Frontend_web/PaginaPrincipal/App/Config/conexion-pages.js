
$(document).ready(function() {
    // Función para mostrar el contenido correspondiente al elemento seleccionado
    function showContent(page) {
        // Ocultar todos los elementos main
        $("main").hide();
        // Mostrar el contenido correspondiente al enlace seleccionado dentro del main adecuado
        $("#" + page).load("/App/Pages/"+page + ".html").show();
    }
    // Función para manejar el clic en los enlaces del menú lateral
    $(".side-menu li a").on("click", function() {
        var page = $(this).attr("href").substring(1); // Obtener el ID del elemento seleccionado
       // Mostrar el contenido correspondiente al enlace seleccionado
        showContent(page);
        // Desactivar la clase active de todos los elementos del menú lateral
        $(".side-menu li").removeClass("active");
        // Activar la clase active para el elemento seleccionado
        $(this).parent().addClass("active");
        return false; // Evitar que el navegador siga el enlace
    });
    // Verificar si algún elemento li del menú lateral tiene la clase active al cargar la página
    var activeMenuItem = $(".side-menu li.active a").attr("href").substring(1); // Obtener el ID del elemento activo
    // Mostrar el contenido correspondiente al elemento activo
    showContent(activeMenuItem);
});

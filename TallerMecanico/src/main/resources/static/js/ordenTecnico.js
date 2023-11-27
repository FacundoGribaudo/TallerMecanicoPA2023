console.log("vinculado orden tecnico.js");
const titulo = document.getElementById("tecnicoDeConsulta");

var nombre = localStorage.getItem("nombreTecnico").toUpperCase();
var apellido = localStorage.getItem("apellidoTecnico").toUpperCase();

titulo.textContent = "HISTORIAL DE ORDENES DE "+nombre+" "+apellido;

document.addEventListener("DOMContentLoaded", function() {
    // Obtener el valor actual de estadoFilter de la URL
    var estadoFilter = new URLSearchParams(window.location.search).get("estadoFilter");

    // Establecer el valor seleccionado en el select
    document.getElementById("estadoFilter").value = estadoFilter || "";
});

function obtenerIdTecnicoDesdeURL() {
    // Obtener la URL actual
    var url = window.location.href;

    // Buscar el patrón "/ordenesTecnico/{id_tecnico}"
    var match = url.match(/\/ordenesTecnico\/(\d+)/);

    // Si se encuentra una coincidencia, devolver el valor del grupo de captura
    return match ? match[1] : null;
}

function filtrarPorEstado() {
    var estadoFilter = document.getElementById("estadoFilter").value;
    
    // Obtener el id_tecnico dinámicamente desde la URL
    var id_tecnico = obtenerIdTecnicoDesdeURL();

    // Verificar si la opción seleccionada es "Seleccionar Estado"
    if (estadoFilter === "") {
        window.location.href = "/ordenesTecnico/" + id_tecnico;
    } else {
        window.location.href = "/ordenesTecnico/" + id_tecnico + "?estadoFilter=" + estadoFilter;
    }
}
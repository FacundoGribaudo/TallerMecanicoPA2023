console.log("vinculado detalleOrden.js");


$(document).ready( function () {
    $('#miTabla').DataTable({
        "scrollX": true,  // Habilita la barra de desplazamiento horizontal si es necesario
        "scrollY": 300,   // Define una altura máxima para la tabla si es necesario
        "paging": false,   // Desactiva la paginación si no la necesitas
        "language": {
            "url": "https://cdn.datatables.net/plug-ins/1.10.25/i18n/Spanish.json"  // Configura el idioma en español
        }
    });
});

function limitarPorcentajeInput(input) {
    if (input.value > 100) {
        input.value = 100; // Establece el valor máximo permitido
    }
}

function limitarMinutosInput(input) {
    if (input.value > 59) {
        input.value = 59; // Establece el valor máximo permitido
    }
}

window.addEventListener('DOMContentLoaded', (event) => {
    // Obten la última columna
    const lastColumn = document.querySelector('.fixed-column');
    
    // Calcula la anchura de la última columna
    const lastColumnWidth = lastColumn.offsetWidth;
    
    // Aplica la anchura a la última columna
    lastColumn.style.width = lastColumnWidth + 'px';
});


// --- Calcular precio por horas y minutos ---
// Obtener referencias a los elementos del DOM por su atributo "name"
var precioPorHoraInputs = document.getElementsByName("precioHora");
var horasTrabajadasInputs = document.getElementsByName("horas");
var minutosTrabajadosInputs = document.getElementsByName("minutos");
var precioServicioInputs = document.getElementsByName("precio");
var descuentoInputs = document.getElementsByName("%descuento");
var montodescuentoInputs = document.getElementsByName("montodescuento");
var impuestoInputs = document.getElementsByName("%impuesto");
var montoImpuestosInputs = document.getElementsByName("montoImpuestos");
var totalInputs = document.getElementsByName("totalServicio");
var totalDescuentosInputs = document.getElementsByName("totalDescuentos");
var totalImpuestosInputs = document.getElementsByName("totalImpuestos");
var subtotalInputs = document.getElementsByName("subtotal");
var totalHorasInputs = document.getElementsByName("horasTrabajadas");
var totalMinutosInputs = document.getElementsByName("minutosTrabajados");

// Agregar event listeners para detectar cambios en los campos de entrada
for (var i = 0; i < precioPorHoraInputs.length; i++) {
    horasTrabajadasInputs[i].addEventListener("input", calcularTotal);
    minutosTrabajadosInputs[i].addEventListener("input", calcularTotal);
}

// Función para calcular el total en tiempo real
function calcularTotal() {
    for (var i = 0; i < precioPorHoraInputs.length; i++) {
        var precioHora = parseFloat(precioPorHoraInputs[i].value) || 0;
        var horas = parseFloat(horasTrabajadasInputs[i].value) || 0;
        var minutos = parseFloat(minutosTrabajadosInputs[i].value) || 0;
        var descuento = parseFloat(descuentoInputs[i].value) || 0;
        var impuesto = parseFloat(impuestoInputs[i].value) || 0;

        var totalServicio = precioHora * (horas + minutos / 60);
        var descuentoServicio = totalServicio * (descuento / 100);
        var impuestoServicio = totalServicio * (impuesto / 100);

        var total = totalServicio - descuentoServicio + impuestoServicio;
        precioServicioInputs[i].value = totalServicio.toFixed(2);
        totalInputs[i].value = total.toFixed(2);
        montodescuentoInputs[i].value = descuentoServicio.toFixed(2);
        montoImpuestosInputs[i].value = impuestoServicio.toFixed(2);
    

    }
    // Calcular el subtotal
    var subtotal = 0;
    for (var i = 0; i < totalInputs.length; i++) {
        subtotal += parseFloat(totalInputs[i].value) || 0;
    }
    subtotalInputs[0].value = subtotal.toFixed(2);

    // Calcular el total de descuentos
    var totalDescuentos = 0;
    for (var i = 0; i < montodescuentoInputs.length; i++) {
        totalDescuentos += parseFloat(montodescuentoInputs[i].value) || 0;
    }
    totalDescuentosInputs[0].value = totalDescuentos.toFixed(2);

    // Calcular el total de impuestos
    var totalImpuestos = 0;
    for (var i = 0; i < montoImpuestosInputs.length; i++) {
        totalImpuestos += parseFloat(montoImpuestosInputs[i].value) || 0;
    }
    totalImpuestosInputs[0].value = totalImpuestos.toFixed(2);

    // Calcular el total de horas y minutos trabajados
    var totalHoras = 0;
    var totalMinutos = 0;

    for (var i = 0; i < horasTrabajadasInputs.length; i++) {
        totalHoras += parseFloat(horasTrabajadasInputs[i].value) || 0;
        totalMinutos += parseFloat(minutosTrabajadosInputs[i].value) || 0;
    }

    // Convertir los minutos a horas si es necesario
    if (totalMinutos >= 60) {
        var horasExtras = Math.floor(totalMinutos / 60);
        totalHoras += horasExtras;
        totalMinutos %= 60;
    }

    // Mostrar el total de horas y minutos trabajados
    totalHorasInputs[0].value = totalHoras;
    totalMinutosInputs[0].value = totalMinutos;
}

// Calcular los totales iniciales
calcularTotal();


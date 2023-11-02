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


// --- Calcular precio por horas y minutos ---
// Obtener referencias a los elementos del DOM por su atributo "name"
var precioPorHoraInputs = document.getElementsByName("precioHora");
var horasTrabajadasInputs = document.getElementsByName("horas");
var minutosTrabajadosInputs = document.getElementsByName("minutos");
var subtotalInputs = document.getElementsByName("precio");
var descuentoInputs = document.getElementsByName("%descuento");
var montodescuentoInputs = document.getElementsByName("montodescuento");
var impuestoInputs = document.getElementsByName("%impuesto");
var montoImpuestosInputs = document.getElementsByName("montoImpuestos");
var totalInputs = document.getElementsByName("totalServicio");

// Agregar event listeners para detectar cambios en los campos de entrada
for (var i = 0; i < precioPorHoraInputs.length; i++) {
    precioPorHoraInputs[i].addEventListener("input", calcularTotal);
    horasTrabajadasInputs[i].addEventListener("input", calcularTotal);
    minutosTrabajadosInputs[i].addEventListener("input", calcularTotal);
    descuentoInputs[i].addEventListener("input", calcularTotal);
    impuestoInputs[i].addEventListener("input", calcularTotal);
}

// Función para calcular el total en tiempo real
function calcularTotal() {
    for (var i = 0; i < precioPorHoraInputs.length; i++) {
        var precioHora = parseFloat(precioPorHoraInputs[i].value) || 0;
        var horas = parseFloat(horasTrabajadasInputs[i].value) || 0;
        var minutos = parseFloat(minutosTrabajadosInputs[i].value) || 0;
        var descuento = parseFloat(descuentoInputs[i].value) || 0;
        var impuesto = parseFloat(impuestoInputs[i].value) || 0;

        var subtotal = precioHora * (horas + minutos / 60);
        var descuentoAmount = subtotal * (descuento / 100);
        var impuestoAmount = subtotal * (impuesto / 100);

        var total = subtotal - descuentoAmount + impuestoAmount;
        subtotalInputs[i].value = subtotal.toFixed(2);
        totalInputs[i].value = total.toFixed(2);
        montodescuentoInputs[i].value = descuentoAmount.toFixed(2);
        montoImpuestosInputs[i].value = impuestoAmount.toFixed(2);
    }
}

// Calcular los totales iniciales
calcularTotal();



window.addEventListener('DOMContentLoaded', (event) => {
    // Obten la última columna
    const lastColumn = document.querySelector('.fixed-column');
    
    // Calcula la anchura de la última columna
    const lastColumnWidth = lastColumn.offsetWidth;
    
    // Aplica la anchura a la última columna
    lastColumn.style.width = lastColumnWidth + 'px';
});
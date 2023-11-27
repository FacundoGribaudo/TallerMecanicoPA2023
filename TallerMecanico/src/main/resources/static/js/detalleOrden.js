console.log("vinculado detalleOrden.js");

function actualizarOrden(){
    document.getElementById("addUserForm").submit();
}

$(document).ready(function () {
    $('#miTabla').DataTable({
        "scrollX": true,  // Habilita la barra de desplazamiento horizontal si es necesario
        "scrollY": 300,   // Define una altura máxima para la tabla si es necesario
        "paging": false,   // Desactiva la paginación si no la necesitas
        "language": {
            "url": "https://cdn.datatables.net/plug-ins/1.10.25/i18n/Spanish.json"  // Configura el idioma en español
        }
    });
});


window.addEventListener('DOMContentLoaded', (event) => {
    // Obten la última columna
    const lastColumn = document.querySelector('.fixed-column');
    
    // Calcula la anchura de la última columna
    const lastColumnWidth = lastColumn.offsetWidth;
    
    // Aplica la anchura a la última columna
    lastColumn.style.width = lastColumnWidth + 'px';

    //Llamada a funciones para que se ejecuten al iniciar
    calcularTotal();
    calcularMontoDesdePorcentaje(porcentajeImpuestoAgregadoInput,montoImpuestoAgregadoInput );
    calcularMontoDesdePorcentaje(porcentajeDescuentoAgregadoInput,montoDescuentoAgregadoInput );
    calcularMontoDesdePorcentaje(porcentajeAumentoAgregadoInput,montoAumentoAgregadoInput );
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
var precioServicioInputs = document.getElementsByName("precio");
var descuentoInputs = document.getElementsByName("%descuento");
var montodescuentoInputs = document.getElementsByName("montodescuento");
var impuestoInputs = document.getElementsByName("%impuesto");
var montoImpuestosInputs = document.getElementsByName("montoImpuestos");
var totalServicioInputs = document.getElementsByName("totalServicio");
var totalDescuentosInputs = document.getElementsByName("totalDescuentos");
var totalImpuestosInputs = document.getElementsByName("totalImpuestos");
var subtotalInputs = document.getElementsByName("subtotal");
var totalHorasInputs = document.getElementsByName("horasTrabajadas");
var totalMinutosInputs = document.getElementsByName("minutosTrabajados");

var porcentajeDescuentoAgregadoInput = document.getElementsByName("%descuentoAgregado")[0];
var montoDescuentoAgregadoInput = document.getElementsByName("montoDescuentoAgregado")[0];
var porcentajeImpuestoAgregadoInput = document.getElementsByName("%impuestoAgregado")[0];
var montoImpuestoAgregadoInput = document.getElementsByName("montoImpuestoAgregado")[0];
var porcentajeAumentoAgregadoInput = document.getElementsByName("%aumentoAgregado")[0];
var montoAumentoAgregadoInput = document.getElementsByName("montoAumentoAgregado")[0];

// Agregar event listeners para detectar cambios en los campos de entrada
for (var i = 0; i < precioPorHoraInputs.length; i++) {
    horasTrabajadasInputs[i].addEventListener("input", function () {
        calcularTotal();
        //Calculo Impuestos
        calcularMontoDesdePorcentaje(porcentajeImpuestoAgregadoInput,montoImpuestoAgregadoInput);
        calcularPorcentajeDesdeMonto(montoImpuestoAgregadoInput,porcentajeImpuestoAgregadoInput);
        //Calculo Descuentos
        calcularMontoDesdePorcentaje(porcentajeDescuentoAgregadoInput,montoDescuentoAgregadoInput);
        calcularPorcentajeDesdeMonto(montoDescuentoAgregadoInput,porcentajeDescuentoAgregadoInput);
        //Calculo Aumento
        calcularMontoDesdePorcentaje(porcentajeAumentoAgregadoInput,montoAumentoAgregadoInput);
        calcularPorcentajeDesdeMonto(montoAumentoAgregadoInput,porcentajeAumentoAgregadoInput);
    });
    minutosTrabajadosInputs[i].addEventListener("input", function () {
        calcularTotal();
        //Calculo Impuestos
        calcularMontoDesdePorcentaje(porcentajeImpuestoAgregadoInput,montoImpuestoAgregadoInput);
        calcularPorcentajeDesdeMonto(montoImpuestoAgregadoInput,porcentajeImpuestoAgregadoInput);
        //Calculo Descuentos
        calcularMontoDesdePorcentaje(porcentajeDescuentoAgregadoInput,montoDescuentoAgregadoInput);
        calcularPorcentajeDesdeMonto(montoDescuentoAgregadoInput,porcentajeDescuentoAgregadoInput);
        //Calculo Aumento
        calcularMontoDesdePorcentaje(porcentajeAumentoAgregadoInput,montoAumentoAgregadoInput);
        calcularPorcentajeDesdeMonto(montoAumentoAgregadoInput,porcentajeAumentoAgregadoInput);
    });
}

// Agregar event listener para el porcentaje de impuesto agregado
porcentajeImpuestoAgregadoInput.addEventListener("input", function () {
    calcularMontoDesdePorcentaje(porcentajeImpuestoAgregadoInput, montoImpuestoAgregadoInput);
});
// Agregar event listener para el monto de impuesto agregado
montoImpuestoAgregadoInput.addEventListener("input", function () {
    calcularPorcentajeDesdeMonto(montoImpuestoAgregadoInput, porcentajeImpuestoAgregadoInput);
});

// Agregar event listener para el porcentaje de descuento agregado
porcentajeDescuentoAgregadoInput.addEventListener("input", function () {
    calcularMontoDesdePorcentaje(porcentajeDescuentoAgregadoInput, montoDescuentoAgregadoInput);
});
// Agregar event listener para el monto de descuento agregado
montoDescuentoAgregadoInput.addEventListener("input", function () {
    calcularPorcentajeDesdeMonto(montoDescuentoAgregadoInput, porcentajeDescuentoAgregadoInput);
});

// Agregar event listener para el porcentaje de aumento agregado
porcentajeAumentoAgregadoInput.addEventListener("input", function () {
    calcularMontoDesdePorcentaje(porcentajeAumentoAgregadoInput, montoAumentoAgregadoInput);
});
// Agregar event listener para el monto de aumento agregado
montoAumentoAgregadoInput.addEventListener("input", function () {
    calcularPorcentajeDesdeMonto(montoAumentoAgregadoInput, porcentajeAumentoAgregadoInput);
});

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
        totalServicioInputs[i].value = total.toFixed(2);
        montodescuentoInputs[i].value = descuentoServicio.toFixed(2);
        montoImpuestosInputs[i].value = impuestoServicio.toFixed(2);


    }
    // Calcular el subtotal
    var subtotal = 0;
    for (var i = 0; i < totalServicioInputs.length; i++) {
        subtotal += parseFloat(totalServicioInputs[i].value) || 0;
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

    // Calcular el total
    var montoImpuestoAgregado = parseFloat(montoImpuestoAgregadoInput.value) || 0;
    var montoDescuentoAgregado = parseFloat(montoDescuentoAgregadoInput.value) || 0;
    var montoAumentoAgregado = parseFloat(montoAumentoAgregadoInput.value) || 0;

    var total = subtotal + montoImpuestoAgregado - montoDescuentoAgregado + montoAumentoAgregado;
    // Asumiendo que tienes un campo en el DOM con nombre "total"
    var totalInput = document.getElementsByName("total")[0];
    totalInput.value = total.toFixed(2);
}

// Función para calcular el monto desde el porcentaje
function calcularMontoDesdePorcentaje(porcentajeAgregado, monto) {
    var porcentaje = parseFloat(porcentajeAgregado.value) || 0;
    var subtotal = parseFloat(subtotalInputs[0].value) || 0;

    var montoCalculado = (porcentaje / 100) * subtotal;
    monto.value = montoCalculado.toFixed(2);

    calcularTotal();
}
// Función para calcular el porcentaje de impuesto desde el monto de impuesto agregado
function calcularPorcentajeDesdeMonto(montoAgregado, porcentaje) {
    var monto = parseFloat(montoAgregado.value) || 0;
    var subtotal = parseFloat(subtotalInputs[0].value) || 0;

    var porcentajeCalculado = (monto / subtotal) * 100;
    porcentaje.value = porcentajeCalculado.toFixed(2);

    calcularTotal();
}


// Descargar Orden
function descargarOrden() {
    console.log("se descarga la orden");

    const id = document.getElementById("botonDescargar").value;
    document.getElementById("botonDescargar").textContent = "Descargando...";
    

    const { jsPDF } = window.jspdf;
	const doc = new jsPDF();
    const element = document.getElementById("containerOrden");

    html2canvas(element, { scale: 8 }).then(function (canvas) {
        // Convertir la imagen capturada en datos URI
        const imgData = canvas.toDataURL("image/jpeg", 0.7); // Cambiar a formato JPEG y ajustar calidad

        // Agregar la imagen al PDF

        // En base a A4
        var anchoPagina = 210; // Ancho de la página en unidades (por ejemplo, en mm)

        // Calcular las coordenadas para centrar la imagen

        // Luego, puedes agregar la imagen al PDF centrada en (x, y)
        // X,Y,ancho,alto --> si el alto o ancho es 0 se ajusta a la otra medida automaticamente
        doc.addImage(imgData, "JPEG", 5, 20, 200, 0);

        // Guardar o mostrar el PDF, según tus necesidades
        // Por ejemplo, para descargar el PDF:
        doc.save("DetalleOrden_"+id+".pdf");
        document.getElementById("botonDescargar").textContent = "Descargar Orden";
    });
}
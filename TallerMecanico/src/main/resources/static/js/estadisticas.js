// Descargar Orden
function descargarEstadisticas() {
    console.log("Descargando la estadistica");

    // const id = document.getElementById("botonDescargar").value;
    document.getElementById("botonDescargar").textContent = "Descargando...";
    

    const { jsPDF } = window.jspdf;
	const doc = new jsPDF();
    const element = document.getElementById('contenedorImprimir');

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
        doc.save("EstadisticaServicio"+".pdf");
        document.getElementById("botonDescargar").textContent = "Descargar Orden";
    });
}

// Descargar Orden
function descargarEstadisticasOT() {
    console.log("Descargando la estadistica");

    // const id = document.getElementById("botonDescargar").value;
    document.getElementById("botonDescargar").textContent = "Descargando...";
    

    const { jsPDF } = window.jspdf;
	const doc = new jsPDF();
    const element = document.getElementById('contenedorImprimir');

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
        doc.save("EstadisticasOrdenTrabajo"+".pdf");
        document.getElementById("botonDescargar").textContent = "Descargar Orden";
    });
}
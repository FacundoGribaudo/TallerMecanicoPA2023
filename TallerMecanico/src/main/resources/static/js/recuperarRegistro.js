console.log("vinculado recuperar registro");

function confirmarRecuperar() {

    return confirm('¿Estás seguro de que quieres recuperar este registro?');
}

function vaciarHistorial() {
    return confirm('¿Estás seguro de vaciar por completo el historial de registros?');

}

function verificarBuscarRegEliminado(e) {
    e.preventDefault();

    const nombreBuscar = document.getElementById("inputBuscarNombre").value;
    const fechaDesde = document.getElementById("inputBuscarDesde").value;
    const fechaHasta = document.getElementById("inputBuscarHasta").value;

    console.log(nombreBuscar,fechaDesde,fechaHasta);

    if (nombreBuscar == "" && fechaDesde == "" && fechaHasta == "") {
        Swal.fire(
            'Error!',
            'No puede realizar una búsqueda vacía',
            'error'
        );
        return
    }else if(nombreBuscar != "" && fechaDesde != "" && fechaHasta == ""){
        Swal.fire(
            'Error!',
            'No ingresó correctamente los campos de búsqueda',
            'error'
        );
        return
    }else if(nombreBuscar != "" && fechaDesde == "" && fechaHasta != ""){
        Swal.fire(
            'Error!',
            'No ingresó correctamente los campos de búsqueda',
            'error'
        );
        return
    }else if(nombreBuscar == "" && fechaDesde != "" && fechaHasta == ""){
        Swal.fire(
            'Error!',
            'No ingresó correctamente los campos de búsqueda',
            'error'
        );
        return
    }else if(nombreBuscar == "" && fechaDesde == "" && fechaHasta != ""){
        Swal.fire(
            'Error!',
            'No ingresó correctamente los campos de búsqueda',
            'error'
        );
        return
    }
    else {
        document.getElementById("formRegEliminado").submit();
    }

}
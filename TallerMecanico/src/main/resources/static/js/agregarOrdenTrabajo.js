console.log("vinculado.js");

function agregarOrdenTrabajo(e){
    e.preventDefault();

    const vehiculo = document.getElementById("vehiculoServicio").value;
    const servicio = document.getElementById("servicioRealizar").value;

    if (vehiculo == "Vehiculo" || servicio == "Servicios"){
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    }else{
        document.getElementById("addUserForm").submit();
    }

}
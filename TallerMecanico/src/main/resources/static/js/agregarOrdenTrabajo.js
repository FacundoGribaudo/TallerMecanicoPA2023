console.log("vinculado.js");

function agregarOrdenTrabajo(e){
    e.preventDefault();

    const vehiculoSelect = document.getElementById("vehiculoServicio");
    const servicioSelect = document.getElementById("serviciosRealizar");

    // Verifica que los elementos existan antes de acceder a sus propiedades
    if (vehiculoSelect && servicioSelect) {
        const vehiculo = vehiculoSelect.options[vehiculoSelect.selectedIndex].value;
        const servicios = [...servicioSelect.options].filter(option => option.selected).map(option => option.value);
    
        if (vehiculo == "Vehiculo" || servicios.length === 0){
            Swal.fire(
                'Error!',
                'Por favor complete todos los campos',
                'error'
            );
            return
        }else{
            document.getElementById("addUserForm").submit();
        }
    } else {
        console.error('No se encontraron los elementos vehiculoServicio o serviciosRealizar.');
    }
}
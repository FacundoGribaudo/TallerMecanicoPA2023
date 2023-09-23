console.log("vinculado tecnico.js");

function agregarTecnico(event){
    event.preventDefault();

    const nombreTecnico = document.getElementById("nombreTecnico").value;
    const apellidoTecnico = document.getElementById("apellidoTecnico").value;
    const telefonoTecnico = document.getElementById("telefonoTecnico").value;
    const legajoTecnico = document.getElementById("legajoTecnico").value;
    const estadoTecnico = document.getElementById("estadoTecnico").value;
    const vehiculoTecnico = document.getElementById("vehiculoTecnico").value;

    if(nombreTecnico == "" || apellidoTecnico == "" || telefonoTecnico == "" || legajoTecnico == "" ||estadoTecnico == "Estado" || vehiculoTecnico == "Vehiculo"){
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    }else if(isNaN(telefonoTecnico)){
        Swal.fire(
            'Error!',
            'El telefono debe ser un número',
            'error'
        );
        return
    }else if(isNaN(legajoTecnico)){
        Swal.fire(
            'Error!',
            'El legajo debe ser un número',
            'error'
        );
        return
    }
    else{
        document.getElementById("addUserForm").submit();
    }

}

const params = new URLSearchParams(window.location.search);
const mensaje = params.get('mensaje');

// Verificar si el valor del parámetro 'mensaje' es igual a 'tecnicoRepetido' y mostrar una alerta
if (mensaje === 'tecnicoRepetido') {
    Swal.fire('Error!', 'Ya existe un Técnico registrado con este Legajo', 'error');
}
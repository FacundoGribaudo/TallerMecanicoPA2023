console.log("vinculado clientes.js");

function agregarCliente(event) {
    event.preventDefault(); // Evita el envío del formulario por defecto

    const nombreCliente = document.getElementById("nombreCliente").value;
    const apellidoCliente = document.getElementById("apellidoCliente").value;
    const dniCliente = document.getElementById("dniCliente").value;
    const telefonoCliente = document.getElementById("telefonoCliente").value;
    const localidadCliente = document.getElementById("localidadCliente").value;
    const nroLicencia = document.getElementById("nroLicencia").value;
    const vtoLicencia = document.getElementById("vtoLicencia").value;

    if (nombreCliente == "" || apellidoCliente == "" || dniCliente == "" || telefonoCliente == "" || localidadCliente == "" || nroLicencia == "" || vtoLicencia == "") {
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    } else if (isNaN(dniCliente) || isNaN(telefonoCliente)) {
        Swal.fire(
            'Error!',
            'El DNI y/o número de teléfono debe ser un número',
            'error'
        );
        return
    } else {
        document.getElementById("addUserForm").submit();
    }
}


const params = new URLSearchParams(window.location.search);
const mensaje = params.get('mensaje');

// Verificar si el valor del parámetro 'mensaje' es igual a 'false' y mostrar una alerta
if (mensaje == 'false') {
    Swal.fire(
        'Error!',
        'Este cliente está asociado a un vehículo registrado, no es posible borrar',
        'error'
    );
}
// Verificar si el valor del parámetro 'mensaje' es igual a 'clienteRepetido' y mostrar una alerta
if (mensaje === 'clienteRepetido') {
    Swal.fire('Error!', 'Ya existe un Cliente registrado con este DNI', 'error');
}

// Eliminar el parámetro 'mensaje' de la URL
params.delete('mensaje');
const newUrl = window.location.pathname + (params.toString() ? '?' + params.toString() : '');
history.replaceState({}, document.title, newUrl);
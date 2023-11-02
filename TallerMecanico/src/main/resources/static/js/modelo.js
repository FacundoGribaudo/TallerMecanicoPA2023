console.log("vinculado modelo.js");

function agregarModelo(event) {
    event.preventDefault(); // Evita el envío del formulario por defecto

    const modelo = document.getElementById("modelo").value;
    const estado = document.getElementById("estadoModelo").value;
    const marcaModelo = document.getElementById("marcaModelo").value;

    if (modelo == "" || estado == "Estado" || marcaModelo == "Marca") {
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    } else {
        document.getElementById("addUserForm").submit();
    }
}

// Obtener la URL actual
const params = new URLSearchParams(window.location.search);
const mensaje = params.get('mensaje');

// Verificar si el valor del parámetro 'mensaje' es igual a 'false' y mostrar una alerta
if (mensaje === 'false') {
    Swal.fire(
        'Error!',
        'Este Modelo está asociado a un vehículo registrado, no es posible borrar',
        'error'
    );
}

// Verificar si el valor del parámetro 'mensaje' es igual a 'modeloRepetido' y mostrar una alerta
if (mensaje === 'modeloRepetido') {
    Swal.fire('Error!', 'Ya existe este Modelo registrado para esta marca', 'error');
}

// Eliminar el parámetro 'mensaje' de la URL después de mostrar la alerta
params.delete('mensaje');
const newUrl = window.location.pathname + (params.toString() ? '?' + params.toString() : '');
history.replaceState({}, document.title, newUrl);

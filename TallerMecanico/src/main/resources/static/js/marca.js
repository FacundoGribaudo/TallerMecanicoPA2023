console.log("vinculado marca.js");
function validarFormulario(event) {
    event.preventDefault(); // Evita el envío del formulario por defecto

    const marca = document.getElementById("inputMarca").value;
    const estado = document.getElementById("inputState").value;
    if (marca == "" || !isNaN(marca)) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'La marca no puede ser un número ni estar vacía',
        })
        return;
    } else if (estado == "Estado") {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Debe seleccionar un estado para la marca',
        })
        return;
    }
    else {
        document.getElementById("addUserForm").submit();
    }
}

// Obtener la URL actual
const params = new URLSearchParams(window.location.search);
const mensaje = params.get('mensaje');

// Verificar si el valor del parámetro 'mensaje' es igual a 'noEliminar' y mostrar una alerta
if (mensaje === 'noEliminar') {
    Swal.fire(
        'Error!',
        'Esta marca está asociada a un vehículo registrado, no es posible borrar',
        'error'
    );
} else if (mensaje === 'eliminar') {
    Swal.fire(
        'Atención!',
        'La marca ha sido eliminada, junto con sus modelos registrados.',
        'warning'
    );
}

// Verificar si el valor del parámetro 'mensaje' es igual a 'marcaRepetida' y mostrar una alerta
if (mensaje === 'marcaRepetida') {
    Swal.fire('Error!', 'Ya existe esta Marca registrada', 'error');
}

// Eliminar el parámetro 'mensaje' de la URL después de mostrar la alerta
params.delete('mensaje');
const newUrl = window.location.pathname + (params.toString() ? '?' + params.toString() : '');
history.replaceState({}, document.title, newUrl);

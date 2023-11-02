console.log("vinculado servicios.js");

// Obtener el valor del parámetro 'mensaje'
const params = new URLSearchParams(window.location.search);
const mensaje = params.get('mensaje');

// Verificar si el valor del parámetro 'mensaje' es igual a 'servicioRepetido' y mostrar una alerta
if (mensaje === 'servicioRepetido') {
    Swal.fire(
        'Error!',
        'Este Servicio ya se encuentra registrado',
        'error'
    );
}

// Verificar si el valor del parámetro 'mensaje' es igual a 'false' y mostrar una alerta
if (mensaje == 'false') {
    Swal.fire(
        'Error!',
        'Este servicio está asociado a una orden de trabajo, no es posible borrar',
        'error'
    );
}

// Eliminar el parámetro 'mensaje' de la URL después de mostrar la alerta
params.delete('mensaje');
const newUrl = window.location.pathname + (params.toString() ? '?' + params.toString() : '');
history.replaceState({}, document.title, newUrl);

function agregarServicio(e) {
    e.preventDefault();

    const servicio = document.getElementById("nombreServicio").value;
    const descServicio = document.getElementById("descripcionServicio").value;

    if (servicio == "" || descServicio == "") {
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    } else if (!isNaN(servicio) || !isNaN(descServicio)) {
        Swal.fire(
            'Error!',
            'Los campos no pueden ser numéricos',
            'error'
        );
        return
    }
    else {

        document.getElementById("addUserForm").submit();
    }

}

function limitarPorcentajeInput(input) {
    if (input.value > 100) {
        input.value = 100; // Establece el valor máximo permitido
    }
}
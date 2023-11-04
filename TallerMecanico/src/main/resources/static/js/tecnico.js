console.log("vinculado tecnico.js");

function agregarTecnico(event) {
    event.preventDefault();

    const nombreTecnico = document.getElementById("nombreTecnico").value;
    const apellidoTecnico = document.getElementById("apellidoTecnico").value;
    const telefonoTecnico = document.getElementById("telefonoTecnico").value;
    const legajoTecnico = document.getElementById("legajoTecnico").value;
    const estadoTecnico = document.getElementById("estadoTecnico").value;

    if (nombreTecnico == "" || apellidoTecnico == "" || telefonoTecnico == "" || legajoTecnico == "" || estadoTecnico == "Estado") {
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    } else if (isNaN(telefonoTecnico)) {
        Swal.fire(
            'Error!',
            'El telefono debe ser un número',
            'error'
        );
        return
    } else if (isNaN(legajoTecnico)) {
        Swal.fire(
            'Error!',
            'El legajo debe ser un número',
            'error'
        );
        return
    }
    else {
        document.getElementById("addUserForm").submit();
    }

}

const params = new URLSearchParams(window.location.search);
const mensaje = params.get('mensaje');

// Verificar si el valor del parámetro 'mensaje' es igual a 'tecnicoRepetido' y mostrar una alerta
if (mensaje === 'tecnicoRepetido') {
    Swal.fire('Error!', 'Ya existe un Técnico registrado con este Legajo', 'error');
}

// Eliminar el parámetro 'mensaje' de la URL después de mostrar la alerta
params.delete('mensaje');
const newUrl = window.location.pathname + (params.toString() ? '?' + params.toString() : '');
history.replaceState({}, document.title, newUrl);

function limitarPorcentajeInput(input) {
    if (input.value > 100) {
        input.value = 100; // Establece el valor máximo permitido
    }
}

//tomo los valores del tecnico pasados por parametro
function datosTecnico(link) {

    var nombre = link.getAttribute("nombre");
    var apellido = link.getAttribute("apellido");

    localStorage.removeItem("nombreTecnico");
    localStorage.removeItem("apellidoTecnico");

    localStorage.setItem("nombreTecnico",nombre);
    localStorage.setItem("apellidoTecnico",apellido);
}
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
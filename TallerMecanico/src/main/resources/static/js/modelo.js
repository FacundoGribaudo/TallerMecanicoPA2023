console.log("vinculado modelo.js");

function agregarModelo(event){
    event.preventDefault(); // Evita el envío del formulario por defecto

    const modelo = document.getElementById("modelo").value;
    const estado = document.getElementById("estadoModelo").value;
    const marcaModelo = document.getElementById("marcaModelo").value;

    if (modelo == "" || estado == "Estado" || marcaModelo == "Marca"){
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

const params = new URLSearchParams(window.location.search);
const mensaje = params.get('mensaje');

// Verificar si el valor del parámetro 'mensaje' es igual a 'true' y mostrar una alerta
if (mensaje == 'false') {
    Swal.fire(
        'Error!',
        'Este Modelo está asociada a un vehículo registrado, no es posible borrar',
        'error'
    );
}

// Verificar si el valor del parámetro 'mensaje' es igual a 'modeloRepetida' y mostrar una alerta
if (mensaje === 'modeloRepetido') {
    Swal.fire('Error!', 'Ya existe este Modelo registrado para esta marca', 'error');
}
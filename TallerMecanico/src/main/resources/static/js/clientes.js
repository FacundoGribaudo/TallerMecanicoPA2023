console.log("vinculado clientes.js");

document.addEventListener("DOMContentLoaded", function () {
    var form = document.getElementById("formBuscar");

    form.addEventListener("keydown", function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
        }
    });
});

function agregarCliente(event){
    event.preventDefault(); // Evita el envío del formulario por defecto

    const nombreCliente = document.getElementById("nombreCliente").value;
    const apellidoCliente = document.getElementById("apellidoCliente").value;
    const dniCliente = document.getElementById("dniCliente").value;
    const telefonoCliente = document.getElementById("telefonoCliente").value;
    const localidadCliente = document.getElementById("localidadCliente").value;

    if (nombreCliente == "" || apellidoCliente == "" || dniCliente == "" || telefonoCliente == "" || localidadCliente == ""){
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    }else if(isNaN(dniCliente) || isNaN(telefonoCliente)){
        Swal.fire(
            'Error!',
            'El DNI y/o número de teléfono debe ser un número',
            'error'
        );
        return
    }else if(dniCliente.length < 8){
        Swal.fire(
            'Error!',
            'El DNI debe tener 8 dígitos',
            'error'
        );
        return
    }
    else{
        document.getElementById("addUserForm").submit();
    }
}

function validarBuscar(event){
    event.preventDefault();

    const buscar = document.getElementById("inputBuscar").value;

    if (isNaN(buscar)){
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Debe buscar por DNI (número)',
            showConfirmButton: false,
            timer: 1700
        });
        setTimeout(() => {
            location.reload();
            return
        }, 1700);
        
    }else{
        document.getElementById("formBuscar").submit();
    }
}
console.log("vinculado editarClientes.js");


function agregarCliente(event){
    event.preventDefault(); // Evita el envío del formulario por defecto

    const nombreCliente = document.getElementById("nombreCliente").value;
    const apellidoCliente = document.getElementById("apellidoCliente").value;
    const dniCliente = document.getElementById("dniCliente").value;
    const telefonoCliente = document.getElementById("telefonoCliente").value;
    const localidadCliente = document.getElementById("localidadCliente").value;
    const nroLicencia = document.getElementById("nroLicencia").value;
    const vtoLicencia = document.getElementById("vtoLicencia").value;

    if (nombreCliente == "" || apellidoCliente == "" || dniCliente == "" || telefonoCliente == "" || localidadCliente == "" || nroLicencia == "" || vtoLicencia == ""){
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    }else if(isNaN(telefonoCliente)){
        Swal.fire(
            'Error!',
            'El teléfono debe ser un número',
            'error'
        );
        return
    }else if(isNaN(dniCliente)){
        Swal.fire(
            'Error!',
            'El DNI debe ser un número',
            'error'
        );
        return
    }
    else{
        document.getElementById("addUserForm").submit();
    }
}

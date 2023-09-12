console.log("vinculado vehiculos.js");

function agregarVehiculo(event){
    event.preventDefault(); // Evita el envío del formulario por defecto

    const patente = document.getElementById("patente").value;
    const km = document.getElementById("km").value;
    const marca = document.getElementById("marcaAuto").value;
    const modelo = document.getElementById("modeloAuto").value;
    const cte = document.getElementById("clienteAuto").value;


    if (patente == "" || km=="" || marca == "Marca" || modelo == "Modelo" || cte == "Cliente"){
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    }else if(isNaN(km)){ //si no es un nro es verdadero y entra
        Swal.fire(
            'Error!',
            'Los kilómetros deben ser un número!',
            'error'
        );
        return
    }
    else{
        document.getElementById("addUserForm").submit();
    }

}

function eliminarVehiculo(){
    return confirm("Esta seguro de eliminar el vehículo?");
}

const params = new URLSearchParams(window.location.search);
const mensaje = params.get('mensaje');

// Verificar si el valor del parámetro 'mensaje' es igual a 'true' y mostrar una alerta
if (mensaje == 'false') {
    Swal.fire(
        'Error!',
        'Este vehículo tiene asociado un técnico registrado, no es posible borrar',
        'error'
    );
}
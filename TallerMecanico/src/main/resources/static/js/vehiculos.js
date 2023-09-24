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

// Verificar si el valor del parámetro 'mensaje' es igual a 'patenteRepetida' y mostrar una alerta
if (mensaje === 'patenteRepetida') {
    Swal.fire('Error!', 'Ya existe un vehículo registrado con esta patente', 'error');
}


//----------------CARGAR MODELOS SEGUN MARCA SELECCIONADA--------------------
function cargarModelosPorMarca() {
    const marcaId = document.getElementById("marcaAuto").value;
    const modelosSelect = document.getElementById("modeloAuto");
    modelosSelect.innerHTML = '<option selected hidden>Modelo</option>';

    if (marcaId === "Marca") {
        return;
    }

    const url = `/vehiculos/listar?marcaId=${marcaId}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            modelosSelect.innerHTML = '<option selected hidden>Modelo</option>';
            data.forEach(modelo => {
                modelosSelect.innerHTML += `<option value="${modelo.id_modelo}">${modelo.nombre}</option>`;
            });
        })
        .catch(error => console.error("Error al obtener modelos:", error));
}
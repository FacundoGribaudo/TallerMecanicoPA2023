console.log("vinculado vehiculos.js");

function agregarVehiculo(event) {
    event.preventDefault(); // Evita el envío del formulario por defecto

    const patente = document.getElementById("patente").value;
    const km = document.getElementById("km").value;
    const marca = document.getElementById("marcaAuto").textContent;
    const modelo = document.getElementById("modeloAuto").textContent;
    const cte = document.getElementById("clienteAuto").textContent;
    const anioFabricacion = document.getElementById("anioFabr").value;


    if (patente == "" || km == "" || marca == "Marca" || modelo == "Modelo" || cte == "Cliente" || anioFabricacion == "") {
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    } else if (isNaN(km) || isNaN(anioFabricacion)) { //si no es un nro es verdadero y entra
        Swal.fire(
            'Error!',
            'Los kilómetros y año de fabricación deben ser un número!',
            'error'
        );
        return
    }
    else {
        document.getElementById("addUserForm").submit();
    }

}

function eliminarVehiculo() {
    return confirm("Esta seguro de eliminar el vehículo?");
}

function mostrarFormMarcaSelect(e) {
    e.preventDefault();
    var fondo = document.getElementById("fondoFormCrearSelect");
    fondo.style.display= "block";
    
    var elemento = document.getElementById("formCrearSelect");
    elemento.style.display = "block";

    console.log("crear marca desde select");
}

const params = new URLSearchParams(window.location.search);
const mensaje = params.get('mensaje');

// Verificar si el valor del parámetro 'mensaje' es igual a 'true' y mostrar una alerta
if (mensaje == 'tecnicoAsociado') {
    Swal.fire(
        'Error!',
        'Este vehículo tiene asociado un técnico, no es posible borrar',
        'error'
    );
} else if (mensaje == 'oTAsociada') {
    Swal.fire(
        'Error!',
        'Este vehículo tiene asociada una orden de trabajo, no es posible borrar',
        'error'
    );
} else if (mensaje == 'tecnicoYOTAsociada') {
    Swal.fire(
        'Error!',
        'Este vehículo tiene asociado un técnico y una orden de trabajo, no es posible borrar',
        'error'
    );
} else if (mensaje == 'patenteRepetida') {
    Swal.fire('Error!',
        'Ya existe un vehículo registrado con esta patente',
        'error'
    );
}


//----------------CARGAR MODELOS SEGUN MARCA SELECCIONADA--------------------
function cargarModelosPorMarca() {
    const marcaId = document.getElementById("marcaAuto").value;
    const modelosSelect = document.getElementById("modeloAuto");
    modelosSelect.innerHTML = '<option selected hidden>Modelo</option>';

    if (marcaId === "Marca") {
        return;
    }

    const url = `/vehiculos/listarModelosPorMarca?marcaId=${marcaId}`;

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


// function cargarModelosPorMarca(modeloVehiculoId) {
//     const marcaId = document.getElementById("marcaAuto").value;
//     const modelosSelect = document.getElementById("modeloAuto");

//     modelosSelect.innerHTML = '';  // Limpiar el contenido actual

//     if (marcaId === "Marca") {
//         return;
//     }

//     const url = `/vehiculos/listarModelosPorMarca?marcaId=${marcaId}`;

//     fetch(url)
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('Network response was not ok: ' + response.statusText);
//             }
//             return response.json();
//         })
//         .then(data => {
//             console.log(modeloVehiculoId);
//             data.forEach(modelo => {
//                 const option = document.createElement('option');
//                 option.value = modelo.id_modelo;
//                 option.text = modelo.nombre;

//                 // Establecer el modelo seleccionado si coincide con el modelo del vehículo
//                 if (modelo.id_modelo.toString() === modeloVehiculoId) {
//                     option.selected = true;
//                 }

//                 modelosSelect.appendChild(option);
//             });
//         })
//         .catch(error => console.error("Error al obtener modelos:", error));
// }

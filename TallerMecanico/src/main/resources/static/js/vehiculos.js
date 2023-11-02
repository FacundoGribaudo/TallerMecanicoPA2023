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
        //cuando envio el formulario borro el cache
        borrarCache();
        document.getElementById("addUserForm").submit();
    }

}

function eliminarVehiculo() {
    return confirm("Esta seguro de eliminar el vehículo?");
}

//Las 3 funciones son llamadas cuando se apreta el boton de + para crear una marca, modelo o cliente desde la ventana de vehiculos, para mostrar el form
function mostrarFormMarcaSelect(e) {
    e.preventDefault();
    var fondo = document.getElementById("fondoFormCrearSelect");
    fondo.style.display = "block";

    var elemento = document.getElementById("formCrearMarcaSelect");
    elemento.style.display = "block";

    console.log("crear marca desde select");
}

function mostrarFormModeloSelect(e) {
    e.preventDefault();
    var fondo = document.getElementById("fondoFormCrearSelect");
    fondo.style.display = "block";

    var elemento = document.getElementById("formCrearModeloSelect");
    elemento.style.display = "block";

    console.log("crear modelo desde select");
}

function mostrarFormClienteSelect(e) {
    e.preventDefault();
    var fondo = document.getElementById("fondoFormCrearSelect");
    fondo.style.display = "block";

    var elemento = document.getElementById("formCrearClienteSelect");
    elemento.style.display = "block";

    console.log("crear cliente desde select");
}

//Se llama cuando se cancela y se quiere cerrar el formulario
function cerrarFormSelect(e) {
    e.preventDefault();

    var fondo = document.getElementById("fondoFormCrearSelect");
    fondo.style.display = "none";

    var elemento = document.getElementById("formCrearMarcaSelect");
    elemento.style.display = "none";

    var elemento = document.getElementById("formCrearModeloSelect");
    elemento.style.display = "none";

    var elemento = document.getElementById("formCrearClienteSelect");
    elemento.style.display = "none";

}

//validaciones para crear desde select
function validarSelectMarca(event) {
    event.preventDefault(); // Evita el envío del formulario por defecto

    const marca = document.getElementById("inputMarca").value;
    if (marca == "" || !isNaN(marca)) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'La marca no puede ser un número ni estar vacía',
        })
        return;
    } else {
        mantenerDatos();
        document.getElementById("formSelectMarca").submit();
    }
}

function validarSelectModelo(event) {
    event.preventDefault(); // Evita el envío del formulario por defecto

    const modelo = document.getElementById("modelo").value;
    const estado = document.getElementById("estadoModelo").value;
    const marcaModelo = document.getElementById("marcaModelo").value;

    if (modelo == "" || estado == "Estado" || marcaModelo == "Marca") {
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    } else {
        mantenerDatos();
        document.getElementById("formSelectModelo").submit();
    }
}

function validarSelectCliente(event) {
    event.preventDefault(); // Evita el envío del formulario por defecto

    const nombreCliente = document.getElementById("nombreCliente").value;
    const apellidoCliente = document.getElementById("apellidoCliente").value;
    const dniCliente = document.getElementById("dniCliente").value;
    const telefonoCliente = document.getElementById("telefonoCliente").value;
    const localidadCliente = document.getElementById("localidadCliente").value;

    if (nombreCliente == "" || apellidoCliente == "" || dniCliente == "" || telefonoCliente == "" || localidadCliente == "") {
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    } else if (isNaN(dniCliente) || isNaN(telefonoCliente)) {
        Swal.fire(
            'Error!',
            'El DNI y/o número de teléfono debe ser un número',
            'error'
        );
        return
    } else {
        mantenerDatos();
        document.getElementById("formSelectCliente").submit();
    }
}

function mantenerDatos() {

    //datos Patente
    const patente = document.getElementById("patente").value;
    localStorage.setItem("inputPatente", patente);

    //datos km
    const km = document.getElementById("km").value;
    localStorage.setItem("inputKM", km);

    //datos año farbicacion
    const anioFabricacion = document.getElementById("anioFabr").value;
    localStorage.setItem("inputanioFabricacion", anioFabricacion);
}

function borrarCache() {
    //borro todo el caché de una
    localStorage.clear();
}

function limpiarForm(){
    localStorage.clear();
    document.getElementById("addUserForm").reset();
}

//traigo los datos del cache y los tomo como input inicial, si es que existe
const patenteGuardada = localStorage.getItem("inputPatente");
const kmGuardado = localStorage.getItem("inputKM");
const anioFabGuardado = localStorage.getItem("inputanioFabricacion");
const marca = localStorage.getItem("marca");
const idMarca = localStorage.getItem("idMarca");
const modelo = localStorage.getItem("modelo");
const idModelo = localStorage.getItem("idModelo");
const cte = localStorage.getItem("cte");
const idcte = localStorage.getItem("idcte");

document.getElementById("patente").value = patenteGuardada;
document.getElementById("km").value = kmGuardado;
document.getElementById("anioFabr").value = anioFabGuardado;


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
} else if (mensaje === 'marcaRepetida') {
    // Verificar si el valor del parámetro 'mensaje' es igual a 'marcaRepetida' y mostrar una alerta
    Swal.fire('Error!', 'Ya existe esta Marca registrada', 'error');
} else if (mensaje === 'clienteRepetido') {
    Swal.fire('Error!', 'Ya existe un Cliente registrado con este DNI', 'error');
}

// Eliminar el parámetro 'mensaje' de la URL después de mostrar la alerta
params.delete('mensaje');
const newUrl = window.location.pathname + (params.toString() ? '?' + params.toString() : '');
history.replaceState({}, document.title, newUrl);


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

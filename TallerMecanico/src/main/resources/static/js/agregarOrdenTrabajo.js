console.log("vinculado.js");

const params = new URLSearchParams(window.location.search);
const mensaje = params.get('mensaje');

function agregarOrdenTrabajo(e) {
    e.preventDefault();

    const vehiculoSelect = document.getElementById("vehiculoServicio");
    const servicioCheckboxes = document.querySelectorAll('input[name="serviciosRealizar"]:checked');
    const tecnicoCheckboxes = document.querySelectorAll('input[name="tecnicosOrden"]:checked');
    const fechaHoraOrdenInput = document.getElementById("fechaHoraOrden");

    console.log(vehiculoSelect.value);
    console.log(servicioCheckboxes.length);
    console.log(tecnicoCheckboxes.length);
    console.log(fechaHoraOrdenInput.value);
    // Verifica si algún campo está vacío
    if (vehiculoSelect.value == "Vehiculo" || servicioCheckboxes.length === 0 || tecnicoCheckboxes.length === 0 || fechaHoraOrdenInput.value == "") {
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
    } else {
        document.getElementById("addUserForm").submit();
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const clickableLabels = document.querySelectorAll(".clickable-label");

    clickableLabels.forEach(function (label) {
        label.addEventListener("click", function () {
            event.preventDefault();
            const checkbox = label.previousElementSibling;
            checkbox.checked = !checkbox.checked;

            // Dispara el evento change en el checkbox manualmente
            const changeEvent = new Event("change", { bubbles: true });
            checkbox.dispatchEvent(changeEvent);
        });
    });
});



document.addEventListener("DOMContentLoaded", function () {
    const tecnicosCheckboxes = document.querySelectorAll('input[name="tecnicosOrden"]');
    const serviciosCheckboxes = document.querySelectorAll('input[name="serviciosRealizar"]');
    const eliminarSeleccionTecnicos = document.getElementById("eliminarSeleccionTecnicos");
    const eliminarSeleccionServicios = document.getElementById("eliminarSeleccionServicios");

    tecnicosCheckboxes.forEach(function (checkbox) {
        checkbox.addEventListener("change", function () {
            const selectedTecnicos = document.querySelectorAll('input[name="tecnicosOrden"]:checked');
            eliminarSeleccionTecnicos.style.display = selectedTecnicos.length > 0 ? "block" : "none";
        });
    });

    serviciosCheckboxes.forEach(function (checkbox) {
        checkbox.addEventListener("change", function () {
            const selectedServicios = document.querySelectorAll('input[name="serviciosRealizar"]:checked');
            eliminarSeleccionServicios.style.display = selectedServicios.length > 0 ? "block" : "none";
        });
    });

    eliminarSeleccionTecnicos.addEventListener("click", function () {
        tecnicosCheckboxes.forEach(function (checkbox) {
            checkbox.checked = false;
        });
        eliminarSeleccionTecnicos.style.display = "none";
    });

    eliminarSeleccionServicios.addEventListener("click", function () {
        serviciosCheckboxes.forEach(function (checkbox) {
            checkbox.checked = false;
        });
        eliminarSeleccionServicios.style.display = "none";
    });
});


function mostrarFondo() {
    var fondo = document.getElementById("fondoFormCrearSelect");
    fondo.style.display = "block";
}

function mostrarFormVehiculosSelect(e) {
    e.preventDefault();

    mostrarFondo();
    var elemento = document.getElementById("formCrearVehiculoSelect");
    elemento.style.display = "block";
}

function mostrarFormServicioSelect(e) {

    mostrarFondo();


    var elemento = document.getElementById("formCrearServicioSelect");
    elemento.style.display = "block";
}

function mostrarFormTecnicoSelect(e) {
    e.preventDefault();

    mostrarFondo();


    var elemento = document.getElementById("formCrearTecnicoSelect");
    elemento.style.display = "block";
}

function cerrarFormCrear(e) {
    e.preventDefault();

    var fondo = document.getElementById("fondoFormCrearSelect");
    fondo.style.display = "none";

    var elemento = document.getElementById("formCrearVehiculoSelect");
    elemento.style.display = "none";

    var elemento = document.getElementById("formCrearServicioSelect");
    elemento.style.display = "none";

    var elemento = document.getElementById("formCrearTecnicoSelect");
    elemento.style.display = "none";

}
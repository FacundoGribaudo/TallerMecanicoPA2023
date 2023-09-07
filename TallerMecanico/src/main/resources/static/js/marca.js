function validarFormulario(event) {
    event.preventDefault(); // Evita el envío del formulario por defecto

    const marca = document.getElementById("inputMarca").value;
    if (marca=="" || !isNaN(marca)){
        alert("El campo no puede estar vacío ni puede ser un número");
        return;
    }else{
        document.getElementById("addUserForm").submit();
    }
 
    // Realiza la validación (agrega tus propias reglas de validación)
    if (marcaInput.trim() === "") {
       alert("Por favor, ingresa una marca.");
    } else if (estadoInput === "Estado") {
       alert("Por favor, selecciona un estado.");
    } else {
       // Si la validación es exitosa, envía el formulario
    }
} 

function eliminarMarca(marcaId) {
    Swal.fire({
        title: '¿Está seguro de que desea eliminar la marca?',
        text: "Esta acción NO se puede revertir",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, borrar'
    }).then((result) => {
        if (result.isConfirmed) {
            // Enviar el formulario de eliminación con marcaId como argumento
            document.getElementById("formEliminar").action = "/eliminarMarca/" + marcaId;
            document.getElementById("formEliminar").submit();
        }
    });
}

console.log("vinculado");
function validarFormulario(event) {
    event.preventDefault(); // Evita el envío del formulario por defecto

    const marca = document.getElementById("inputMarca").value;
    const estado = document.getElementById("inputState").value;
    if (marca=="" || !isNaN(marca)){
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'La marca no puede ser un número ni estar vacía',
        })
        return;
    }else if(estado == "Estado"){
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Debe seleccionar un estado para la marca',
        })
        return;
    }
    else{
        document.getElementById("addUserForm").submit();
    }
} 

function eliminarMarca(event) {
    event.preventDefault();
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
            console.log("confirmado");
        }
    });
}
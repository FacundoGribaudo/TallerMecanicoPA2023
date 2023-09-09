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

function confirmarEliminar(){
    return confirm("Estas seguro de eliminar?");
}
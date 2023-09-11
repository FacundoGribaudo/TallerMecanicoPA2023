console.log("vinculado modelo.js");

function agregarModelo(event){
    event.preventDefault(); // Evita el envío del formulario por defecto

    const modelo = document.getElementById("modelo").value;
    const estado = document.getElementById("estadoModelo").value;
    const marcaModelo = document.getElementById("marcaModelo").value;

    if (modelo == "" || estado == "Estado" || marcaModelo == "Marca"){
        Swal.fire(
            'Error!',
            'Por favor complete todos los campos',
            'error'
        );
        return
    }else{
        document.getElementById("addUserForm").submit();
    }
}
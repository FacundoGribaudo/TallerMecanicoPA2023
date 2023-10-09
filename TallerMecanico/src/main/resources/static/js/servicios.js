console.log("vinculado servicios.js");

function agregarServicio(e) {
    e.preventDefault();

    const servicio = document.getElementById("nombreServicio").value;
    const descServicio = document.getElementById("descripcionServicio").value;

    if (servicio == "" || descServicio == "") {
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
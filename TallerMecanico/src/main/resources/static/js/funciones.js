function eliminarMarca(id){
    Swal.fire({
        title: 'Estas seguro que deseas elminiar esta marca?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url:"/eliminarMarca/"+id_marca,
                success: function(res){
                    console.log(res); 
                }
            })
            Swal.fire(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                    )
        }
        }
    })
}



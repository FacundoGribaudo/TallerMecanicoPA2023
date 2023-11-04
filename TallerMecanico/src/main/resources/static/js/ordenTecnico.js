console.log("vinculado orden tecnico.js");
const titulo = document.getElementById("tecnicoDeConsulta");

var nombre = localStorage.getItem("nombreTecnico").toUpperCase();
var apellido = localStorage.getItem("apellidoTecnico").toUpperCase();

titulo.textContent = "HISTORIAL DE ORDENES DE "+nombre+" "+apellido;
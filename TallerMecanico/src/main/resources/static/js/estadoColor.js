console.log("vinculadoColor.js");
// script.js

document.addEventListener('DOMContentLoaded', function () {
    cambiarColorFondo();
  });
  
  function cambiarColorFondo() {
    var estadoText = document.getElementById('estadoColor').innerText.trim();
    var estadoClass;
  
    switch (estadoText) {
      case 'EN ESPERA':
        estadoClass = 'ENESPERA';
        break;
      case 'EN PROGRESO':
        estadoClass = 'ENPROGRESO';
        break;
      case 'ESPERANDO RESPUESTA':
        estadoClass = 'ESPERANDORESPUESTO';
        break;
      case 'COMPLETADA':
        estadoClass = 'COMPLETADA';
        break;
      default:
        estadoClass = ''; // Puedes establecer un color predeterminado aquí
        break;
    }
  
    document.getElementById('estadoColor').classList.add(estadoClass);
  }
  
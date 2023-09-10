package com.Proyecto.TallerMecanico.controler;

import org.springframework.ui.Model;
import com.Proyecto.TallerMecanico.domain.Marca;
import com.Proyecto.TallerMecanico.domain.Modelo;
import com.Proyecto.TallerMecanico.domain.Tecnico;
import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.domain.Cliente;
import com.Proyecto.TallerMecanico.interfaceServices.IModeloServices;
import com.Proyecto.TallerMecanico.interfaceServices.IclienteServices;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcaServices;
import com.Proyecto.TallerMecanico.interfaceServices.ItecnicoServices;
import com.Proyecto.TallerMecanico.interfaceServices.IvehiculoServices;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestParam;




@Controller 
@RequestMapping 
public class Controlador {
    
    @Autowired
    private ImarcaServices service; 
    @Autowired
    private IvehiculoServices servicesVehiculo;
    @Autowired
    private IModeloServices servicesModelo;
    @Autowired
    private IclienteServices servicesCliente;
    @Autowired
    private ItecnicoServices servicesTecnico; 
    
    @GetMapping("/")
    public String home(Model model){
        return "index";  //Renderizamos el template 
    }
    
    
    // ------------- ABM MARCA -------------
    @GetMapping("/marca")
    public String listar(Model model){
        List<Marca> marcas = service.listarMarcas();
        //System.out.println(marcas);
        model.addAttribute("marcas", marcas); 
        return "marca";
    }
    
    @PostMapping("/save")
    public String saveMarca(Marca m, Model model){
        service.save(m); 
        return "redirect:/marca";
    }
    
    @GetMapping("/editarMarca/{id_marca}")
    public String editarMarca(@PathVariable int id_marca, Model model){
        Optional<Marca> optionalMarca = service.listarIdMarca(id_marca);

        // Verifica si la marca existe antes de agregarla al modelo
        if (optionalMarca.isPresent()) {
            Marca marca = optionalMarca.get();
            model.addAttribute("marca", marca);
        } else {
            // Manejar el caso en el que la marca no existe (puedes redirigir o mostrar un mensaje de error)
        }

        return "editarMarca"; 
    }
    
    @GetMapping("/eliminarMarca/{id_marca}")
    public String eliminarMarca(Model model, @PathVariable int id_marca){
        service.delete(id_marca);
        return "redirect:/marca"; 
    }
    
    @PostMapping("/buscar")
    public String buscarMarca(Model model, String nombre){
        
        List<Marca> marcas = service.listarMarcas(); //Traigo todos los objetos de la db
        List<Marca> marcasBuscadas = new ArrayList<Marca>(); //Segundo array que me almacena los objetos que coinciden
        
        
        for (int i = 0; i<marcas.size(); i++){
            if(marcas.get(i).getNombre().toUpperCase().equals(nombre.toUpperCase())){
                System.out.println("ENTRO PORQUE EL NOMBRE COINCIDE");
                marcasBuscadas.add(marcas.get(i));
            }
        }

        if(marcasBuscadas.size() == 0){
            marcasBuscadas = marcas; 
        }

        model.addAttribute("marcas", marcasBuscadas); 
        return "marca"; 
    }



    // ------------- ABM VEHICULO -------------
    @GetMapping("/vehiculos")
    public String listarVehiculos(Model model){
        
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();

        //Marcas 
        List<Marca> marcas = service.listarMarcas();
        List<Marca> marcasActivas = new ArrayList<Marca>(); //Segundo array que me almacena los objetos que coinciden

        //Modelo
        List<Modelo> modelos = servicesModelo.listarModelos();
        List<Modelo> modelosPermitidos = new ArrayList<Modelo>();

        //Cliente
        List<Cliente> clientes = servicesCliente.listarClientes();

        // //Tecnicos
        // List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        // System.out.println(""+"Los tecnicos son: " + tecnicos); 
        // List<Tecnico> tecnicosActivos = new ArrayList<Tecnico>(); 

        //Unicamente permito vincular modelos de aquellas marcas que estan activas. 
        for (Modelo m : modelos){
            System.out.println(m.getMarca().getEstado());
            if(m.getMarca().getEstado().toUpperCase().equals("activo".toUpperCase())){
                modelosPermitidos.add(m);
            }
            System.out.println(modelosPermitidos);
        }


        for(int i=0; i<marcas.size(); i++){
            if(marcas.get(i).getEstado().toUpperCase().equals("activo".toUpperCase())){
                marcasActivas.add(marcas.get(i));
            }
        }

        // //Muestro los tecnicos que estan en estado "activo" --> Disponibles para trabajar
        // for (Tecnico t:tecnicos){
        //     if(t.getEstado().toUpperCase().equals("activo".toUpperCase())){
        //         // System.out.println(""+ "Los tecnicos activos son: " + t.getNombre()); 
        //         tecnicosActivos.add(t); 
        //     }
        // }

        System.out.println(""+ "Los objetos VEHICULO estan asi: " + vehiculos);
        
        model.addAttribute("vehiculo", vehiculos); 
        model.addAttribute("marcasActivas", marcasActivas); 
        model.addAttribute("modelosPermitidos", modelosPermitidos);
        // model.addAttribute("tecnicos", tecnicosActivos);
        model.addAttribute("clientes", clientes);
        return "vehiculos";
    }

    @PostMapping("/saveVehiculo")
    public String guardarVehiculo(Vehiculo v, Model model){
        servicesVehiculo.save(v);
        return "redirect:/vehiculos"; 
    }  

    @GetMapping("/eliminarVehiculo/{id_vehiculo}")
    public String eliminarVehiculo(Model model, @PathVariable int id_vehiculo){
        servicesVehiculo.delete(id_vehiculo);
        return "redirect:/vehiculos"; 
    }

    @GetMapping("/editarVehiculo/{id_vehiculo}")
    public String editarVehiculo(@PathVariable int id_vehiculo, Model model){
        Optional<Vehiculo> optionalVehiculo = servicesVehiculo.listarIdVehiculo(id_vehiculo);

        // Verifica si la marca existe antes de agregarla al modelo
        if (optionalVehiculo.isPresent()) {
            Vehiculo vehiculo = optionalVehiculo.get();
            model.addAttribute("vehiculo", vehiculo);
        } else {
            // Manejar el caso en el que la marca no existe (puedes redirigir o mostrar un mensaje de error)
        }

        //Marcas 
        List<Marca> marcas = service.listarMarcas();
        List<Marca> marcasActivas = new ArrayList<Marca>(); //Segundo array que me almacena los objetos que coinciden

        //Modelo
        List<Modelo> modelos = servicesModelo.listarModelos();
        List<Modelo> modelosPermitidos = new ArrayList<Modelo>();

        //Cliente
        List<Cliente> clientes = servicesCliente.listarClientes();

        //Unicamente permito vincular modelos de aquellas marcas que estan activas. 
        for (Modelo m : modelos){
            System.out.println(m.getMarca().getEstado());
            if(m.getMarca().getEstado().toUpperCase().equals("activo".toUpperCase())){
                modelosPermitidos.add(m);
            }
            System.out.println(modelosPermitidos);
        }
        
        for(int i=0; i<marcas.size(); i++){
            if(marcas.get(i).getEstado().toUpperCase().equals("activo".toUpperCase())){
                marcasActivas.add(marcas.get(i));
            }
        }

        //model.addAttribute("vehiculo", vehiculos); 
        model.addAttribute("marcasActivas", marcasActivas); 
        model.addAttribute("modelosPermitidos", modelosPermitidos);
        model.addAttribute("clientes", clientes);

        return "editarVehiculo"; 
    }

    @PostMapping("/buscarVehiculo")
    public String buscarVehiculo(Model model, String patente){

        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos(); //Traigo todos los objetos de la db
        List<Vehiculo> vehiculosBuscados = new ArrayList<Vehiculo>(); //Un segundo array que me almacena los objetos vehiculos que coinciden con la patente buscada

        for(int i=0; i<vehiculos.size(); i++){
            if(vehiculos.get(i).getPatente().toUpperCase().equals(patente.toUpperCase())){
                vehiculosBuscados.add(vehiculos.get(i));
            }
        }

        if(vehiculosBuscados.size() == 0){
            vehiculosBuscados = vehiculos; 
        }

        model.addAttribute("vehiculo", vehiculosBuscados);
        return "vehiculos";

    }



    // ------------- ABM MODELO -------------
    // Cambia el nombre del atributo para los modelos en el modelo
    @GetMapping("/modelo")
    public String listarModelos(Model model){
        List<Modelo> modelos = servicesModelo.listarModelos(); // Obtén la lista de modelos
        model.addAttribute("modelos", modelos); // Usa "modelos" como el nombre del atributo en el modelo

        //Marcas 
        List<Marca> marcas = service.listarMarcas();
        /*
        List<Marca> marcasActivas = new ArrayList<Marca>(); //Segundo array que me almacena los objetos que coinciden

        for(int i=0; i<marcas.size(); i++){
            if(marcas.get(i).getEstado().toUpperCase().equals("activo".toUpperCase())){
                marcasActivas.add(marcas.get(i));
            }
        }*/ 

        model.addAttribute("marcas", marcas); 

        return "modelo"; // Retorna la vista "modelo" para mostrar los modelos
    }

    @PostMapping("/saveModelo")
    public String agregarModelo(Modelo mo, Model model) {
        servicesModelo.save(mo); 
        // Redirige a la página de modelos para mostrar la lista actualizada
        return "redirect:/modelo";
    }
 
    @GetMapping("/eliminarModelo/{id_modelo}")
    public String delete(Modelo modelo,@PathVariable int id_modelo){
        servicesModelo.delete(id_modelo);
        return "redirect:/modelo"; 
    }

    @GetMapping("/editarModelo/{id_modelo}")
    public String editarModelo(@PathVariable int id_modelo, Model model){
        Optional<Modelo> optionalModelo = servicesModelo.listarIdModelo(id_modelo);

        //Marcas 
        List<Marca> marcas = service.listarMarcas();
        
        //List<Marca> marcasActivas = new ArrayList<Marca>(); //Segundo array que me almacena los objetos que coinciden

        /* 
        for(int i=0; i<marcas.size(); i++){
            if(marcas.get(i).getEstado().toUpperCase().equals("activo".toUpperCase())){
                marcasActivas.add(marcas.get(i));
            }
        }*/

        model.addAttribute("marcas", marcas); 

        // Verifica si la marca existe antes de agregarla al modelo
        if (optionalModelo.isPresent()) {
            Modelo modelo = optionalModelo.get();
            model.addAttribute("modelo", modelo);
        } else {
            // Manejar el caso en el que la marca no existe (puedes redirigir o mostrar un mensaje de error)
        }

        return "editarModelo"; 
    }

    @PostMapping("/buscarModelo")
    //TODO Ver si se busca tambien por marca, mepa medio al pedo.
    public String buscarModelo(Model model, @RequestParam("nombreModelo") String nombre){
        List<Modelo> modelos = servicesModelo.listarModelos();
        List<Modelo> modelosBuscados = new ArrayList<Modelo>();
        
        for(Modelo m : modelos){
            if (m.getNombre().toUpperCase().equals(nombre.toUpperCase())){
                modelosBuscados.add(m); 
            }
        }

        if (modelosBuscados.size() == 0){
            modelosBuscados = modelos; 
        }

        model.addAttribute("modelos", modelosBuscados);

        return "modelo"; 
    }


    // ------------- ABM CLIENTE -------------
    @GetMapping("/clientes")
    public String listarClientes(Model model){
        List<Cliente> clientes = servicesCliente.listarClientes();
        model.addAttribute("clientes", clientes);
        return "clientes"; 
    }
    
    @PostMapping("/saveCliente")
    public String agregarCliente(Cliente c, Model model){
        servicesCliente.save(c);
        return "redirect:/clientes"; 
    }

    @GetMapping("/eliminarCliente/{id_cliente}")
    public String eliminarCliente(Model model, @PathVariable int id_cliente){
        servicesCliente.delete(id_cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editarCliente/{id_cliente}")
    public String editarCliente(@PathVariable int id_cliente, Model model){
        Optional<Cliente> optionalCliente = servicesCliente.listarIdCliente(id_cliente);

        // Verifica si la marca existe antes de agregarla al modelo
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            model.addAttribute("cliente", cliente);
        } else {
            // Manejar el caso en el que la marca no existe (puedes redirigir o mostrar un mensaje de error)
        }

        return "editarCliente"; 
    }
    
    @PostMapping("/buscarCliente")
    public String buscarCliente(Model model, @RequestParam("datoCliente") String dni){
        List<Cliente> clientes = servicesCliente.listarClientes();
        List<Cliente> clientesBuscados = new ArrayList<Cliente>();

        
        if(dni != " " && dni !="" ){
            int dniEntero = Integer.parseInt(dni);
            for (Cliente c:clientes){
                if(c.getDni().intValue() == dniEntero){
                    System.out.println("LOS VALORES COINCIDEN SE BUSCARA...");
                    clientesBuscados.add(c);
                }
            }
        }else{
            System.out.println("SE INGRESO UNA CADENA VACIA, NO BUSCA NADA..");
            clientesBuscados = clientes;
        }

        model.addAttribute("clientes", clientesBuscados);
        return "clientes";

    }


    // ------------- ABM TECNICOS -------------
    @GetMapping("/tecnicos")
    public String listarTecnicos(Model model){
        
        /*
         * DEFINIMOS QUE MUCHOS TECNICOS PUEDAN ESTAR EN UN MISMO VEHICULO
         * ASI QUE DEBEMOS PODER SELECCIONAR Y MOSTRAR LOS VEHICULOS DONDE ESTAN 
         * TRABAJANDO LOS TECNICOS
         */
        
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();

        model.addAttribute("tecnicos", tecnicos);
        model.addAttribute("vehiculos", vehiculos);
        
        return "tecnico";
    }

    @PostMapping("/saveTecnico")
    public String guardarTecnico(Tecnico t){
        servicesTecnico.save(t);
        return "redirect:/tecnicos";
    }

    @GetMapping("/eliminarTecnico/{id_tecnico}")
    public String eliminarTecnico(Model model, @PathVariable int id_tecnico){
        servicesTecnico.delete(id_tecnico);
        return "redirect:/tecnicos"; 
    }

    @GetMapping("/editarTecnico/{id_tecnico}")
    public String editarTecnico(@PathVariable int id_tecnico, Model model){
        Optional<Tecnico> optionalTecnico = servicesTecnico.listarIdTecnico(id_tecnico);
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();

        // Verifica si la marca existe antes de agregarla al modelo
        if (optionalTecnico.isPresent()) {
            Tecnico tecnico = optionalTecnico.get();
            model.addAttribute("tecnico", tecnico);
        } else {
            // Manejar el caso en el que la marca no existe (puedes redirigir o mostrar un mensaje de error)
        }

        model.addAttribute("vehiculos", vehiculos);
        return "editarTecnico"; 
    }

    @PostMapping("/buscarTecnico") //TODO Definir por que parametro buscar, esta filtrado por "estado"
    public String buscarTecnico(Model model, @RequestParam("datosBuscados") String estado){
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        List<Tecnico> tecnicosEncontrados = new ArrayList<Tecnico>();
        
        

        for (Tecnico t:tecnicos){
            if(t.getEstado().toUpperCase().equals(estado.toUpperCase())){
                tecnicosEncontrados.add(t);
            }
        }

        if (tecnicosEncontrados.size() == 0){
            tecnicosEncontrados = tecnicos;
        }

       
        model.addAttribute("tecnicos", tecnicosEncontrados);

        return "tecnico";
    }
}







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
        model.addAttribute("marcas", marcas); 
        return "marca";
    }
    
    @PostMapping("/save")
    public String saveMarca(Marca m, Model model){
        // List<Marca> marcas = service.listarMarcas();
        // Boolean guardoMarca = true;

        // for(Marca mar:marcas){
        //     if(mar.getNombre().toUpperCase().equals(m.getNombre().toUpperCase())){
        //         System.out.println("son iguale ");
        //         guardoMarca = false;
        //     }
        // }
        
        // if(guardoMarca == true){
        //     service.save(m); 
        // }
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
        }

        return "editarMarca"; 
    }
    
    @GetMapping("/eliminarMarca/{id_marca}")
    public String eliminarMarca(Model model, @PathVariable int id_marca){
        List<Modelo> modelos = servicesModelo.listarModelos();
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();

        Boolean eliminarMarca = true;

        for(Modelo m:modelos){
            for(Vehiculo v:vehiculos){
                if(m.getMarca().getId_marca().equals(id_marca) || v.getMarca().getId_marca().equals(id_marca)){
                    eliminarMarca = false;
                }
            }
        }
        
        if (eliminarMarca == true){
            System.out.println("si PODES ELIMINAR, NO ESTA RELACIONADO");
            service.delete(id_marca);
        }else{System.out.println("HAY REGISTROS RELACIONADOS");}        
        
        // Antes de la redirección
        String mensaje = eliminarMarca ? "true" : "false";
        String urlRedireccion = "redirect:/marca?mensaje=" + mensaje;
        return urlRedireccion;


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
        return "buscarMarca"; 
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
        // List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();
        // Boolean guardarVehiculo = true;

        // for(Vehiculo ve:vehiculos){
        //     if(ve.getPatente().toUpperCase().equals(v.getPatente().toUpperCase())){
        //         guardarVehiculo = false;
        //     }
        // }

        // if(guardarVehiculo == true){
        //     servicesVehiculo.save(v);
        // }
        servicesVehiculo.save(v);
        return "redirect:/vehiculos"; 
    }  

    @GetMapping("/eliminarVehiculo/{id_vehiculo}")
    public String eliminarVehiculo(Model model, @PathVariable int id_vehiculo){
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        Boolean eliminarVehiculo = true;

        for(Tecnico t:tecnicos){
            if(t.getVehiculo().getId_vehiculo().equals(id_vehiculo)){
                eliminarVehiculo = false;
            }
        }

        if(eliminarVehiculo == true){
            System.out.print("SI SE PUEDE ELIMINAR EL VEHICULO");
            servicesVehiculo.delete(id_vehiculo);
        }else{System.out.println("NO SE PUEDE ELIMINAR EL VEHICULO, ELIMINA LOS REGISTROS RELACIONADOS");}
        
        // Antes de la redirección
        String mensaje = eliminarVehiculo ? "true" : "false";
        String urlRedireccion = "redirect:/vehiculos?mensaje=" + mensaje;
        return urlRedireccion;
    }

    @GetMapping("/editarVehiculo/{id_vehiculo}")
    public String editarVehiculo(@PathVariable int id_vehiculo, Model model){
        Optional<Vehiculo> optionalVehiculo = servicesVehiculo.listarIdVehiculo(id_vehiculo);

        // Verifica si la marca existe antes de agregarla al modelo
        if (optionalVehiculo.isPresent()) {
            Vehiculo vehiculo = optionalVehiculo.get();
            model.addAttribute("vehiculo", vehiculo);
        } else {
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
        return "buscarVehiculo";

    }



    // ------------- ABM MODELO -------------
    // Cambia el nombre del atributo para los modelos en el modelo
    @GetMapping("/modelo")
    public String listarModelos(Model model){
        
        List<Modelo> modelos = servicesModelo.listarModelos(); 
        model.addAttribute("modelos", modelos);

        //Marcas 
        List<Marca> marcas = service.listarMarcas();

        model.addAttribute("marcas", marcas); 
        return "modelo";
    }

    @PostMapping("/saveModelo")
    public String agregarModelo(Modelo mo, Model model) {
        // List<Modelo> modelos = servicesModelo.listarModelos();
        // Boolean guardoModelo = true;

        // for(Modelo mod:modelos){
        //     if(mod.getNombre().toUpperCase().equals(mo.getNombre().toUpperCase())){
        //         System.out.println("son iguale ");
        //         guardoModelo = false;
        //     }
        // }
        
        // if(guardoModelo == true){
        //     servicesModelo.save(mo); 
        // }
        servicesModelo.save(mo); 
        return "redirect:/modelo";
    }
 
    @GetMapping("/eliminarModelo/{id_modelo}")
    public String delete(Modelo modelo,@PathVariable int id_modelo){
        
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();
        Boolean eliminarModelo = true;

        for(Vehiculo v:vehiculos){
            if(v.getModelo().getId_modelo().equals(id_modelo)){
                eliminarModelo = false;
            }
        }
        
        if(eliminarModelo == true){
            System.out.println("si PODES ELIMINAR, NO ESTA RELACIONADO");
            servicesModelo.delete(id_modelo);
        }else{System.out.println("NO PODES ELIMINAR, ESTA RELACIONADO");}

        // Antes de la redirección
        String mensaje = eliminarModelo ? "true" : "false";
        String urlRedireccion = "redirect:/modelo?mensaje=" + mensaje;
        return urlRedireccion;
    }

    @GetMapping("/editarModelo/{id_modelo}")
    public String editarModelo(@PathVariable int id_modelo, Model model){
        Optional<Modelo> optionalModelo = servicesModelo.listarIdModelo(id_modelo);

        //Marcas 
        List<Marca> marcas = service.listarMarcas();
        model.addAttribute("marcas", marcas); 

        if (optionalModelo.isPresent()) {
            Modelo modelo = optionalModelo.get();
            model.addAttribute("modelo", modelo);
        } else {
            
        }

        return "editarModelo"; 
    }

    @PostMapping("/buscarModelo")
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

        return "buscarModelo"; 
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
        // List<Cliente> clientes = servicesCliente.listarClientes();
        // Boolean cargarCliente = true;

        // for(Cliente cliente:clientes){
        //     if(cliente.getDni().equals(c.getDni())){
        //         cargarCliente = false;
        //     }
        // }
        
        // if(cargarCliente == true){
        //     servicesCliente.save(c);
        // }
        servicesCliente.save(c);
        return "redirect:/clientes"; 
    }

    @GetMapping("/eliminarCliente/{id_cliente}")
    public String eliminarCliente(Model model, @PathVariable int id_cliente){
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();
        Boolean eliminarCliente = true;

        for(Vehiculo v:vehiculos){
            if(v.getCliente().getId_cliente().equals(id_cliente)){
                eliminarCliente = false;
            }
        }
        
        if(eliminarCliente == true){
            System.out.println("si PODES ELIMINAR, NO ESTA RELACIONADO");
            servicesCliente.delete(id_cliente);
        }else{System.out.println("NO PODES ELIMINAR, ESTA RELACIONADO");}
        
        // Antes de la redirección
        String mensaje = eliminarCliente ? "true" : "false";
        String urlRedireccion = "redirect:/clientes?mensaje=" + mensaje;
        return urlRedireccion;
    }

    @GetMapping("/editarCliente/{id_cliente}")
    public String editarCliente(@PathVariable int id_cliente, Model model){
        Optional<Cliente> optionalCliente = servicesCliente.listarIdCliente(id_cliente);

        
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            model.addAttribute("cliente", cliente);
        } else {
           
        }

        return "editarCliente"; 
    }
    
    @PostMapping("/buscarCliente")
    public String buscarCliente(Model model, @RequestParam("datoCliente") String dni){
        List<Cliente> clientes = servicesCliente.listarClientes();
        List<Cliente> clientesBuscados = new ArrayList<Cliente>();
        
        if(dni != null && !dni.isEmpty() && dni.matches("\\d+")){
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
        return "buscarCliente";

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
        // List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        // Boolean guardarTecnico = true;

        // for(Tecnico tec:tecnicos){
        //     if(tec.getLegajo().toUpperCase().equals(t.getLegajo().toUpperCase()))
        //         guardarTecnico = false;
        // }

        // if (guardarTecnico == true){
        //     servicesTecnico.save(t);
        // }
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

        
        if (optionalTecnico.isPresent()) {
            Tecnico tecnico = optionalTecnico.get();
            model.addAttribute("tecnico", tecnico);
        } else {
            
        }

        model.addAttribute("vehiculos", vehiculos);
        return "editarTecnico"; 
    }

    @PostMapping("/buscarTecnico")
    public String buscarTecnico(Model model, @RequestParam("datosBuscados") String legajo){
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        List<Tecnico> tecnicosEncontrados = new ArrayList<Tecnico>();
        
        for (Tecnico t:tecnicos){
            if(t.getLegajo().toUpperCase().equals(legajo.toUpperCase())){
                tecnicosEncontrados.add(t);
            }
        }

        if (tecnicosEncontrados.size() == 0){
            tecnicosEncontrados = tecnicos;
        }

       
        model.addAttribute("tecnicos", tecnicosEncontrados);

        return "buscarTecnico";
    }
}







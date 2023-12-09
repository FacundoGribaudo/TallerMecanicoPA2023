import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import com.Proyecto.TallerMecanico.TallerMecanicoApplication;
import com.Proyecto.TallerMecanico.domain.Marca;
import com.Proyecto.TallerMecanico.domain.Modelo;
import com.Proyecto.TallerMecanico.services.ModeloServices;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TallerMecanicoApplication.class)
@ComponentScan(basePackages = "com.Proyecto.TallerMecanico")
public class ModeloTest {
    
    @Autowired
    private ModeloServices testModelo; 


    /*
     * TEST1 - PARA VERIFICAR QUE ES POSIBLE GUARDAR OBJETOS "MARCA"
     */
    @Test
    public void  testGuardarModelo(){

        //Objeto marca para crear el modelo de prueba
        Marca mar = new Marca("MarcaPrueba", "Activo");
       //Modelo
        Modelo m = new Modelo("Modelo1", "Test", mar);

        //Evaluo test
        Integer resultado = testModelo.save(m);
        Assertions.assertEquals(1, resultado);

    }

    /*
     * TEST2 - PARA VERIFICAR QUE CADA MODELO PERTENECE A UNA MARCA
     */
    @Test
    public void testMarcaAsociada(){

        List<Modelo> modelos = testModelo.listarModelos();

        Boolean resultado = false; 

        for (Modelo m:modelos){
            if (m.getMarca().getNombre() != ""){
                resultado = true;
            } 
        }

        Assertions.assertEquals(true, resultado);
    }

}

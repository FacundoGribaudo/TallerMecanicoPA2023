import java.math.BigInteger;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import com.Proyecto.TallerMecanico.TallerMecanicoApplication;
import com.Proyecto.TallerMecanico.domain.Cliente;
import com.Proyecto.TallerMecanico.services.ClienteServices;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TallerMecanicoApplication.class)
@ComponentScan(basePackages = "com.Proyecto.TallerMecanico")
public class ClienteTest {
    
    @Autowired
    private ClienteServices testCliente; 

    /*
     * TEST1 PARA VERIFICAR QUE ES POSIBLE GUARDAR OBJETOS "CLIENTE"
     */
    @Test
    public void  testGuardarCliente(){

        //Cliente Prueba
        Cliente c = new Cliente("Cliente", "TEST", BigInteger.valueOf(43883934), BigInteger.valueOf(43883934), "deheza", "1", "2");

        //Evaluo test
        Integer resultado = testCliente.save(c);
        Assertions.assertEquals(1, resultado);

    }
}

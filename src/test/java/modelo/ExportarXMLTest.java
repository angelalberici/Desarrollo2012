/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

// ManejoXML.exportar(DbCon.getInstance().exportarBaseDeDatosDelUsuario(correo));
/**
 *
 * @author Legna Filloy
 */
public class ExportarXMLTest extends TestCase {
    
     static String correo ="";
    public ExportarXMLTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
      //super.setUp();
        correo = "angelalberici@gmail.com";    
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of exportar method, of class ManejoXML.
     */
    public void testExportar() throws IOException {
        System.out.println("Exportar");
        Usuario usuario = DbCon.getInstance().exportarBaseDeDatosDelUsuario(correo); 
        boolean exportResult = true;
        boolean result = ManejoXML.exportar(usuario);
        assertEquals("La prueba ha sido exitosa ",exportResult, result);
 
    }

}

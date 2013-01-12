/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Legna Filloy
 */
public class ImportarXMLTest extends TestCase {
    
     static String correo ="";
    public ImportarXMLTest(String testName) {
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
     * Test of importar method, of class ManejoXML.
     */
    public void testImportar() throws IOException {
        System.out.println("Importar");
        Usuario usuario =  ManejoXML.importar(correo); 
        boolean importResult = true;
        boolean result = DbCon.getInstance().importarBaseDeDatosDelUsuario(usuario);
        assertEquals(importResult, result);
       
     
    }

}

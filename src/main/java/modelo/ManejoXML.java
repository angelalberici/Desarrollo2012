/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.*;

/**
 *
 * @author LAB_A112
 */
public class ManejoXML {
    
    
    public static boolean exportar (Usuario usuario){
        XStream xstream = new XStream(new DomDriver()); 
        xstream.alias("usuario", Usuario.class);
        xstream.alias("libreta", Libreta.class);
        xstream.alias("nota",Nota.class);
        xstream.alias("adjunto",Adjunto.class);
        xstream.alias("tag",Tag.class); 
        
        String xml = xstream.toXML(usuario);
      
        // OJOOO CREAR constante ruta 
       File file = new File ("C:\\Documents and Settings\\LAB_A112\\Mis documentos\\Spring3Example\\salida.xml");
    try{
        FileWriter w = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(w);
        PrintWriter wr = new PrintWriter(bw);	
        wr.write(xml);//escribimos en el archivo 
         wr.close();
         bw.close();
         return true;
      }catch(IOException e){
      e.printStackTrace();
      return false;
      }
                   
   }
    
    
    
    
}

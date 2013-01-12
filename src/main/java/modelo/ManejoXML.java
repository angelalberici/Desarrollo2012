/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Legna Filloy
 */
public class ManejoXML {
  
    static Logger logger = Logger.getLogger("com.ManejoXML");
    private static String path = "C:\\desarrollo\\";

    public static boolean exportar(Usuario usuario) {
    
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("usuario", Usuario.class);
        xstream.alias("libreta", Libreta.class);
        xstream.alias("nota", Nota.class);
        xstream.alias("adjunto", Adjunto.class);
        xstream.alias("tag", Tag.class);

        String xml = xstream.toXML(usuario);
     
        File file = new File(path);
        try {
            if (usuario.getCorreo()== null){
                return false;
            }
            else {
           
            FileWriter w = new FileWriter(file+"\\"+usuario.getCorreo()+".xml");
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write(xml);//escribimos en el archivo 
            wr.close();
            bw.close();
            }
           
        } catch (IOException e) {
            e.printStackTrace();
             logger.info("No exportó la informacion del usuario "+usuario.getCorreo()+ ", error al leer el archivo");
        
            return false;
        }
          logger.info("Se exportó toda la informacion del usuario "+usuario.getCorreo());
        return true;
    }

    public static Usuario importar(String correo) {

        XStream xstream = new XStream(new DomDriver());
        xstream.alias("usuario", Usuario.class);
        xstream.alias("libreta", Libreta.class);
        xstream.alias("nota", Nota.class);
        xstream.alias("adjunto", Adjunto.class);
        xstream.alias("tag", Tag.class);
        String xml = "";
        
        try {
            // Abrimos el archivo
            FileInputStream fstream = new FileInputStream(path+correo+".xml");
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            StringBuffer answer = new StringBuffer();
            String strLinea;

            while ((strLinea = buffer.readLine()) != null) {

                answer.append(strLinea + "\n");

            }

            entrada.close();
            fstream.close();
            xml = answer.toString();
            System.out.println(xml);
            Usuario newInfo = (Usuario) xstream.fromXML(xml);
            
            logger.info("Se obtuvo la data para importarla al usuario "+correo);
            return newInfo;

        } catch (Exception e) { //Catch de excepciones
            e.printStackTrace();
            logger.info("No se obtuvo la data para importarla al usuario "+correo);
           
             return null;

        }
       

    }
}

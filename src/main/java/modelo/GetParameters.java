package modelo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class GetParameters {

 
/**
 * 
 * @param token recibe el token para hacer un get en la pagina https://www.googleapis.com/oauth2/v1/userinfo para obtener el correo del usuario
 * @return retorna el correo del usuario
 */
    public String sendGetRequest(String token) {
        
        String link = "https://www.googleapis.com/oauth2/v1/userinfo?access_token="+token;
        String mail = "";
        
// Send a GET request to the servlet
            
            try {
// Construct data
                StringBuffer data = new StringBuffer();

// Send data
      
                URL url = new URL(link);
              //  System.out.println(url);
                URLConnection conn = url.openConnection();
            
// Get the response
                
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                
                 String line ="";
                 String[] split1,split2;
                 int CONT = 0; 
                while ((line = rd.readLine()) != null) {
                     if (CONT==2){    
                      split1 = line.split(",");
                      split2 = split1[0].split(":");
                         for (int i = 2;i<split2[1].length()-1;i++) {
                            mail = mail+split2[1].charAt(i);
                         }
                  //System.out.println("el mail "+mail);
                  return mail;
               // break;
                      }
                  CONT++;
                  sb.append(line);
                }
                rd.close();
                //result = sb.toString();
                
            } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
    }
        return mail;
  }
        
}

package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PostParameters {
    
   /**
 * 
 * @param code recibe el code para hacer un POST y obtener el token que se necesitará para acceder a los datos del usuario
 * @return  retorna el token 
 */
    public String sendPostRequest(String code) throws IOException {
        
        //Build parameter string
         Usuario.getInstance().setCode(code);
        String data = "code="+code+"&client_id=830695509204-746t1u95vf6reenns3u53kog8as9e17n.apps.googleusercontent.com&client_secret=GEK8rpk0Z3qiGk2_Ya7a5mdC&redirect_uri=http://localhost:8080/SpringMVC/autenticacion.htm&grant_type=authorization_code";
        String token = "";
        try {
            
            // Send the request
            URL url = new URL("https://accounts.google.com/o/oauth2/token");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
           
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            
            //write parameters
            writer.write(data);
            writer.flush();
            
            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line="";
            String[] split1,split2;
            int CONT = 0;
            while ((line = reader.readLine()) != null) {

              if (CONT==1){    
                split1 = line.split(",");
                split2 = split1[0].split(":");
                for (int i = 2;i<split2[1].length()-1;i++) {
                    token = token+split2[1].charAt(i);
                }
                Usuario.getInstance().setToken(token);
                return token;  
 
              }
                  CONT++;
                answer.append(line);
                                    
                
            }
            
            writer.close();
            reader.close();
                        
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return token;
    }

}
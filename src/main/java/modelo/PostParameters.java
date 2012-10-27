package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 * Main.java
 *
 * @author www.javadb.com
 */
public class PostParameters {
    
    /**
     * Extends the size of an array.
     */
    public String sendPostRequest(String code) {
        
        //Build parameter string
      
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
                //System.out.println(line);
              if (CONT==1){    
                split1 = line.split(",");
                split2 = split1[0].split(":");
                for (int i = 2;i<split2[1].length()-1;i++) {
                    token = token+split2[1].charAt(i);
                }
                
                 // System.out.println("el token "+token);
                return token;  
               // break;
              }
                  CONT++;
                answer.append(line);
                                    
                
            }
            
            writer.close();
            reader.close();
            
            //Output the response
            //System.out.println(answer.toString());
            
            //System.out.println(content);
            
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return token;
    }

}
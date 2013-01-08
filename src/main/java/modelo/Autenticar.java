package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class Autenticar {

    /**
     *
     * @param code recibe el code para hacer un POST y obtener el token que se
     * necesitará para acceder a los datos del usuario
     * @return retorna el token
     */
    static JSONObject jObj = null;
    static JSONObject jObj2 = null;
    static String token = "";
    static String correo = "";

    public void getAccesToken(String code) throws IOException, JSONException {

        //Build parameter string
        Usuario.getInstance().setCode(code);
        String data = "code=" + code + "&client_id=830695509204-746t1u95vf6reenns3u53kog8as9e17n.apps.googleusercontent.com&client_secret=GEK8rpk0Z3qiGk2_Ya7a5mdC&redirect_uri=http://localhost:8080/SpringMVC/autenticacion.htm&grant_type=authorization_code";

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
            String line = "";
            String json = "";

            while ((line = reader.readLine()) != null) {
                answer.append(line + "\n");
            } // answer tiene todooo

            writer.close();
            reader.close();
            json = answer.toString();

            try {
                jObj = new JSONObject(json);
                token = jObj.getString("access_token");
                Usuario.getInstance().setToken(token);
                correo = getMail(token);
                Usuario.getInstance().setCorreo(correo);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public String getMail(String token) {

        String link = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token;
        String mail = "";

// Send a GET request to the servlet

        try {
// Construct data
            StringBuffer data = new StringBuffer();
            URL url = new URL(link);
            URLConnection conn = url.openConnection();

// Get the response

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();

            String line = "";
            String json = "";

            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            json = sb.toString();
            try {
                jObj2 = new JSONObject(json);
                mail = jObj2.getString("email");
                return mail;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return mail;
    }
}

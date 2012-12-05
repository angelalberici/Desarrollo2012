/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.DbCon;
import modelo.Libreta;
import modelo.Nota;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Carlos
 */
public class LibretaDAOMySQL implements LibretaDAO {

    private static List<Libreta> LibretaList;
    DriverManagerDataSource dataSource;
    JdbcTemplate jdbcTemplate;
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("modelo.LibretaDaoMySQL");

    public LibretaDAOMySQL() throws IOException {       
        jdbcTemplate = DbCon.getInstance().getJdbcTemplate();
        dataSource =  DbCon.getInstance().getDriverManagerDataSource();
    }

    /**
     * procedimiento para cargar todas libretas de un usuario
     * @param correo  el correo del usuario que se registra
     */
    @Override
    public void cargarLibreta(String correo) {
        LibretaList = new LinkedList<Libreta>();

        List<Map> rows = jdbcTemplate.queryForList("select * from libreta where usuario_id='" + correo + "' order by nombre ");
        for (Map row : rows) {
            Libreta libreta = new Libreta();
            libreta.setId((Integer) row.get("id"));
            libreta.setNombre((String) row.get("nombre"));
            libreta.setUsuario_id((String) row.get("usuario_id"));
            LibretaList.add(libreta);
        }
        logger.info("Devolviendo libretas del correo: "+correo);

    }

    /*
     * procedimiento para eliminar una libreta
     * @param id el id de la libreta
     */
    @Override
    public void eliminarLibreta(String id) {
        int personas = jdbcTemplate.update("delete from libreta where id='" + id + "'");
        logger.info("Eliminada con éxito la libreta de id: "+id);
    }

    /**
     * procedimiento para insertar un libreta
     * @param nombre el nombre que se le va a dar a la libreta
     * @param correo  el correo del usuario que creo esa libreta
     */
    @Override
    public void insertarLibreta(String nombre, String correo) {
        int personas = jdbcTemplate.update("insert into libreta (nombre,usuario_id) values ('" + nombre + "','" + correo + "')");
        logger.info("Insertada con éxito la libreta "+nombre+" en el correo "+correo);
    }
    
    /**
     * procedimiento para modificar una libreta
     * @param id el id de la libreta
     * @param nombre  el nombre nuevo que se le quiere dar a la libreta
     */
    @Override
    public void modificarLibreta(int id, String nombre) {
        int personas = jdbcTemplate.update("update libreta set nombre='"+nombre+"' where id="+id+"");
        logger.info("Se modificó la libreta de id: "+id+", el nuevo nombre es "+nombre);
    }

    /**
     * funcion que retorna la lista con todas las libretas de la Base de datos
     * @return  retorna la lista con las libretas de la BD de un usuario en particular
     */
    @Override
    public List<Libreta> getLibretaList() {
        return LibretaList;
    }
}

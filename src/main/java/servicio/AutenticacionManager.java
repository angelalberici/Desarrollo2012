/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import modelo.DbCon;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Carlos
 */
public class AutenticacionManager {
    
    DriverManagerDataSource dataSource;
    JdbcTemplate jdbcTemplate;

    /**
     * procedimiento para insertar un usuario
     * @param correo el correo del usuario 
     * @throws IOException  
     */
    public void insertarUsuario(String correo) throws IOException  {
        String verificar = "";
        jdbcTemplate = DbCon.getInstance().getJdbcTemplate();
        dataSource =  DbCon.getInstance().getDriverManagerDataSource();
         List<Map> rows = jdbcTemplate.queryForList("select * from usuario where correo='" + correo + "'");
        for (Map row : rows) {
           verificar = (String)row.get("correo");
        }
       if (verificar.equals("")){ 
         int personas = jdbcTemplate.update("insert into usuario values ('" + correo + "')");
       }
    }
}


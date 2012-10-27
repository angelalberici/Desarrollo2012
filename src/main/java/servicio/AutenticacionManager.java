/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.io.IOException;
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

    public void insertarUsuario(String correo) throws IOException {
        jdbcTemplate = DbCon.getInstance().getJdbcTemplate();
        dataSource =  DbCon.getInstance().getDriverManagerDataSource();
        int personas = jdbcTemplate.update("insert into usuario values ('" + correo + "'");
    }
}


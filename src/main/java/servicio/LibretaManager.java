/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import modelo.Libreta;
import modelo.Nota;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Carlos
 */
public class LibretaManager {

    private static List<Libreta> LibretaList;

    public LibretaManager() {
        
        LibretaList = new LinkedList<Libreta>();
        
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/desarrollo");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

// La clase Spring con la Connection
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // operaciones DML
        
        //int personas = jdbcTemplate.update("insert into dummy values ('otro','probando',1)");      
        //int personas1 = jdbcTemplate.update("delete from dummy where nombre='a'");
        //int personas2 = jdbcTemplate.update("update dummy set='q' where nombre='g'");
        List<Map> rows = jdbcTemplate.queryForList("select * from libreta");
        for (Map row : rows) {
            Libreta libreta = new Libreta();
            libreta.setId((String)row.get("id"));
            libreta.setNombre((String)row.get("nombre"));
            libreta.setUsuario_id((String)row.get("usuario_id"));
            LibretaList.add(libreta);
        }
       
    } 

    public List<Libreta> getLibretaList() {
        return LibretaList;
    }
}

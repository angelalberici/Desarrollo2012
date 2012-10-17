/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.sun.org.apache.bcel.internal.generic.Select;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.tree.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Angel Alberici
 */
public class DbCon {
    DriverManagerDataSource dataSource;
    
    public DbCon(){
     dataSource= new DriverManagerDataSource("org.gjt.mm.mysql.Driver",
                "jdbc:mysql://localhost/desarrollo",
                "root",
                "root");
    }
    
    public String consultaImprimir() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT id,titulo,texto FROM NOTA";
        List<Map> rows = jdbcTemplate.queryForList(sql);
        for (Object object : rows) {
            Nota a = (Nota) object;
            
            System.out.println("Object is " + a.getId());
            return a.getId()+"";
        }
        
        List<Nota> notas = new ArrayList<Nota>();
        return "";
    }



  
    
// La clase Spring con la Connection
//    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//    int personas = jdbcTemplate.update("update dummy set col=30; ");
    //Haciendo select e imprimiendo
    

    

//        for (Map row : rows) {
//            Nota nota = new Nota();
//            nota.setId((Integer) (row.get("id")));
//            nota.setTitulo((String) row.get("titulo"));
//            nota.setTexto((String) row.get("texto"));
//            notas.add(nota);
//        }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.sun.org.apache.bcel.internal.generic.Select;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.tree.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Angel Alberici
 */
public class DbCon {

    DriverManagerDataSource dataSource;
    JdbcTemplate jdbcTemplate;

    public DbCon() {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/desarrollo");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Buscar la lista de todas las notas en la BD y devolverla
     *
     * @return
     */
    public List<Nota> entregarTodasLasNotas() {


        String sql = "SELECT id,titulo,texto FROM NOTA";

        List<Nota> listaNota = new ArrayList<Nota>();

        List<Map> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Nota nota = new Nota();
            nota.setId((String) (row.get("id")));
            nota.setTitulo((String) row.get("titulo"));
            nota.setTexto((String) row.get("texto"));
            listaNota.add(nota);
        }

        return listaNota;
    }

    /**
     * Se elimina una nota particular, todos sus adjuntos y todas sus
     * asociaciones con los tags
     *
     * @param id
     * @return
     */
    public int eliminarNota(String id) {
        //ELIMINAR EN GOOGLE DRIVE adjuntos de la nota OJO
        try {
            return jdbcTemplate.update("delete from nota where id=" + id);
        } catch (Exception e) {
            // No existe la nota que se quiere eliminar.
            return 0;
        }

    }

    /**
     * Se inserta la nota en la base de datos
     *
     * @param nota
     * @param tags si no hay lista, se pasa null
     * @param adjuntos si no hay adjuntos, se pasa null
     * @return
     */
    public int crearNota(Nota nota, List<Tag> tags, List<Adjunto> adjuntos) {
       try{
        if (nota != null) {
            jdbcTemplate.update("insert into nota (id,titulo,texto,fecha,libreta_id) values('" + nota.getId()
                    + "','" + nota.getTitulo() + "','" + nota.getTexto() + "', CURDATE(),'"+
                    nota.getLibreta_id() + "')");
            modificarTagsDeNota(nota.getId(), tags);
            modificarAdjuntosDeNota(nota.getId(), adjuntos);
            return 1;
        }
        return 0;
       }catch(Exception e){
       
           //Ya existe la primary key, esto no deberia pasar usando google drive
           return 0;
       }
    }

    /**
     * Se agrega la nueva lista de tags a la nota especificada
     *
     * @param notaid id de la nota a la que pertenece la asociacion con tag
     * @param tags lista de tags asociados a esa nota
     * @return
     */
    public int modificarTagsDeNota(String notaid, List<Tag> tags) {

        if (tags != null) {
            jdbcTemplate.update("delete from nota_tag where nota_id='" + notaid+"'");
            for (Tag tag : tags) {
                try {
                    jdbcTemplate.update("insert into tag(nombre) values ('" + tag.getNombre() + "')");
                } catch (Exception e) {
                    //Si ya existe tag, se genera un error pero no es relevante
                }
                jdbcTemplate.update("insert into nota_tag(nota_id,tag_nombre) values ('" + notaid + "','" + tag.getNombre() + "')");
            }
            return 1;
        }


        return 0;
    }

    /**
     * Se agrega la nueva lista de adjuntos a la nota especificado, no son los
     * archivos de google drive es la parte de la base de datos
     *
     * @param notaid
     * @param adjuntos
     * @return
     */
    public int modificarAdjuntosDeNota(String notaid, List<Adjunto> adjuntos) {
        if (adjuntos != null) {
            jdbcTemplate.update("delete from adjunto where nota_id='" + notaid+"'");
            for (Adjunto adjunto : adjuntos) {
                jdbcTemplate.update("insert into adjunto(id,nombre,nota_id) values ('" + adjunto.getId() + "','" + adjunto.getNombre() + "','" + notaid + "')");
            }
            return 1;
        }
        return 0;
    }
}

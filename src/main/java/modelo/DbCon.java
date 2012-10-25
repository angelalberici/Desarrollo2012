/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.ParentReference;
import com.mkyong.common.controller.GoogleDrive;
import com.sun.org.apache.bcel.internal.generic.Select;
import java.io.IOException;
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
    GoogleDrive goodledrive;

    public DbCon() throws IOException {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/desarrollo");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        jdbcTemplate = new JdbcTemplate(dataSource);
        goodledrive = new GoogleDrive();
    }

    /**
     * Buscar una nota por su id
     *
     * @return una Nota con el id que se busca
     */
    public Nota entregarNota(Integer id) {

        try {
            String sql = "SELECT id,titulo,texto,fecha FROM NOTA where id=" + id;
       Nota notab;     
        List<Map> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Nota nota = new Nota();
            nota.setId((Integer) (row.get("id")));
            nota.setTitulo((String) row.get("titulo"));
            nota.setTexto((String) row.get("texto"));
            nota.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) row.get("fecha")));
            System.out.println(nota.getId());
            System.out.println(nota.getFecha());
            System.out.println(nota.getTitulo());
            
            return nota;
        }
 return null;
        } catch (Exception e) {
//No existe la nota
            return null;
        }

    }

    /**
     * Buscar la lista de todas las notas en la BD y devolverla
     *
     * @return
     */
    public List<Nota> entregarTodasLasNotas() {


        String sql = "SELECT id,titulo,texto,fecha FROM NOTA";

        List<Nota> listaNota = new ArrayList<Nota>();

        List<Map> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Nota nota = new Nota();
            nota.setId((Integer) (row.get("id")));
            nota.setTitulo((String) row.get("titulo"));
            nota.setTexto((String) row.get("texto"));
            nota.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) row.get("fecha")));
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
        try {
            if (nota != null) {

                if (nota.getId() != null) {
                    modificarNota(nota);
                } else {
                    jdbcTemplate.update("insert into nota (titulo,texto,fecha,libreta_id) values('" + nota.getTitulo() + "','" + nota.getTexto() + "', CURDATE(),'"
                            + nota.getLibreta_id() + "')");
                }

                modificarTagsDeNota(nota.getId(), tags);
                modificarAdjuntosDeNota(nota.getId(), adjuntos);

                return 1;
            }
            return 0;
        } catch (Exception e) {

            //Ya existe la primary key, esto no deberia pasar usando google drive
            return 0;
        }
    }
/**
 * Se le pasa la nota a modificar y se actualiza la nota original con esta nueva nota
 * @param nota actualizacion de la nota anterior
 * @return 
 */
    public int modificarNota(Nota nota) {
        jdbcTemplate.update("update nota set titulo='" + nota.getTitulo() + "',texto='" + nota.getTexto()
                + "',fecha=NOW() where id=" + nota.getId());
        return 0;
    }

    /**
     * Sirve para actualizar el campo fecha cada vez que se haga una
     * modificacion en una nota
     *
     * @param notaid
     * @return 1 si fue exitoso, 0 si no
     */
    public int actualizarFecha(Integer notaid) {
        try {
            jdbcTemplate.update("update nota set fecha=NOW() where id=" + notaid);
            return 1;
        } catch (Exception e) {
            //No existe la nota que se trata de modificar
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
    public int modificarTagsDeNota(Integer notaid, List<Tag> tags) {

        if (tags != null) {
            jdbcTemplate.update("delete from nota_tag where nota_id='" + notaid + "'");
            for (Tag tag : tags) {
                try {
                    jdbcTemplate.update("insert into tag(nombre) values ('" + tag.getNombre() + "')");
                    actualizarFecha(notaid);
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
    public int modificarAdjuntosDeNota(Integer notaid, List<Adjunto> adjuntos) {
        if (adjuntos != null) {

            jdbcTemplate.update("delete from adjunto where nota_id='" + notaid + "'");
            for (Adjunto adjunto : adjuntos) {
                jdbcTemplate.update("insert into adjunto(id,nombre,nota_id) values ('" + adjunto.getId() + "','" + adjunto.getNombre() + "','" + notaid + "')");
            }
            actualizarFecha(notaid);
            return 1;
        }
        return 0;
    }
}

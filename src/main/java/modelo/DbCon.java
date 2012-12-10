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
import org.apache.log4j.Logger;
import javax.swing.tree.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Clase encargada del control absoluto de todas las CRUD contra la BD
 *
 * @author Angel Alberici
 */
public class DbCon implements NotaDAO {

    Logger logger = Logger.getLogger("com.DbCon");
    DriverManagerDataSource dataSource;
    JdbcTemplate jdbcTemplate;
    private static DbCon dbConInstance;

    private DbCon() throws IOException {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/desarrollo");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static DbCon getInstance() throws IOException {
        if (dbConInstance == null) {
            dbConInstance = new DbCon();
        }
        if (dbConInstance != null) {
            return dbConInstance;
        }

        return null;
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public DriverManagerDataSource getDriverManagerDataSource() {
        return this.dataSource;
    }

    /**
     * Buscar una nota por su id y devolverla
     *
     * @return una Nota con el id que se busca
     */
    @Override
    public Nota entregarNota(Integer id) {

        try {
            List<Tag> tags = new ArrayList<Tag>();
            List<Adjunto> adjuntos = new ArrayList<Adjunto>();
            String sql = "SELECT id,titulo,texto,fecha FROM NOTA where id=" + id;
            Nota notab;
            List<Map> rows = jdbcTemplate.queryForList(sql);
            Nota nota = new Nota();
            for (Map row : rows) {

                nota.setId((Integer) (row.get("id")));
                nota.setTitulo((String) row.get("titulo"));
                nota.setTexto((String) row.get("texto"));
                nota.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) row.get("fecha")));



            }
            sql = "select t.* from nota_tag nt, tag t where t.nombre=nt.tag_nombre and nt.nota_id=" + id;
            rows = jdbcTemplate.queryForList(sql);
            for (Map row : rows) {
                Tag tag = new Tag();
                tag.setNombre((String) (row.get("nombre")));
                tags.add(tag);

            }
            nota.setTags(tags);

            sql = "select id,nombre from adjunto where nota_id=" + id;
            rows = jdbcTemplate.queryForList(sql);
            for (Map row : rows) {
                Adjunto adjunto = new Adjunto();
                adjunto.setId((String) (row.get("id")));
                adjunto.setNombre((String) (row.get("nombre")));
                adjunto.setNota_id(id + "");
                adjuntos.add(adjunto);

            }
            nota.setAdjuntos(adjuntos);

            logger.info("Se entregó correctamente la nota: " + nota.getId() + ", fecha: " + nota.getFecha() + ", título: " + nota.getTitulo());
            return nota;

        } catch (Exception e) {
//No existe la nota
            logger.error("La nota: " + id + " no existe");
            return null;
        }

    }

    /**
     * 
     * @param palabra, representa la palabra que coloca en el buscador
     * @param correo el correo del usuario
     * @return 
     */
    @Override
    public List<Nota> entregarNotasBusqueda(String palabra, String correo) {

        String sql = "SELECT x. * FROM "
                + "((SELECT n.fecha, n.id, n.titulo, n.texto, n.libreta_id FROM nota n, libreta l, usuario u WHERE ((n.titulo LIKE '%" + palabra + "%' OR n.texto LIKE '%" + palabra + "%') AND l.usuario_id = '" + correo + "' AND n.libreta_id = l.id) ORDER BY fecha DESC) "
                + "UNION "
                + "(SELECT n.fecha, n.id, n.titulo, n.texto, n.libreta_id FROM nota n, adjunto a, libreta l, usuario u WHERE (a.nombre LIKE '%" + palabra + "%' AND n.id = a.nota_id AND l.usuario_id = '" + correo + "' AND n.libreta_id = l.id) ORDER BY n.fecha DESC) "
                + "UNION "
                + "(SELECT n.fecha, n.id, n.titulo, n.texto, n.libreta_id FROM tag t, nota n, nota_tag nt, libreta l, usuario u WHERE (t.nombre LIKE '%" + palabra + "%' AND n.id = nt.nota_id AND t.nombre = nt.tag_nombre AND l.usuario_id = '" + correo + "' AND n.libreta_id = l.id) ORDER BY n.fecha DESC))x "
                + "ORDER BY x.fecha DESC";

        List<Nota> listaNota = new ArrayList<Nota>();

        List<Map> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Nota nota = new Nota();
            nota.setId((Integer) (row.get("id")));
            nota.setTitulo((String) row.get("titulo"));
            nota.setLibreta_id((Integer) row.get("libreta_id"));
            if (nota.getTitulo().length() < 50) {
                nota.setTitulo(nota.getTitulo().substring(0, nota.getTitulo().length()));
            } else {
                nota.setTitulo(nota.getTitulo().substring(0, 50) + "...");
            }
            nota.setTexto((String) row.get("texto"));
            if (nota.getTexto().length() < 200) {
                nota.setTexto(nota.getTexto().substring(0, nota.getTexto().length()));
            } else {
                nota.setTexto(nota.getTexto().substring(0, 200) + "...");
            }

            nota.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) row.get("fecha")));
            listaNota.add(nota);
        }
        return listaNota;
    }

    /**
     * Buscar la lista de todas las notas en la BD y devolverla
     *
     * @return Lista de notas actual de la BD
     */
    @Override
    public List<Nota> entregarTodasLasNotas(Integer libretaid) {


        String sql = "SELECT id,titulo,texto,fecha FROM NOTA where libreta_id=" + libretaid;

        List<Nota> listaNota = new ArrayList<Nota>();

        List<Map> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Nota nota = new Nota();
            nota.setId((Integer) (row.get("id")));
            nota.setTitulo((String) row.get("titulo"));
            nota.setLibreta_id(libretaid);
            if (nota.getTitulo().length() < 50) {
                nota.setTitulo(nota.getTitulo().substring(0, nota.getTitulo().length()));
            } else {
                nota.setTitulo(nota.getTitulo().substring(0, 50) + "...");
            }
            nota.setTexto((String) row.get("texto"));
            if (nota.getTexto().length() < 200) {
                nota.setTexto(nota.getTexto().substring(0, nota.getTexto().length()));
            } else {
                nota.setTexto(nota.getTexto().substring(0, 200) + "...");
            }

            nota.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) row.get("fecha")));
            listaNota.add(nota);

        }
        logger.info("Se entregaron correctmanete las notas de la libreta: " + libretaid);
        return listaNota;
    }

    /**
     * Se elimina una nota particular, todos sus adjuntos y todas sus
     * asociaciones con los tags
     *
     * @param id pertenece a la nota que se quiere eliminar
     * @return 0 si existio algun problema, 1 si fue exitosa
     */
    @Override
    public int eliminarNota(Integer id) {
        try {
            logger.info("Se eliminó correctmanete la nota de id: " + id);
            return jdbcTemplate.update("delete from nota where id=" + id);

        } catch (Exception e) {
            logger.error("La nota que se intenta eliminar no existe, el id de la nota: " + id);
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
     * @return 0 si existio algun problema, 1 si fue exitosa
     */
    @Override
    public int crearNota(Nota nota, List<Tag> tags, List<Adjunto> adjuntos) {
        try {
            if (nota != null) {

                if (nota.getId() != null) {
                    modificarNota(nota);
                } else {
                    jdbcTemplate.update("insert into nota (titulo,texto,fecha,libreta_id) values('" + nota.getTitulo() + "','" + nota.getTexto() + "', CURDATE(),"
                            + nota.getLibreta_id() + ")");
                }

                modificarTagsDeNota(nota.getId(), tags);

                modificarAdjuntosDeNota(nota.getId(), adjuntos);
                logger.info("Nota creada con éxito. id:" + nota.getId() + ", título: " + nota.getTitulo());
                return 1;
            }
            logger.info("Se creó correctmanete la nota de id: " + nota.getId() + ", título: " + nota.getTitulo());
            return 0;
        } catch (Exception e) {
            logger.error("Ya existe la primary key de la nota que se intenta registrar. No tiene sentido que este error suceda, ya que hay autoincrement como primary key.");
            //Ya existe la primary key, esto no deberia pasar usando google drive
            return 0;
        }
    }

    /**
     * Se le pasa la nota a modificar y se actualiza la nota original con esta
     * nueva nota
     *
     * @param nota actualizacion de la nota anterior
     * @return 0 si existio algun problema, 1 si fue exitosa
     */
    @Override
    public int modificarNota(Nota nota) {
        logger.info("Se modificó correctmanete la nota de id: " + nota.getId());
        jdbcTemplate.update("update nota set titulo='" + nota.getTitulo() + "',texto='" + nota.getTexto()
                + "',fecha=NOW() where id=" + nota.getId());
        return 0;

    }

    /**
     * Sirve para actualizar el campo fecha cada vez que se haga una
     * modificacion en una nota
     *
     * @param notaid se pasa Null si la nota es nueva
     * @return 1 si fue exitoso, 0 si no
     */
    public int actualizarFecha(Integer notaid) {
        try {
            if (notaid == null) {
                logger.debug("update nota set fecha=NOW() where id=(SELECT max( id ) FROM nota");
                jdbcTemplate.update("update nota set fecha=NOW() where id=(SELECT max( id ) FROM nota");
            } else {
                logger.debug("update nota set fecha=NOW() where id=" + notaid + ")");
                jdbcTemplate.update("update nota set fecha=NOW() where id=" + notaid + ")");
            }
            return 1;
        } catch (Exception e) {
            logger.error("Al intentar actualizar la fecha de la nota con id:" + notaid + ", no se encontró la nota");
            //No existe la nota que se trata de modificar
            return 0;
        }

    }

    /**
     * Se agrega la nueva lista de tags a la nota especificada
     *
     * @param notaid id de la nota a la que pertenece la asociacion con tag
     * @param tags lista de tags asociados a esa nota
     * @return 0 si existio algun problema, 1 si fue exitosa
     */
    @Override
    public int modificarTagsDeNota(Integer notaid, List<Tag> tags) {

        if (tags != null) {
//            logger.debug("delete from nota_tag where nota_id=(SELECT max( id ) FROM nota)");
            if (notaid != null) {
                jdbcTemplate.update("delete from nota_tag where nota_id=" + notaid);
            } else {
                jdbcTemplate.update("delete from nota_tag where nota_id=(SELECT max( id ) FROM nota)");
            }
            for (Tag tag : tags) {
                if (!tag.getNombre().replaceAll("\\s", "").equals("")) {

                    try {
                        jdbcTemplate.update("insert into tag(nombre) values ('" + tag.getNombre().replaceAll("\\s", "") + "')");
                        actualizarFecha(notaid);
                    } catch (Exception e) {
                        logger.debug("Ya existe tag con ese id:" + tag.getNombre());
                        //Si ya existe tag, se genera un error pero con controlar la excepcion se soluciona el problema
                    }
                    if (notaid != null) {
                        logger.debug("insert into nota_tag(nota_id,tag_nombre) values (" + notaid + ",'" + tag.getNombre().replaceAll("\\s", "") + "')");
                        jdbcTemplate.update("insert into nota_tag(nota_id,tag_nombre) values (" + notaid + ",'" + tag.getNombre().replaceAll("\\s", "") + "')");
                    } else {
                        jdbcTemplate.update("insert into nota_tag(nota_id,tag_nombre) values ((SELECT max( id ) FROM nota),'" + tag.getNombre().replaceAll("\\s", "") + "')");
                    }
                }

            }
            logger.info("Se modificaron los tags de la nota de id: " + notaid);
            return 1;
        }

        logger.info("Error al intentar modificar los tags de la nota de id: " + notaid);
        return 0;
    }

    /**
     * Se agrega la nueva lista de adjuntos a la nota especificado, no son los
     * archivos de google drive es la parte de la base de datos
     *
     * @param notaid
     * @param adjuntos
     * @return 0 si existio algun problema, 1 si fue exitosa
     */
    @Override
    public int modificarAdjuntosDeNota(Integer notaid, List<Adjunto> adjuntos) {
        if (adjuntos != null) {
            for (Adjunto adjunto : adjuntos) {
                if (notaid != null) {
                    jdbcTemplate.update("insert into adjunto(id,nombre,nota_id) values ('" + adjunto.getId() + "','" + adjunto.getNombre() + "'," + adjunto.getNota_id() + ")");
                } else {
                    jdbcTemplate.update("insert into adjunto(id,nombre,nota_id) values ('" + adjunto.getId() + "','" + adjunto.getNombre() + "',(SELECT ma(angry) id ) FROM nota) )");
                }
            }
            actualizarFecha(notaid);
            logger.info("Se modificaron los adjuntos de la nota de id: " + notaid);
            return 1;
        }
        logger.info("Error al intentar modificar los adjuntos de la nota de id: " + notaid);
        return 0;
    }

    /**
     * Borrar un adjunto, se necesita su ID
     *
     * @param adjuntoID
     * @return
     */
    @Override
    public int borrarAdjunto(String adjuntoID) {
        try {
            return jdbcTemplate.update("delete from adjunto where id='" + adjuntoID + "'");
        } catch (Exception e) {
            logger.info("Error al intentar borrar el adjunto con ID "+adjuntoID);
            return 0;

        }

    }

    /**
     * Se mueve nota de una libreta a otra
     *
     * @param nota nota que se mueve
     * @param libreta libreta a la que se mueve
     * @return
     */
    @Override
    public String moverNota(Integer nota, Integer libreta) {
        try {
            jdbcTemplate.update("update nota set libreta_id=" + libreta + " where id=" + nota);
            logger.info("Se movió la nota de id: " + nota + " a la libreta de id:" + libreta);
            return ("update nota set libreta_id=" + libreta + " where id=" + nota);

        } catch (Exception e) {
            // No existe la nota que se quiere eliminar.
            logger.error("Error al intentar modificar mover la nota de id: " + nota + " a la libreta de id:" + libreta);
            return null;
        }

    }
}

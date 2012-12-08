/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;

/**
 *
 * @author Angel Alberici
 */
public interface NotaDAO {

    /**
     * Se inserta la nota en la base de datos
     *
     * @param nota
     * @param tags si no hay lista, se pasa null
     * @param adjuntos si no hay adjuntos, se pasa null
     * @return 0 si existio algun problema, 1 si fue exitosa
     */
    int crearNota(Nota nota, List<Tag> tags, List<Adjunto> adjuntos);

    /**
     * Se elimina una nota particular, todos sus adjuntos y todas sus
     * asociaciones con los tags
     *
     * @param id pertenece a la nota que se quiere eliminar
     * @return 0 si existio algun problema, 1 si fue exitosa
     */
    int eliminarNota(Integer id);

    /**
     * Buscar una nota por su id y devolverla
     *
     * @return una Nota con el id que se busca
     */
    Nota entregarNota(Integer id);

    /**
     * Buscar la lista de todas las notas en la BD y devolverla
     *
     * @return Lista de notas actual de la BD
     */
    List<Nota> entregarTodasLasNotas(Integer libretaid);

    /**
     * Se agrega la nueva lista de adjuntos a la nota especificado, no son los
     * archivos de google drive es la parte de la base de datos
     *
     * @param notaid
     * @param adjuntos
     * @return 0 si existio algun problema, 1 si fue exitosa
     */
    int modificarAdjuntosDeNota(Integer notaid, List<Adjunto> adjuntos);

    /**
     * Se le pasa la nota a modificar y se actualiza la nota original con esta
     * nueva nota
     *
     * @param nota actualizacion de la nota anterior
     * @return 0 si existio algun problema, 1 si fue exitosa
     */
    int modificarNota(Nota nota);

    /**
     * Se agrega la nueva lista de tags a la nota especificada
     *
     * @param notaid id de la nota a la que pertenece la asociacion con tag
     * @param tags lista de tags asociados a esa nota
     * @return 0 si existio algun problema, 1 si fue exitosa
     */
    int modificarTagsDeNota(Integer notaid, List<Tag> tags);

    /**
     * Se mueve nota de una libreta a otra
     *
     * @param nota nota que se mueve
     * @param libreta libreta a la que se mueve
     * @return
     */
    String moverNota(Integer nota, Integer libreta);
        /**
     * Buscar la lista de las notas que coincidan con la busqueda en la BD y devolverla
     *
     * @return Lista de notas actual de la BD
     */
   List<Nota> entregarNotasBusqueda(String palabra,String correo);
}

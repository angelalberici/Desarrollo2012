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
public interface LibretaDAO {

    /**
     * procedimiento para cargar todas libretas de un usuario
     * @param correo  el correo del usuario que se registra
     */
    void cargarLibreta(String correo);

    /*
     * procedimiento para eliminar una libreta
     * @param id el id de la libreta
     */
    void eliminarLibreta(String id);

    /**
     * funcion que retorna la lista con todas las libretas de la Base de datos
     * @return  retorna la lista con las libretas de la BD de un usuario en particular
     */
    List<Libreta> getLibretaList();

    /**
     * procedimiento para insertar un libreta
     * @param nombre el nombre que se le va a dar a la libreta
     * @param correo  el correo del usuario que creo esa libreta
     */
    void insertarLibreta(String nombre, String correo);

    /**
     * procedimiento para modificar una libreta
     * @param id el id de la libreta
     * @param nombre  el nombre nuevo que se le quiere dar a la libreta
     */
    void modificarLibreta(int id, String nombre);
    
}

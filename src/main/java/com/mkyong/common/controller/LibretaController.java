/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DbCon;
import modelo.LibretaDAOMySQL;
import modelo.ManejoXML;
import modelo.Usuario;
import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import servicio.LibretaManager;

/**
 *
 * @author Carlos
 */
public class LibretaController implements Controller {

    @Override
    /**
     * controlador de las libretas, cada vez que se llame al url /libreta.htm se
     * dispara este controlador, que dependiendo de la opcion que se reciba por
     * parametro hara un eliminar, agregar o modificar una libreta
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView modelAndView = null;

        // parametros que recibe y se obtienen
        String opcion = (String) request.getParameter("opcion");
        String id = (String) request.getParameter("id");
        String nombre = (String) request.getParameter("nombrelibreta");
        String correo = Usuario.getInstance().getCorreo();
        String modificar = (String) request.getParameter("nombremodificado");

        LibretaDAOMySQL libretaManager = new LibretaDAOMySQL();
        libretaManager.cargarLibreta(correo); // cargar las libretas por medio de un correo

        
        if (request.getParameter("exportar") != null) {
            if (request.getParameter("exportar").equals("1")) {
                try {

                    ManejoXML.exportar(DbCon.getInstance().exportarBaseDeDatosDelUsuario(correo));

                    // Suponemos que es un zip lo que se quiere descargar el usuario.
                    // Aqui se hace a piñón fijo, pero podría obtenerse el fichero
                    // pedido por el usuario a partir de algún parámetro del request
                    // o de la URL con la que nos han llamado.
                    String nombreFichero = correo + ".xml";
                    String unPath = "c:/desarrollo/";

                    response.setContentType("application/zip");
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + nombreFichero + "\"");

                    InputStream is = new FileInputStream(unPath + nombreFichero);

                    IOUtils.copy(is, response.getOutputStream());

                    response.flushBuffer();

                } catch (IOException ex) {
                    // Sacar log de error.
                    ex.printStackTrace();
//                throw ex;
                }
                      
            } else {
                Usuario usuario = ManejoXML.importar(correo);
                boolean result = DbCon.getInstance().importarBaseDeDatosDelUsuario(usuario);

            }
        }
        

        if (opcion != null && id != null) {
            // si se quiere eliminar alguna libreta
            if (opcion.equals("2")) {
                libretaManager.eliminarLibreta(id);
                libretaManager.cargarLibreta(correo);
            }
        }

        // si se quiere insertar alguna libreta
        if (nombre != null && !nombre.equals("")) {
            libretaManager.insertarLibreta(nombre, correo);
            libretaManager.cargarLibreta(correo);
        }

        //si se quiere modificar alguna libreta
        if (modificar != null && !modificar.equals("")) {
            libretaManager.modificarLibreta(Integer.parseInt(id), modificar);
            libretaManager.cargarLibreta(correo);
        }

        //para acceder al jsp que permite agregar una libreta
        if (opcion != null && opcion.equals("3")) {
            modelAndView = new ModelAndView("AgregarLibreta");
            modelAndView.addObject("mail", correo);
            //para acceder al jsp que permite modificar una libreta
        } else if (opcion != null && opcion.equals("1")) {
            modelAndView = new ModelAndView("ModificarLibreta");
            modelAndView.addObject("mail", correo);
            modelAndView.addObject("idLibreta", id);
            // si no se cumple nada tan solo carga las libretas de un usuario
        } else {
            modelAndView = new ModelAndView("Libreta");
            modelAndView.addObject("LibretaList", libretaManager.getLibretaList());
            modelAndView.addObject("mail", correo);

        }

        return modelAndView;
    }
}

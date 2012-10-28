/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * controlador de las libretas, cada vez que se llame al url /libreta.htm
     * se dispara este controlador, que dependiendo de la opcion que se reciba
     * por parametro hara un eliminar, agregar o modificar una libreta
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView modelAndView = null;

 // parametros que recibe y se obtienen
        String opcion = (String) request.getParameter("opcion");
        String id = (String) request.getParameter("id");
        String nombre = (String) request.getParameter("nombrelibreta");
        String correo = (String) request.getParameter("correo");
        String modificar = (String) request.getParameter("nombremodificado");

        LibretaManager libretaManager = new LibretaManager();
        libretaManager.cargarLibreta(correo); // cargar las libretas por medio de un correo

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

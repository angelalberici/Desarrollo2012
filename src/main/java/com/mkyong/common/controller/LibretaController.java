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
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView modelAndView = null;


        String opcion = (String) request.getParameter("opcion");
        String id = (String) request.getParameter("id");
        String nombre = (String) request.getParameter("nombrelibreta");
        String correo = (String) request.getParameter("correo");
        String modificar = (String) request.getParameter("nombremodificado");

        LibretaManager libretaManager = new LibretaManager();
        libretaManager.cargarLibreta(correo);

        if (opcion != null && id != null) {
            if (opcion.equals("2")) {
                libretaManager.eliminarLibreta(id);
                libretaManager.cargarLibreta(correo);
            }
        }

        if (nombre != null && !nombre.equals("")) {
            libretaManager.insertarLibreta(nombre, correo);
            libretaManager.cargarLibreta(correo);
        }

        if (modificar != null && !modificar.equals("")) {
            libretaManager.modificarLibreta(Integer.parseInt(id), modificar);
            libretaManager.cargarLibreta(correo);
        }
        
        if (opcion != null && opcion.equals("3")) {
            modelAndView = new ModelAndView("AgregarLibreta");
            modelAndView.addObject("mail", correo);
        } else if (opcion != null && opcion.equals("1")) {
            modelAndView = new ModelAndView("ModificarLibreta");
            modelAndView.addObject("mail", correo);
            modelAndView.addObject("idLibreta", id);
        } else {
            modelAndView = new ModelAndView("Libreta");
            modelAndView.addObject("LibretaList", libretaManager.getLibretaList());
            modelAndView.addObject("mail", correo);

        }

        return modelAndView;
    }
}

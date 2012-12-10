/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DbCon;
import modelo.Usuario;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import servicio.LibretaManager;

/**
 * Le llegan 2 parametros, correo y b (notaid) 
 * Sirve para llamarse a si misma con otro parametro l (libretaid) y asi
 * mover la nota que se paso a otra libreta, de ese mismo usuario
 * @author Angel Alberici
 */
public class MoverNotaController implements Controller {

      public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse arg1) throws Exception {
  
          String correo = Usuario.getInstance().getCorreo();
        Integer nota = Integer.parseInt(request.getParameter("b"));
        if (request.getParameter("l") != null) {
            Integer libreta = Integer.parseInt(request.getParameter("l"));
            DbCon.getInstance().moverNota(nota, libreta);
        }
        LibretaManager libretaManager = new LibretaManager();
        ModelAndView modelAndView = null;

        //Cargar libretas aqui
        libretaManager.cargarLibreta(correo);
        modelAndView = new ModelAndView("MoverNota");
        modelAndView.addObject("LibretaList", libretaManager.getLibretaList());
        modelAndView.addObject("mail", correo);
        modelAndView.addObject("notaid", nota);
        return modelAndView;
    }
}


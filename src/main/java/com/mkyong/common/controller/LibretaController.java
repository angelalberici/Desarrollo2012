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
      public ModelAndView handleRequest(HttpServletRequest arg0,
            HttpServletResponse arg1) throws Exception {

        LibretaManager libretaManager = new LibretaManager();
        ModelAndView modelAndView = new ModelAndView("Libreta");
        modelAndView.addObject("LibretaList", libretaManager.getLibretaList());
        
        return modelAndView;
    }
   
}

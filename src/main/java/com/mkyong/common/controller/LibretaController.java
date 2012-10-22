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

         //1 es modificar 
        // 2 es eliminar
         LibretaManager libretaManager = new LibretaManager();
         libretaManager.cargarLibreta();
         
        String opcion = (String) request.getParameter("opcion");
        String id = (String) request.getParameter("id");
        System.out.println("la opcion "+opcion);
        System.out.println("el id  es "+id);
       if (opcion!=null){
        if (opcion.equals("1")){
            System.out.println("entro en el modificar");
        }  
       
        if (opcion.equals("2")){
            System.out.println("entro en el eliminar");
            libretaManager.eliminarLibreta(id);
            libretaManager.cargarLibreta();
        }
       }
       
        ModelAndView modelAndView = new ModelAndView("Libreta");
        modelAndView.addObject("LibretaList", libretaManager.getLibretaList());
        
        return modelAndView;
    }
   
}

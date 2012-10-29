/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import modelo.DbCon;
import modelo.Nota;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Angel Alberici
 */
@Controller("caosController")
public class CaosController {
    
    @RequestMapping(value = "/enviar.htm",method = RequestMethod.GET)
    public ModelAndView enviar(){
    
    return new ModelAndView("newjsp");
    }

    @RequestMapping(value = "/enviar.htm",method = RequestMethod.POST)
    public ModelAndView guardar(Nota nota) {

        try {
            
//            DbCon dbcon = new DbCon();
//            dbcon.crearNota(nota, null, null);
            ModelAndView modelAndView = new ModelAndView("newjsp");
            modelAndView.addObject("titulo", guardar(nota));
            
            return new ModelAndView("HelloWorldPage"); 
            
        } catch (Throwable he) {

//            loggerUtil.error("Error al registrar nuevo reporte de problema", he);
//
//            return new ModelAndView("paginaError") // esta pagina estara ubicada dentro del directorio /WEB-INF/jsp
return null;
        }
    }
}
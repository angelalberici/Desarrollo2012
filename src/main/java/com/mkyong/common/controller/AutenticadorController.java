/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Angel Alberici
 */
public class AutenticadorController implements Controller{
    
     public ModelAndView handleRequest(HttpServletRequest arg0,
            HttpServletResponse arg1) throws Exception {

        NotaManager notaManager = new NotaManager();
        ModelAndView modelAndView = new ModelAndView("notaList");
        modelAndView.addObject("notaList", notaManager.getNotaList());

        return modelAndView;
    }  
    
        public static String makeUrl(HttpServletRequest request)
{
    return request.getRequestURL().toString() + "?" + request.getQueryString();
}
    
}

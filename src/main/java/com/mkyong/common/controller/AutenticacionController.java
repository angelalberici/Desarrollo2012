/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import java.awt.Desktop;
import java.io.PrintWriter;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Autenticar;
import modelo.Usuario;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import servicio.AutenticacionManager;

/**
 *
 * @author Legna Filloy
 */
public class AutenticacionController implements Controller {
    
       /**
     * 
     * @param request recibimos el code si se le permite el acceso a la aplicacion y el error (acces_denied) si no se le permite el acceso. Segun estos parametros se direcciona a la vista correspondiente
     * @param response
     * @return
     * @throws Exception 
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
            String  error = request.getParameter("error");
            String  code = request.getParameter("code");
            String mail =""; 
            String token =""; 
            
                     
            
        ModelAndView modelAndView = null;
          if (error != null){
             modelAndView = new ModelAndView("IraInicio");
             modelAndView.addObject("irainicio",error); 
            
          }
           
          if (code != null){
            Autenticar autenticar = new Autenticar(); 
            AutenticacionManager insertar = new AutenticacionManager();
            autenticar.getAccesToken(code);
            insertar.insertarUsuario(Usuario.getInstance().getCorreo());
            
            modelAndView = new ModelAndView("Autenticacion");
            modelAndView.addObject("autenticacion",code);                          
          }
       
    return  modelAndView;
}
    
}

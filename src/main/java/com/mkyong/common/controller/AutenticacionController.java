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
import modelo.GetParameters;
import modelo.PostParameters;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import servicio.NotaManager;

/**
 *
 * @author Legna Filloy
 */
public class AutenticacionController implements Controller {
    
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
            PostParameters post = new PostParameters();  
            GetParameters get = new GetParameters();
            token = post.sendPostRequest(code); 
            mail = get.sendGetRequest(token);
            
            modelAndView = new ModelAndView("Autenticacion");
            modelAndView.addObject("autenticacion",code); 
            modelAndView.addObject("correo",mail); 
            
           // System.out.println("el mail es: "+mail);
           // System.out.println("el acces token es: "+token);
            
            
            
            
            // aqui metodo para llamar a la BD e insertar el correo 
            // crear objeto con el correo mail 
             
             
          }
       
    return  modelAndView;
}
    
}

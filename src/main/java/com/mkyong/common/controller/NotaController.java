/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DbCon;
import modelo.Nota;
import modelo.Tag;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Se controla a la vista notaList, se reciben 
 * @author Angel Alberici
 */
public class NotaController implements Controller{

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
  

        Nota nota;
              if (request.getParameter("b") != null && request.getParameter("titulo") == null 
                      && request.getParameter("texto") == null) {
            DbCon.getInstance().eliminarNota(Integer.parseInt(request.getParameter("b")));
        }
              Integer libreta = Integer.parseInt(request.getParameter("l"));
                      
           if (request.getParameter("titulo") != null && request.getParameter("texto") != null) {
            nota = new Nota();
            List<Tag> tags = null;
               System.out.println("parametro b----"+request.getParameter("b")+"------");
            if (request.getParameter("b") != null && !request.getParameter("b").equals("")) {
                nota.setId(Integer.parseInt(request.getParameter("b")));
            }
            nota.setTitulo(request.getParameter("titulo"));
            nota.setTexto(request.getParameter("texto"));
            System.out.println(request.getParameter("l") + "----------------------------");
            nota.setLibreta_id(Integer.parseInt(request.getParameter("l")));
            if (request.getParameter("tags") != null) {
                tags = formatearTags(request.getParameter("tags"));
            }
            System.out.println("TAGS::::::::::::" + tags);
            DbCon.getInstance().crearNota(nota, tags, null);            
        }
           
         ModelAndView modelAndView = new ModelAndView("notaList");
        modelAndView.addObject("notaList",DbCon.getInstance().entregarTodasLasNotas(libreta));
        modelAndView.addObject("libreta",request.getParameter("l"));
        modelAndView.addObject("mail",request.getParameter("correo"));
        return modelAndView;
    }
    
      /**
     * Se recibe un String lleno de tags y se splitea segun las comas (,) que
     * hayan, se forma una lista de Tags y se devuelve
     *
     * @param tagsSinOrden string con todos los tags separados por comas (,)
     * @return lista de tags
     */
    public List<Tag> formatearTags(String tagsSinOrden) {
        System.out.println("-----------------------------" + tagsSinOrden);
        List<Tag> listaTag = new ArrayList<Tag>();
        int recorreLista = 0;
        
        try {
            String[] tags = tagsSinOrden.split(",");
            
            while (recorreLista < tags.length) {
                Tag tag = new Tag();
                tag.setNombre(tags[recorreLista]);
                listaTag.add(tag);
                recorreLista++;
                
            }
        } catch (Exception e) {
            
            Tag tag = new Tag();
            tag.setNombre(tagsSinOrden);
            listaTag.add(tag);
        }
        
        return listaTag;
        
        
        
    }


}

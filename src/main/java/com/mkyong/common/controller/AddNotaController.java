package com.mkyong.common.controller;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import com.mkyong.common.controller.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DbCon;
import modelo.Nota;
import modelo.Tag;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Manejo de la vista AddNota, en esta clase es donde se crea o modifica una
 * nota que haya sido seleccionada en la pagina notaList (controlada por
 * NotaController)
 *
 * @author Angel Alberici
 */
public class AddNotaController extends AbstractController {
Logger logger = Logger.getLogger("com.AddNotaController");

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Integer id = null;

        if (request.getParameter("b") != null) {
            id = Integer.parseInt(request.getParameter("b"));
        }



        ModelAndView modelAndView = new ModelAndView("AddNota");
        Nota nota;
        modelAndView.addObject("mail", request.getParameter("correo"));
        modelAndView.addObject("libreta", request.getParameter("l"));
        
        if (request.getParameter("b") != null) {
            modelAndView.addObject("idNota", request.getParameter("b"));
        }
        if (id != null && id != 0) {

            modelAndView.addObject("nota", DbCon.getInstance().entregarNota(id));


        } else {
            modelAndView.addObject("nota", DbCon.getInstance().entregarNota(id));

        }

        if (request.getParameter("titulo") != null && request.getParameter("texto") != null) {
            nota = new Nota();
            List<Tag> tags = null;
            if (request.getParameter("b") != null) {
                nota.setId(Integer.parseInt(request.getParameter("b")));
            }
            nota.setTitulo(request.getParameter("titulo"));
            nota.setTexto(request.getParameter("texto"));
            nota.setLibreta_id(Integer.parseInt(request.getParameter("l")));
            if (request.getParameter("tags") != null) {
                tags = formatearTags(request.getParameter("tags"));
            }
             logger.info("Lista de tags de la nota: "+tags);
            System.out.println("TAGS::::::::::::" + tags);
            DbCon.getInstance().crearNota(nota, tags, null);
            
            //si ya cree la nota entonces me regreso a mi lista de notas 
            modelAndView = new ModelAndView("notaList");
            modelAndView.addObject("correo", request.getParameter("correo"));
            modelAndView.addObject("l", request.getParameter("l"));

        }
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

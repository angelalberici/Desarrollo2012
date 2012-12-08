/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DbCon;
import modelo.Nota;
import modelo.Tag;
import org.springframework.beans.support.PagedListHolder;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
// Import log4j classes.

/**
 * Se controla a la vista notaList, se reciben
 *
 * @author Angel Alberici
 */
public class NotaController implements Controller {

    Logger logger = Logger.getLogger("com.NotaController");
    final int tamañoPagina = 5;

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Nota nota;



        if (request.getParameter("b") != null && request.getParameter("titulo") == null
                && request.getParameter("texto") == null) {
            DbCon.getInstance().eliminarNota(Integer.parseInt(request.getParameter("b")));
            logger.info("Nota: " + request.getParameter("b") + " fue eliminada");
        }
        Integer libreta = Integer.parseInt(request.getParameter("l"));

        if (request.getParameter("titulo") != null && request.getParameter("texto") != null) {
            nota = new Nota();
            List<Tag> tags = null;
            if (request.getParameter("b") != null && !request.getParameter("b").equals("")) {
                nota.setId(Integer.parseInt(request.getParameter("b")));
            }
            nota.setTitulo(request.getParameter("titulo"));
            nota.setTexto(request.getParameter("texto"));

            nota.setLibreta_id(Integer.parseInt(request.getParameter("l")));
            if (request.getParameter("tags") != null) {
                tags = formatearTags(request.getParameter("tags"));
            }
            logger.info("Tags:" + tags + ",\n traidos de la nota: " + request.getParameter("b"));
            DbCon.getInstance().crearNota(nota, tags, null);
        }

        Integer pagina;
        if (request.getParameter("p") != null) {
            pagina = Integer.parseInt(request.getParameter("p"));
        } else {
            pagina = 0;
        }
        logger.info("Se esta accediendo a la página: " + pagina);
        String correo = request.getParameter("correo");
        String palabra = request.getParameter("palabra");

        ModelAndView modelAndView = new ModelAndView("notaList");
        modelAndView.addObject("pagedListHolder", paginarResultados(pagina, libreta, palabra, correo));
        modelAndView.addObject("libreta", request.getParameter("l"));
        modelAndView.addObject("mail", request.getParameter("correo"));
        modelAndView.addObject("url", "?l=" + libreta + "&correo=" + correo + "&palabra=" + palabra);
        return modelAndView;

    }

    /**
     * Se le pasan, el numero de pagina y la libreta a la que pertenecen las
     * notas.
     *
     * @see tamañoPagina es una constante que se declara en la clase que tiene
     * el numero de resultados a mostrar por pagina
     * @param paginaNum
     * @param libreta
     * @return una lista segmentada, se muestran resultados segun la constante
     * tamañoPagina, segun la pagina seleccionada y segun la libreta en la que
     * se esta posicionado
     */
    public PagedListHolder paginarResultados(int paginaNum, int libreta, String palabra, String correo) {
        try {
            PagedListHolder pagedListHolder;
            if (libreta != -1) {
                pagedListHolder = new PagedListHolder(DbCon.getInstance().entregarTodasLasNotas(libreta));
            } else {
                pagedListHolder = new PagedListHolder(DbCon.getInstance().entregarNotasBusqueda(palabra, correo));
            }
            pagedListHolder.setPageSize(tamañoPagina);
            pagedListHolder.setPage(paginaNum);

            return pagedListHolder;
        } catch (IOException ex) {
            logger.error("Error al intentar paginar paginarResultados.");
            ex.printStackTrace();

        }
        return null;
    }

    /**
     * Se recibe un String lleno de tags y se splitea segun las comas (,) que
     * hayan, se forma una lista de Tags y se devuelve
     *
     * @param tagsSinOrden string con todos los tags separados por comas (,)
     * @return lista de tags
     */
    public List<Tag> formatearTags(String tagsSinOrden) {
        logger.info("Los tags que se van a tener la nota: " + tagsSinOrden);
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
            logger.error("Error al intentar formatear los tags: " + tagsSinOrden);
            Tag tag = new Tag();
            tag.setNombre(tagsSinOrden);
            listaTag.add(tag);
        }

        return listaTag;



    }
}

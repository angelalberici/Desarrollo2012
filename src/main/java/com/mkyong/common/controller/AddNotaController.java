package com.mkyong.common.controller;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import com.mkyong.common.controller.*;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DbCon;
import modelo.Nota;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author Angel Alberici
 */
public class AddNotaController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Integer id;
        try {
            id = Integer.parseInt(request.getParameter("b"));
        } catch (Exception e) {
            //No hay parametros
            id = 0;
        }

        NotaManager notaManager = new NotaManager();

        ModelAndView modelAndView = new ModelAndView("AddNota");

        if (id != null && id != 0) {
            Nota nota = new Nota();
            nota.setId(id);
            modelAndView.addObject("notaList", nota);
        } else {
            modelAndView.addObject("notaList", notaManager.consultaNota(id));

        }

        return modelAndView;
    }
}

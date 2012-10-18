/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 *
 * @author Angel Alberici
 */
public class NotaListaController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest arg0,
            HttpServletResponse arg1) throws Exception {

        NotaManager notaManager = new NotaManager();
        ModelAndView modelAndView = new ModelAndView("notaList");
        modelAndView.addObject("notaList", notaManager.getNotaList());

        return modelAndView;
    }


}

package com.mkyong.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DbCon;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class HelloWorldController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

//intento fallido para conectar con la base de datos
//        DbCon conexion = new DbCon();
//
//        System.out.println("--------------------" + conexion.consultaImprimir());
        String codigo = (String) request.getParameter("code");
        ModelAndView model = new ModelAndView("HelloWorldPage");
        model.addObject("msg", "hello worlds");
        model.addObject("codigo", codigo);

        return model;
    }
}
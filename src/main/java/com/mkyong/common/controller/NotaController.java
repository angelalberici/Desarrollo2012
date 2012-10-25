/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Nota;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author Angel Alberici
 */
public class NotaController implements Controller{

    public ModelAndView handleRequest(HttpServletRequest arg0,
            HttpServletResponse arg1) throws Exception {

        NotaManager notaManager = new NotaManager();
        ModelAndView modelAndView = new ModelAndView("notaList");
        modelAndView.addObject("notaList", notaManager.getNotaList());

        return modelAndView;
    }

//    @RequestMapping(value = "/crearnota.htm", method = RequestMethod.GET)
//    public String editarNota(Model model) {
//
//
//        return "/crearnota.htm";
//    }

}
//@SessionAttributes("notaList")
//public class NotaController {
//
//    @RequestMapping(value = "/nota.htm", method = RequestMethod.POST)
//    public String initAboutYou(HttpServletRequest arg0,
//            HttpServletResponse arg1) throws Exception {
//
//        NotaManager notaManager = new NotaManager();
//
//        if (arg0.getParameter("b") != null) {
//            notaManager.deleteNota(arg0.getParameter("b"));
//        }
//
//        ModelAndView modelAndView = new ModelAndView("notaList");
//        modelAndView.addObject("notaList", notaManager.getNotaList());
//
//        return "/crearnota.htm";
//    }
//
//    @RequestMapping(value = "/crearnota.htm", method = RequestMethod.POST)
//    public String initAboutyou(@ModelAttribute("HelloWorldPage") List<Nota> notaList,
//            Model model,
//            BindingResult bindingResult) {
//        model.addAttribute("notaList", notaList);
//        return "redirect:/test1.htm";
//    }
//}

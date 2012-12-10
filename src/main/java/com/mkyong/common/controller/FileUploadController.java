/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DbCon;
import modelo.Usuario;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author Legna Filloy
 */
public class FileUploadController implements Controller {

    String ruta = "C:\\Users\\Angel Alberici\\Desktop\\";
    String correo = "";
    String idNota = "";
    Logger logger = Logger.getLogger("com.FileUploadController");

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {


        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        ModelAndView modelAndView = new ModelAndView("ChequeoAdjunto");

        try {

            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

            while (iter.hasNext()) {


                FileItem item = (FileItem) iter.next();


                if (item.getFieldName().equals("b")) {
                    idNota = item.getString();
                } else if (item.getFieldName().equals("correo")) {
                    correo = item.getString();
                } else {
                    File disk = new File(ruta + item.getName());
                    item.write(disk);
                    UploadGoogleDrive.uploadFile(idNota, Usuario.getInstance().getToken(), item.getName(), ruta);
                }
            }

        } catch (FileUploadException fue) {
            logger.info("Error al tratar de obtener los archivos que se quieren adjuntar");
        } catch (IOException ioe) {
            logger.info("Error al querer agarrar los parametros");
        } catch (Exception e) {
            logger.info("Error al tratar de obtener los archivos que se quieren adjuntar");
        }

        DbCon.getInstance().modificarAdjuntosDeNota(Integer.parseInt(idNota),UploadGoogleDrive.getListaAdjunto());
        modelAndView.addObject("cantidad",UploadGoogleDrive.getListaAdjunto().size());
        UploadGoogleDrive.borrarListaAdjunto();
        
        return modelAndView;
    }
}

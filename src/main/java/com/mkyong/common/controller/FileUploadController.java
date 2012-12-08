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
import modelo.Usuario;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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

    String ruta = "C:\\Users\\Legna Filloy\\Desktop\\";
    String titulo ="";
    String texto ="";
    String tags="";

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {


        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
      

        try {

            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

            while (iter.hasNext()) {
              
                
                FileItem item = (FileItem) iter.next();
                
                if(item.getFieldName().equals("titulo")){
                   titulo = item.getString();
                }
               else if (item.getFieldName().equals("texto")){
                    texto = item.getString(); 
               }
               else if (item.getFieldName().equals("tags")){
                    tags= item.getString();
               }
               else{
                File disk = new File(ruta + item.getName());
                item.write(disk);
                UploadGoogleDrive.uploadFile(Usuario.getInstance().getToken(), item.getName(), ruta);
               }
            }

        } catch (FileUploadException fue) {
            fue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("Libreta"); // Ver a donde coño voy a direccionar
       

    }
}

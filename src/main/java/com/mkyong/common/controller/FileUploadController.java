package com.mkyong.common.controller;


import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.FileUpload;
import modelo.Usuario;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


public class FileUploadController extends SimpleFormController {
String ruta ="C:\\Users\\Legna Filloy\\Desktop\\";
 
 
    public FileUploadController() {
        
        setCommandClass(FileUpload.class);
        setCommandName("fileUploadForm");
       
    }

    @Override
    /**
     * funcion para subir un archivo, desplaza el archivo seleccionado
     * a  un carpeta contenedora para que despues sea subido a google drive
     */ 
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
     
                
               FileUpload file = (FileUpload)command;	
		MultipartFile multipartFile = file.getFile();
		String fileName="";
            System.out.println("EL ARCHIVOOOOOOOOOOOOOOO"+multipartFile);
           if (multipartFile != null) {
                   fileName = multipartFile.getOriginalFilename();
                   File f = new File (ruta+fileName);
                   multipartFile.transferTo(f); 
                   System.out.println("nombre del archivooooooooooo"+fileName);
         
          UploadGoogleDrive.uploadFile(Usuario.getInstance().getToken(),fileName,ruta);
       
       }    
       //cambie esto antes era fileuploadsucces en vez de autenticacion 
        return new ModelAndView("FileUploadSuccess", "fileName", fileName);

    }
}
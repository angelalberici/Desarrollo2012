/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;

/**
 *
 * @author Legna Filloy
 */
public class Usuario {
    
    private String correo; 
    private String token; 
    private String code;
    private String folderID;
    private static Usuario usuarioInstance; 
   
    
   public static Usuario getInstance() throws IOException {
        if (usuarioInstance == null) {
            usuarioInstance = new Usuario();
        }
        if (usuarioInstance != null) {
            return usuarioInstance;
        }

        return null;
    }
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
        public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFolderID() {
        return folderID;
    }

    public void setFolderID(String folderID) {
        this.folderID = folderID;
    }
      
    
}

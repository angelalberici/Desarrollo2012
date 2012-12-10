/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import org.apache.log4j.Logger;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Adjunto;
import modelo.Usuario;

/**
 *
 * @author Legna Filloy
 */
public class UploadGoogleDrive {

    static List<Adjunto> listaAdjunto = new ArrayList<Adjunto>();
    static Logger logger = Logger.getLogger("com.UploadGoogleDrive");

    /**
     * Insert a file into a folder.
     *
     * @param service Drive API service instance.
     * @param folderId ID of the folder to insert the file into
     * @param fileId ID of the file to insert.
     * @return The inserted parent if successful, {@code null} otherwise.
     */
    private static ParentReference insertFileIntoFolder(String folderId,
            String fileId) throws IOException {
        ParentReference newParent = new ParentReference();
        newParent.setId(folderId);

        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        TokenResponse tokenResponse = new TokenResponse().setAccessToken(Usuario.getInstance().getToken());
        tokenResponse.setExpiresInSeconds(Long.MIN_VALUE);
        tokenResponse.setTokenType("Bearer");

        GoogleCredential c = new GoogleCredential().setFromTokenResponse(tokenResponse);
        Drive service = new Drive.Builder(httpTransport, jsonFactory, c).build();

        try {
            return service.parents().insert(fileId, newParent).execute();
        } catch (IOException e) {
           logger.info("Ocurrio un error al querer insertar el archivo en la carpeta de NoteManager");
        }
        return null;
    }

    public static void descargarArchivoDrive(String path, String idAdjunto) throws FileNotFoundException, IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        TokenResponse tokenResponse = new TokenResponse().setAccessToken(Usuario.getInstance().getToken());
        tokenResponse.setExpiresInSeconds(Long.MIN_VALUE);
        tokenResponse.setTokenType("Bearer");

        GoogleCredential c = new GoogleCredential().setFromTokenResponse(tokenResponse);
        Drive service = new Drive.Builder(httpTransport, jsonFactory, c).build();

        File f = service.files().get(idAdjunto).execute();
        FileOutputStream destino = new FileOutputStream(path + "\\" + f.getTitle());

        InputStream llegada = downloadFile(service, f);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = llegada.read(buffer)) > 0) {
            destino.write(buffer, 0, len);
        }
        llegada.close();destino.close();
    }

    /**
     * Download a file's content.
     *
     * @param service Drive API service instance.
     * @param file Drive File instance.
     * @return InputStream containing the file's content if successful,
     * {@code null} otherwise.
     */
    private static InputStream downloadFile(Drive service, File file) {

        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
            try {
                HttpResponse resp =
                        service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl())).execute();
                return resp.getContent();
            } catch (IOException e) {
                logger.info("Error al querer descargar el archivo "+file.getTitle()+" desde el Google Drive");
                return null;
            }
        } else {
            // The file doesn't have any content stored on Drive.
            return null;
        }
    }

    public static void uploadFile(String idNota, String token, String fileName, String ruta) {

        String extension[] = fileName.split("\\.");
        String extend = extension[extension.length - 1];

        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        TokenResponse tokenResponse = new TokenResponse().setAccessToken(token);
        tokenResponse.setExpiresInSeconds(Long.MIN_VALUE);
        tokenResponse.setTokenType("Bearer");

        GoogleCredential c = new GoogleCredential().setFromTokenResponse(tokenResponse);
        Drive service2 = new Drive.Builder(httpTransport, jsonFactory, c).build();


        File body = new File();
        body.setTitle(fileName);
        body.setDescription("A test document");

        if (extend.equals("txt")) {
            body.setMimeType("text/plain");
        } else if (extend.equals("pdf")) {
            body.setMimeType("application/pdf");
        } else if (extend.equals("jpg")) {
            body.setMimeType("image/jpeg");
        } else if (extend.equals("docx")) {
            body.setMimeType("application/msword");
        } else if (extend.equals("png")) {
            body.setMimeType("image/png");
        }


        java.io.File fileContent = new java.io.File(ruta + fileName);
        FileContent mediaContent = new FileContent("text/plain", fileContent);


        try {
            File file = service2.files().insert(body, mediaContent).execute();
            Adjunto adjunto = new Adjunto();
            adjunto.setId(file.getId());
            adjunto.setNombre(fileName);
            adjunto.setNota_id(idNota);
            listaAdjunto.add(adjunto);

            insertFileIntoFolder(Usuario.getInstance().getFolderID(), file.getId());
            logger.info("Se subio con exito el archivo "+fileName);

        } catch (IOException e) {
            logger.info("Error al querer subir el archivo "+fileName);
        }

    }

    public static void insertFolder(String token) {

        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        TokenResponse tokenResponse = new TokenResponse().setAccessToken(token);
        tokenResponse.setExpiresInSeconds(Long.MIN_VALUE);
        tokenResponse.setTokenType("Bearer");

        GoogleCredential c = new GoogleCredential().setFromTokenResponse(tokenResponse);
        Drive service2 = new Drive.Builder(httpTransport, jsonFactory, c).build();

        File folder = new File();
        folder.setTitle("Note Manager");
        folder.setDescription("A test document");
        folder.setMimeType("application/vnd.google-apps.folder");

        try {

            File file2 = service2.files().insert(folder).execute();
            Usuario.getInstance().setFolderID(file2.getId());
        } catch (IOException e) {
           logger.info("Error al querer crear la carpeta en Google Drive llamada NoteManager ");
        }


    }

    public static List<Adjunto> getListaAdjunto() {
        return listaAdjunto;
    }

    public static void borrarListaAdjunto() {
        int l = listaAdjunto.size();
        for (int i = 0; i < l; i++) {
            listaAdjunto.remove(0);
        }
    }
}

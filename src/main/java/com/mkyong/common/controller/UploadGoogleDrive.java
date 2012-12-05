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
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Legna Filloy
 */
public class UploadGoogleDrive {

    /**
     * Insert a file into a folder.
     *
     * @param service Drive API service instance.
     * @param folderId ID of the folder to insert the file into
     * @param fileId ID of the file to insert.
     * @return The inserted parent if successful, {@code null} otherwise.
     */
    private static ParentReference insertFileIntoFolder(Drive service, String folderId,
            String fileId) {
        ParentReference newParent = new ParentReference();
        newParent.setId(folderId);
        try {
            return service.parents().insert(fileId, newParent).execute();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
        return null;
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
                        service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
                        .execute();
                return resp.getContent();
            } catch (IOException e) {
                // An error occurred.
                e.printStackTrace();
                return null;
            }
        } else {
            // The file doesn't have any content stored on Drive.
            return null;
        }
    }

    private static List<File> retrieveAllFiles(Drive service) throws IOException {
        List<File> result = new ArrayList<File>();
        Files.List request = service.files().list();

        do {
            try {
                FileList files = request.execute();

                result.addAll(files.getItems());
                request.setPageToken(files.getNextPageToken());
            } catch (IOException e) {
                System.out.println("An error occurred: " + e);
                request.setPageToken(null);
            }
        } while (request.getPageToken() != null
                && request.getPageToken().length() > 0);

        return result;
    }

    public static void uploadFile(String token, String fileName, String ruta) {

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
            JOptionPane.showMessageDialog(null, "Archivo adjuntado correctamente");
            System.out.println("File ID: " + file.getId());
           
        } catch (IOException e) {
            e.printStackTrace();
             JOptionPane.showMessageDialog(null, "No hay conexion");       ;
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
        folder.setTitle("Carpeta nueva");
        folder.setDescription("A test document");
        folder.setMimeType("application/vnd.google-apps.folder");

        try {

            File file2 = service2.files().insert(folder).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

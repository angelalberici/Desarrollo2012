package com.mkyong.common.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase para el manejo de todas las interacciones con google drive
 *
 * @author Angel Alberici
 */
public class GoogleDrive {

    private static final String CLIENT_ID = "830695509204.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "Ylr-W22jmY9Xjm5tcHE_KI28";
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    private static Drive service;

    public GoogleDrive() throws IOException {

        iniciarConexion();

    }

//    public static void main(String[] args) throws IOException {
//
//        if (GoogleDrive.iniciarConexion() == 0) {
//            System.out.println("Error 1.00: Error al conectarse a Google Drive");
//        }
////        if (GoogleDrive.descargarArchivo(service, "0B5GGrI8FK4dcLW9wa1FaQ2ROY") == 0) {
////            System.out.println("Error 1.01: Error el archivo no existe");
////        }
////        if (GoogleDrive.mostrarArchivos() == null) {
////            System.out.println("No ha subido ningun archivo");
////        }
//
//    }
    /**
     * Iniciar conexion con google drive, se solicita autorizacion del usuario y
     * se adquiere un token.
     *
     * @throws IOException
     */
    public static int iniciarConexion() throws IOException {
        try {
            HttpTransport httpTransport = new NetHttpTransport();
            JsonFactory jsonFactory = new JacksonFactory();
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE)).setAccessType("online").setApprovalPrompt("auto").build();

            String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
            System.out.println("Please open the following URL in your browser then type the authorization code:");
            System.out.println("  " + url);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String code = br.readLine();

            GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
            GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);

            //Create a new authorized API client
            service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
            return 1;
        } catch (Exception e) {
            return 0;
        }


    }

    public int cargarArchivo(File body) throws IOException {
        //        //Insert a file  
//        File body = new File();
//        body.setTitle("prueba.txt");
//        body.setDescription("A test document");
//        body.setMimeType("text/plain");
        try {
            java.io.File fileContent = new java.io.File(body.getTitle());
            FileContent mediaContent = new FileContent(null, fileContent);
            File file = service.files().insert(body, mediaContent).execute();
            file.getId();
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * Descargar archivo de google drive, se debe tener iniciado una conexion
     * con token y se debe conocer el id del archivo a descargar
     *
     * @param service Sesion con google drive con token
     * @param fileId identificador del archivo a descargar
     * @return
     */
    public int descargarArchivo(Drive service, String fileId) {
        try {
            //        Descargar un archivo especifico 0B5GGrI8FK4dcLW9wa1FaQ2ROYzg
            File file = descargarMetadaDelArchivo(service, fileId);


            InputStream archivo = descargarContenidoDelArchivo(service, file);

            try {
                // read this file into InputStream
                InputStream inputStream = archivo;

                // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(file.getTitle());

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                inputStream.close();
                out.flush();
                out.close();

                System.out.println("New file created!");
                return 1;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return 0;
            }

        } catch (Exception o) {

//No existe un archivo con ese id
            return 0;
        }

    }

    /**
     * Download a file's content.
     *
     * @param service Drive API service instance.
     * @param file Drive File instance.
     * @return InputStream containing the file's content if successful,
     *         {@code null} otherwise.
     */
    public InputStream descargarContenidoDelArchivo(Drive service, File file) {
        try {
            if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
//                try {
                HttpResponse resp =
                        service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl())).execute();
                return resp.getContent();
//                } catch (IOException e) {
//                    // An error occurred.
//                    System.out.println("Error  1.1");
//                    e.printStackTrace();
//                    return null;
//                }
            } else {
                System.out.println("Error 1.2 No url");
                // The file doesn't have any content stored on Drive.
                return null;
            }
        } catch (Exception o) {

//No existe un archivo con ese id
            return null;
        }
    }

    /**
     * Download a file's metadata.
     *
     * @param service Drive API service instance.
     * @param fileId ID of the file to get metadata for.
     */
    public File descargarMetadaDelArchivo(Drive service, String fileId) {

        try {
            File file = service.files().get(fileId).execute();

            System.out.println("Title: " + file.getTitle());
            System.out.println("Description: " + file.getDescription());
            System.out.println("MIME type: " + file.getMimeType());
            return file;
        } catch (IOException e) {
            System.out.println("An error occured: " + e);
            return null;
        }

    }

    public List<File> mostrarArchivos() throws IOException {

        // Retrieve all list 
        List<File> rows = mostrarTodosLosArchivos(service);
        for (File row : rows) {

            System.out.println(row.getDownloadUrl());
            System.out.println(row.getId());
            System.out.println(row.getTitle());
            System.out.println(row.getMimeType());
            System.out.println(row.getOriginalFilename());
            System.out.println("-------------------------------");
        }

        if (rows.size() == 0) {
            return null;
        }
        return rows;


    }

    public List<File> mostrarTodosLosArchivos(Drive service) throws IOException {
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

    /**
     * Insert a file into a folder.
     *
     * @param service Drive API service instance.
     * @param folderId ID of the folder to insert the file into
     * @param fileId ID of the file to insert.
     * @return The inserted parent if successful, {@code null} otherwise.
     */
    public ParentReference insertFileIntoFolder(String folderId,
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

    public void delete(String idFile) throws IOException {
//           service.files().delete(idFile).execute();
        service.files().delete("0B5GGrI8FK4dcNnlXSHZiSUxYaDg").execute();
    }
}

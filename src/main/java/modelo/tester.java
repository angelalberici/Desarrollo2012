/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.google.api.services.drive.model.File;
import com.mkyong.common.controller.GoogleDrive;
import com.sun.imageio.plugins.gif.GIFImageMetadata;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Angel Alberici
 */
public class tester {


    public static void main(String[] args) throws IOException {
//        dbcon = new DbCon();
        System.out.println(DbCon.getInstance().eliminarNota(1));
        System.out.println(DbCon.getInstance().eliminarNota(1));
//        DbCon.getInstance().eliminarNota(2);
//        DbCon.getInstance().eliminarNota(3);
//        DbCon.getInstance().eliminarNota(1);
//        GoogleDrive g = new GoogleDrive();
//        g.insertFileIntoFolder(null, null);

//        testcrearNota();
//        testcrearTags();
//        testcrearAdjuntos();
//          testeliminarNota();
retornarNota();
        
    }
public static void retornarNota() throws IOException{
//        List<Nota> lista = DbCon.getInstance().entregarTodasLasNotas();
//         for (Nota nota : lista) {
//             System.out.println(nota.getFecha());
//         
//         }
//
//}
//    public static void testcrearNota() throws IOException {
//
//        Nota nota = new Nota();
//        nota.setId(3);
//        nota.setTitulo("Los Alpes y Anibal");
//        nota.setTexto("El gran Anibal invadia los Alpes, una hazaña realemnte sorprendente"
//                + "sin embargo tenia una manera sucia de luchar y sin honor");
//       
//        nota.setLibreta_id("1");
//        System.out.println(DbCon.getInstance().crearNota(null, null, null));
//        System.out.println(DbCon.getInstance().crearNota(nota, null, null));
////        System.out.println(dbcon.crearNota(null, null, null));
////        System.out.println(dbcon.crearNota(null, null, null));
//
//
//    }
//        public static void testcrearAdjuntos() throws IOException {
//            System.out.println("Adjuntos:");
//            System.out.println(DbCon.getInstance().modificarAdjuntosDeNota(3, null));
//            
//            List<Adjunto> listaAdjunto = new ArrayList<Adjunto>();
//            Adjunto a = new Adjunto();
//            a.setId("10");
//            a.setNombre("Anibal.png");
//            a.setNota_id("3");
//            listaAdjunto.add(a);
//            
//            System.out.println(DbCon.getInstance().modificarAdjuntosDeNota(3, listaAdjunto));
//        }
//        
//                public static void testcrearTags() throws IOException {
//            System.out.println("Tags:");
//            System.out.println(DbCon.getInstance().modificarAdjuntosDeNota(3, null));
//            
//                        List<Tag> listaTag = new ArrayList<Tag>();
//            Tag a = new Tag();
//            a.setNombre("anibal");
//            listaTag.add(a);
//            
//            System.out.println(DbCon.getInstance().modificarTagsDeNota(3, listaTag));
//        }
//                
//                public static void testeliminarNota() throws IOException {
//                
//                    System.out.println(DbCon.getInstance().eliminarNota(3));
//                
//                
                }
}

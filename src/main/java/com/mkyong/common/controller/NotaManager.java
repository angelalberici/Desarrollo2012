/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import modelo.DbCon;
import modelo.DbCon;
import modelo.Nota;
import modelo.Nota;
import modelo.Tag;

/**
 *
 * @author Angel Alberici
 */
public class NotaManager {

    private static List<Nota> notaList;
    private static DbCon modelo;
    
    public NotaManager() throws IOException {

 modelo = new DbCon();
notaList= modelo.entregarTodasLasNotas();

    }
    
    public Nota consultaNota(Integer id){
    return modelo.entregarNota(id);
    }

    public List<Nota> getNotaList() {
        return modelo.entregarTodasLasNotas();
    }
    
    public void addNota(Nota nota, Tag [] tags){

    }
    
    public void deleteNota(String id){
    modelo.eliminarNota(id);
    }
}

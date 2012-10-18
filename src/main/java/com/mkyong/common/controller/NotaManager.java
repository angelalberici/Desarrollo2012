/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common.controller;

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
    private DbCon modelo;
    public NotaManager() {

 modelo = new DbCon();
notaList= modelo.entregarTodasLasNotas();
//        Nota nota1 = new Nota();
//        nota1.setId(1);
//        nota1.setTitulo("Controladores");
//        nota1.setTexto("Me gustan mucho");
//
//        Nota nota2 = new Nota();
//        nota2.setId(2);
//        nota2.setTitulo("Examinando Modelos");
//        nota2.setTexto("Los modelos nos permiten muchas cosas");
//
//        Nota nota3 = new Nota();
//        nota3.setId(3);
//        nota3.setTitulo("Mirar vistas");
//        nota3.setTexto("Porque ser redundante es bueno");
//
//        notaList = new LinkedList<Nota>();
//        notaList.add(nota1);
//        notaList.add(nota2);
//        notaList.add(nota3);
    }

    public List<Nota> getNotaList() {
        return notaList;
    }
    
    public void addNota(Nota nota, Tag [] tags){

    }
    
    public void deleteNota(String id){
    modelo.eliminarNota(id);
    }
}

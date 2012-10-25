package modelo;

import java.security.Timestamp;
import java.util.Date;

public class Nota {
	private Integer id;
	private String titulo;
	private String texto;
        private String fecha;
        private String libreta_id;

    public String getLibreta_id() {
        return libreta_id;
    }

    public void setLibreta_id(String libreta_id) {
        this.libreta_id = libreta_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
        
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


 
 

}
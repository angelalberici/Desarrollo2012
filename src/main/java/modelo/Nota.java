package modelo;

import java.util.List;
import modelo.Adjunto;

public class Nota {
	private Integer id;
	private String titulo;
	private String texto;
        private String fecha;
        private Integer libreta_id;
        List <Tag> tags;
        List <Adjunto> adjuntos;

    public Nota() {
    }

    public List<Adjunto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<Adjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getLibreta_id() {
        return libreta_id;
    }

    public void setLibreta_id(Integer libreta_id) {
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
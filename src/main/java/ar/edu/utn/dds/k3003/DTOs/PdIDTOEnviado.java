package ar.edu.utn.dds.k3003.DTOs;

public class PdIDTOEnviado {

    private String contenido;
    private String descripcion;
    private String hechoId;
    private String lugar;
    private String momento;
    private String urlImagen;

    public PdIDTOEnviado() {
    }

    public PdIDTOEnviado(String contenido, String descripcion, String hechoId, String lugar, String momento, String urlImagen) {
        this.contenido = contenido;
        this.descripcion = descripcion;
        this.hechoId = hechoId;
        this.lugar = lugar;
        this.momento = momento;
        this.urlImagen = urlImagen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHechoId() {
        return hechoId;
    }

    public void setHechoId(String hechoId) {
        this.hechoId = hechoId;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getMomento() {
        return momento;
    }

    public void setMomento(String momento) {
        this.momento = momento;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}

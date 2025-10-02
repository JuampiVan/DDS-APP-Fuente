package ar.edu.utn.dds.k3003.DTOs;

import java.util.List;

public class PdiDTORecibido {

    private String id;
    private String hechoId;
    private String descripcion;
    private String lugar;
    private String momento;
    private String contenido;
    private String urlImagen;
    private String ocrResultado;
    private List<String> etiquetas;

    public PdiDTORecibido() {
    }

    public PdiDTORecibido(String id, String hechoId, String descripcion, String lugar, String momento, String contenido, String urlImagen, String ocrResultado, List<String> etiquetas) {
        this.id = id;
        this.hechoId = hechoId;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.momento = momento;
        this.contenido = contenido;
        this.urlImagen = urlImagen;
        this.ocrResultado = ocrResultado;
        this.etiquetas = etiquetas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHechoId() {
        return hechoId;
    }

    public void setHechoId(String hechoId) {
        this.hechoId = hechoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getOcrResultado() {
        return ocrResultado;
    }

    public void setOcrResultado(String ocrResultado) {
        this.ocrResultado = ocrResultado;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }
}

package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.facades.dtos.CategoriaHechoEnum;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Hecho {

  @Id
  private String id;

  @ManyToOne
  @JoinColumn(name = "coleccion_nombre")
  private Coleccion coleccion;

  private String titulo;

  @ElementCollection
  @CollectionTable(name = "etiquetas")
  @Column(name = "valor")
  private List<String> etiquetas;

  private CategoriaHechoEnum categoria;

  private String ubicacion;

  private LocalDateTime fecha;

  private String origen;

  private Boolean censurado;

  public Hecho(String id, Coleccion coleccion, String titulo) {
    this.id = id;
    this.coleccion = coleccion;
    this.titulo = titulo;
  }

  public Hecho() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Coleccion getColeccion() {
    return coleccion;
  }

  public void setColeccion(Coleccion coleccion) {
    this.coleccion = coleccion;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
}

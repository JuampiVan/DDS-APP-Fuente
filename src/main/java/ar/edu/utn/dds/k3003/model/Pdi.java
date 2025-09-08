package ar.edu.utn.dds.k3003.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Pdi {

  @Id
  private String id;

  @OneToOne
  @JoinColumn(name = "hecho_id")
  private Hecho hecho;
  private String descripcion;
  private String lugar;
  private LocalDateTime momento;
  private String contenido;
  @ElementCollection
  @CollectionTable(name = "etiquetas")
  @Column(name = "valor")
  List<String> etiquetas;

  public Pdi(String id, Hecho hecho) {
    this.id = id;
    this.hecho = hecho;
  }

  public Pdi() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Hecho getHecho() {
    return hecho;
  }

  public void setHecho(Hecho hecho) {
    this.hecho = hecho;
  }
}

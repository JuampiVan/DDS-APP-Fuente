package ar.edu.utn.dds.k3003.repository;

import ar.edu.utn.dds.k3003.model.Coleccion;
import ar.edu.utn.dds.k3003.model.Hecho;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("!test")
public class InMemoryHechoRepo implements HechoRepository{
  private List<Hecho> hechos;

  public InMemoryHechoRepo() {
    this.hechos = new ArrayList<Hecho>();
  }

  @Override
  public Optional<Hecho> findById(String id) {
    return this.hechos.stream().filter(c -> c.getId().equals(id)).findFirst();
  }

  @Override
  public Hecho save(Hecho hecho) {
    this.hechos.add(hecho);
    return hecho;
  }

  @Override
  public List<Hecho> findAll() {
    return new ArrayList<>(hechos);
  }

  @Override
  public List<Hecho> findByColeccion(Coleccion coleccion) {
    return this.hechos.stream().filter(h -> h.getColeccion().getNombre().equals(coleccion.getNombre())).collect(Collectors.toList());
  }
}
